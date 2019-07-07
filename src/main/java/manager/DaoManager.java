package manager;


import dao.*;
import datatypes.*;
import datatypes.answer.Answer;
import datatypes.messages.FriendRequest;
import datatypes.messages.TextMessage;
import datatypes.question.Question;
import enums.DaoType;
import enums.RequestStatus;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class DaoManager {
    private Map<DaoType, Dao> map;
    private AnnouncementDao announcementDao = new AnnouncementDao();
    private AnswerDao answerDao = new AnswerDao();
    private QuestionDao questionDao = new QuestionDao();
    private FriendRequestDao friendRequestDao = new FriendRequestDao();
    private TextMessageDao textMessageDao = new TextMessageDao();
    private QuizDao quizDao = new QuizDao();
    private UserDao userDao = new UserDao();
    private QuizResultDao quizResultDao = new QuizResultDao();
    private UserAchievementDao userAchievementDao = new UserAchievementDao();
    private QuizChallengeDao quizChallengeDao = new QuizChallengeDao();
    private ActivityDao activityDao = new ActivityDao();

    public DaoManager() {
        map = new HashMap<>();
        map.put(DaoType.Announcement, announcementDao);
        map.put(DaoType.Answer, answerDao);
        map.put(DaoType.Question, questionDao);
        map.put(DaoType.User, userDao);
        map.put(DaoType.Quiz, quizDao);
        map.put(DaoType.FriendRequest, friendRequestDao);
        map.put(DaoType.TextMessage, textMessageDao);
        map.put(DaoType.QuizResult, quizResultDao);
        map.put(DaoType.UserAchievement, userAchievementDao);
        map.put(DaoType.Activity, activityDao);
        //map.put(DaoType.QuizChallenge, quizChallengeDao);
        map.values().forEach(Dao::cache);
        setQuizFields();
        setUserFields();
        setTextMessages();
        setAchievements();
        setQuizResults();
    }

    private void setQuizResults() {
        Collection<QuizResult> quizResults = quizResultDao.findAll();
        Map<Integer, List<QuizResult>> userQuizResultMap = new HashMap<>();
        quizResults.forEach(quizResult -> {
            userQuizResultMap.putIfAbsent(quizResult.getUserId(), new ArrayList<>());
            userQuizResultMap.get(quizResult.getUserId()).add(quizResult);
        });
        userQuizResultMap.keySet().forEach(userId -> userDao.findById(userId).setQuizResults(userQuizResultMap.get(userId)));
    }

    private void setAchievements() {
        Collection<UserAchievement> achievements = userAchievementDao.findAll();
        Map<Integer, List<Achievement>> userAchievementMap = new HashMap<>();
        achievements.forEach(userAchievement -> {
            userAchievementMap.putIfAbsent(userAchievement.getUserId(), new ArrayList<>());
            userAchievementMap.get(userAchievement.getUserId()).add(userAchievement.getAchievement());
        });
        userAchievementMap.keySet().forEach(userId -> userDao.findById(userId).setAchievements(userAchievementMap.get(userId)));
    }

    private void setTextMessages() {
        for (TextMessage message : textMessageDao.findAll()) {
            setMessages(message);
        }
    }

    private void setQuizFields() {
        for (Quiz quiz : quizDao.findAll()) {
            Map<Question, Answer> questionAnswerMap = new HashMap<>();
            List<Question> questions = questionDao.getQuestionForQuiz(quiz.getId());
            questions.forEach(question -> questionAnswerMap.put(question, answerDao.findAnswerForQuestion(question.getId())));
            quiz.setQuestionAnswerMap(questionAnswerMap);
        }
    }

    private void setUserFields() {
        for (User user : userDao.findAll()) {
            user.setQuizzes(quizDao.findAllForUser(user.getId()));
            user.setFriends(getFriendsForUser(user.getId()));
            user.setPendingFriendRequests(getPendingRequestsFor(user.getId()));
        }
    }

    private List<Person> getPendingRequestsFor(int receiverId) {
        List<Integer> pendingRequests = new ArrayList<>();
        friendRequestDao.findAll().stream().filter(s -> s.getReceiverId() == receiverId && s.getStatus() == RequestStatus.Pending).
                forEach(s -> pendingRequests.add(s.getSenderId()));
        List<Person> friendRequests = new ArrayList<>();
        pendingRequests.forEach(request -> friendRequests.add(userDao.findById(request)));
        return friendRequests;
    }

    private List<Person> getFriendsForUser(int id) {
        List<Integer> friendsIds = new ArrayList<>();
        friendRequestDao.findAll().stream().filter(s -> s.getReceiverId() == id && s.getStatus() == RequestStatus.Accepted).forEach(s -> friendsIds.add(s.getSenderId()));
        friendRequestDao.findAll().stream().filter(s -> s.getSenderId() == id && s.getStatus() == RequestStatus.Accepted).forEach(s -> friendsIds.add(s.getReceiverId()));
        List<Person> people = new ArrayList<>();
        friendsIds.forEach(friendId -> people.add(userDao.findById(friendId)));
        return people;
    }

    public <E extends Dao> E getDao(DaoType daoType) {
        return (E) map.get(daoType);
    }

    public void insert(FriendRequest friendRequest) {
        friendRequestDao.insert(friendRequest);
        Person sender = userDao.findById(friendRequest.getSenderId());
        User receiver = userDao.findById(friendRequest.getReceiverId());
        receiver.getPendingFriendRequests().add(sender);
        activityDao.insert(new Activity(sender.getId(), "sent friend request to "+receiver.getUserName(), LocalDateTime.now()));
    }

    public void insert(Quiz quiz) {
        new Thread(() -> {
            quizDao.insert(quiz);
            activityDao.insert(new Activity(quiz.getAuthorId(), "created quiz "+quiz.getQuizName(), LocalDateTime.now()));
            quiz.getQuestionAnswerMap().keySet().forEach(s -> s.setQuizId(quiz.getId()));
            questionDao.insertAll(quiz.getQuestionAnswerMap().keySet());
            for (Question question : quiz.getQuestionAnswerMap().keySet()) {
                quiz.getQuestionAnswerMap().get(question).setQuestionId(question.getId());
            }
            answerDao.insertAll(quiz.getQuestionAnswerMap().values());
            quiz.setQuestionAnswerMap(quiz.getQuestionAnswerMap());

            User creator = userDao.findById(quiz.getAuthorId());
            creator.getQuizzes().add(quiz);
            User user = userDao.findById(quiz.getAuthorId());
            Achievement achievement = new Achievement("Amateur Author");
            if (!user.getAchievements().contains(achievement)) {
                UserAchievement userAchievement = new UserAchievement(user.getId(), achievement);
                insert(userAchievement);
            }
            Achievement prolificAchievement = new Achievement("Prolific Author");
            if (!user.getAchievements().contains(prolificAchievement) && user.getQuizzes().size() == 5) {
                UserAchievement userAchievement = new UserAchievement(user.getId(), prolificAchievement);
                insert(userAchievement);
            }
            Achievement prodigiousAchievement = new Achievement("Prodigious Author");
            if (!user.getAchievements().contains(prodigiousAchievement) && user.getQuizzes().size() == 10) {
                UserAchievement userAchievement = new UserAchievement(user.getId(), prodigiousAchievement);
                insert(userAchievement);
            }
        }).start();
    }

    public void delete(User deleteUser) {
        userDao.deleteById(deleteUser.getId());
        // TODO: 7/5/19 delete all user fields from db
    }

    public void insert(UserAchievement userAchievement) {
        new Thread(() -> {
            userAchievementDao.insert(userAchievement);
            userDao.findById(userAchievement.getUserId()).getAchievements().add(userAchievement.getAchievement());
            activityDao.insert(new Activity(userAchievement.getUserId(), "gained achievement "+userAchievement.getAchievement().getAchievementName(), LocalDateTime.now()));
        }).start();
    }

    public void insert(QuizResult quizResult) {
        new Thread(() -> {
            quizResultDao.insert(quizResult);
            activityDao.insert(new Activity(quizResult.getUserId(), "completed quiz, score:"+quizResult.getScore()+" time:"+quizResult.getTimeSpent()+"s", LocalDateTime.now()));
            User user = userDao.findById(quizResult.getUserId());
            user.getQuizResults().add(quizResult);
            Achievement possibleAchievement = new Achievement("Quiz Machine");
            if (!user.getAchievements().contains(possibleAchievement) && user.getQuizResults().size() == 10) {
                userAchievementDao.insert(new UserAchievement(user.getId(), possibleAchievement));
                user.getAchievements().add(possibleAchievement);
            }

            List<QuizResult> allQuizResultsOfThisQuiz = quizResultDao.findAll().stream().
                    filter(result -> result.getQuizId() == quizResult.getQuizId()).
                    sorted(Comparator.comparingInt(QuizResult::getScore).reversed().
                            thenComparing(QuizResult::getTimeSpent)).
                    collect(Collectors.toList());
            Achievement quizMachineAchievement = new Achievement("I Am The Greatest");
            if (allQuizResultsOfThisQuiz.size() > 0 && quizResult.equals(allQuizResultsOfThisQuiz.get(0)) && !user.getAchievements().contains(quizMachineAchievement)) {
                userAchievementDao.insert(new UserAchievement(user.getId(), quizMachineAchievement));
                user.getAchievements().add(quizMachineAchievement);
            }
        }).start();
    }

    public void delete(Quiz quiz) {
        quizDao.deleteById(quiz.getId());

        //delete questions
        questionDao.deleteAll(quiz.getQuestionAnswerMap().keySet());
        quiz.getQuestionAnswerMap().keySet().forEach(question -> questionDao.deleteById(question.getId()));
        //delete answers
        answerDao.deleteAll(quiz.getQuestionAnswerMap().values());

        User user = userDao.findById(quiz.getAuthorId());
        Quiz quiz1 = user.getQuizzes().stream().filter(q -> q.getId().equals(quiz.getId())).findFirst().get();
        //remove this quiz from author's created quizzes
        user.getQuizzes().remove(quiz1);
        List<QuizResult> resultsInThisQuiz = quizResultDao.findAll().stream().filter(quizResult -> quizResult.getQuizId() == quiz.getId()).collect(Collectors.toList());
        //delete quizResults of this quiz from db
        resultsInThisQuiz.forEach(quizResult -> quizResultDao.deleteById(quizResult.getId()));
        //delete quizResults of this quiz from runtime users
        resultsInThisQuiz.forEach(quizResult -> userDao.findById(quizResult.getUserId()).getQuizResults().remove(quizResult));

        activityDao.insert(new Activity(quiz.getAuthorId(), "deleted quiz "+quiz.getQuizName(), LocalDateTime.now()));
    }

    public void insert(TextMessage mes) {
        new Thread(() -> {
            textMessageDao.insert(mes);
            setMessages(mes);
        }).start();
    }

    private void setMessages(TextMessage mes) {
        User sender = userDao.findById(mes.getSenderId());
        User receiver = userDao.findById(mes.getReceiverId());

        sender.getTextMessages().putIfAbsent(receiver, new ArrayList<>());
        sender.getTextMessages().get(receiver).add(mes);

        receiver.getTextMessages().putIfAbsent(sender, new ArrayList<>());
        receiver.getTextMessages().get(sender).add(mes);
    }

    public void update(FriendRequest request) {
        friendRequestDao.update(request);
        User sender = userDao.findById(request.getSenderId());
        User receiver = userDao.findById(request.getReceiverId());
        sender.getFriends().add(receiver);
        receiver.getFriends().add(sender);
        receiver.getPendingFriendRequests().remove(sender);
        activityDao.insert(new Activity(receiver.getId(), "accepted "+receiver.getUserName()+"'s friend request", LocalDateTime.now()));
    }

    public void delete(FriendRequest request) {
        friendRequestDao.deleteById(request.getId());
        User sender = userDao.findById(request.getSenderId());
        User receiver = userDao.findById(request.getReceiverId());
        sender.getFriends().remove(receiver);
        receiver.getFriends().remove(sender);
        receiver.getPendingFriendRequests().remove(sender);
        activityDao.insert(new Activity(receiver.getId(), "rejected friendship with "+receiver.getUserName(), LocalDateTime.now()));
    }

    public void insert(User user) {
        userDao.insert(user);
        activityDao.insert(new Activity(user.getId(), "Registered", LocalDateTime.now()));
    }

    public void update(User user) {
        userDao.update(user);
        activityDao.insert(new Activity(user.getId(), "updated profile", LocalDateTime.now()));
    }

    public void insert(Announcement announcement) {
        announcementDao.insert(announcement);
        activityDao.insert(new Activity(announcement.getUserId(), "created announcement", LocalDateTime.now()));
    }

    public void update(Announcement announcement) {
        announcementDao.update(announcement);
        activityDao.insert(new Activity(announcement.getUserId(), "updated announcement", LocalDateTime.now()));
    }
}
