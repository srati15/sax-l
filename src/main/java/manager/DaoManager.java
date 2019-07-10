package manager;


import dao.*;
import datatypes.announcement.Announcement;
import datatypes.messages.FriendRequest;
import datatypes.messages.QuizChallenge;
import datatypes.messages.TextMessage;
import datatypes.quiz.Quiz;
import datatypes.quiz.QuizResult;
import datatypes.quiz.answer.Answer;
import datatypes.quiz.question.Question;
import datatypes.server.Activity;
import datatypes.user.Achievement;
import datatypes.user.Person;
import datatypes.user.User;
import datatypes.user.UserAchievement;
import enums.DaoType;
import enums.RequestStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

public class DaoManager {
    private static final Logger logger = LogManager.getLogger(DaoManager.class);
    private ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);

    private final Map<DaoType, Dao> map;
    private final AnnouncementDao announcementDao = new AnnouncementDao();
    private final AnswerDao answerDao = new AnswerDao();
    private final QuestionDao questionDao = new QuestionDao();
    private final FriendRequestDao friendRequestDao = new FriendRequestDao();
    private final TextMessageDao textMessageDao = new TextMessageDao();
    private final QuizDao quizDao = new QuizDao();
    private final UserDao userDao = new UserDao();
    private final QuizResultDao quizResultDao = new QuizResultDao();
    private final UserAchievementDao userAchievementDao = new UserAchievementDao();
    private final QuizChallengeDao quizChallengeDao = new QuizChallengeDao();
    private final ActivityDao activityDao = new ActivityDao();

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
        map.put(DaoType.QuizChallenge, quizChallengeDao);
        map.values().forEach(Dao::cache);
        setQuizFields();
        setUserFields();
        setTextMessages();
        setAchievements();
        setQuizResults();
        setQuizChallenges();
    }

    private void setQuizChallenges() {
        for (User user : userDao.findAll()) {
            List<QuizChallenge> quizChallenges = quizChallengeDao.findAll().stream().filter(s->s.getRequestStatus()==RequestStatus.Pending && s.getReceiverId() == user.getId()).collect(Collectors.toList());
            user.setQuizChallenges(quizChallenges);
        }
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
        Map<Integer, List<UserAchievement>> userAchievementMap = new HashMap<>();
        achievements.forEach(userAchievement -> {
            userAchievementMap.putIfAbsent(userAchievement.getUserId(), new ArrayList<>());
            userAchievementMap.get(userAchievement.getUserId()).add(userAchievement);
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
        if (friendRequestDao.insert(friendRequest)){
            Person sender = userDao.findById(friendRequest.getSenderId());
            User receiver = userDao.findById(friendRequest.getReceiverId());
            receiver.getPendingFriendRequests().add(sender);
            activityDao.insert(new Activity(sender.getId(), "sent friend request to "+receiver.getUserName(), LocalDateTime.now()));
        }
    }

    public void insert(Quiz quiz) {
        executor.execute(() -> {
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
            Achievement achievement = new Achievement("Amateur Author", "Gained for creating 1 quiz");
            List<Achievement> achievements = user.getAchievements().stream().map(UserAchievement::getAchievement).collect(Collectors.toList());
            if (!achievements.contains(achievement)) {
                UserAchievement userAchievement = new UserAchievement(user.getId(), achievement);
                insert(userAchievement);
            }
            Achievement prolificAchievement = new Achievement("Prolific Author", "Gained for creating 5 quizzes");
            if (!achievements.contains(prolificAchievement) && user.getQuizzes().size() == 5) {
                UserAchievement userAchievement = new UserAchievement(user.getId(), prolificAchievement);
                insert(userAchievement);
            }
            Achievement prodigiousAchievement = new Achievement("Prodigious Author", "Gained for creating 10 quizzes");
            if (!achievements.contains(prodigiousAchievement) && user.getQuizzes().size() == 10) {
                UserAchievement userAchievement = new UserAchievement(user.getId(), prodigiousAchievement);
                insert(userAchievement);
            }
        });
    }

    public void delete(User deleteUser) {
        if (userDao.deleteById(deleteUser.getId())){
            activityDao.insert(new Activity(deleteUser.getId(), "'s account is being removed",LocalDateTime.now()));
            deleteUser.getAchievements().forEach(achievement -> userAchievementDao.deleteById(achievement.getId()));
            deleteUser.getQuizzes().forEach(quiz -> {
                quizDao.deleteById(quiz.getId());
                answerDao.deleteAll(quiz.getQuestionAnswerMap().values());
                questionDao.deleteAll(quiz.getQuestionAnswerMap().keySet());
            });
            friendRequestDao.findAllForUser(deleteUser.getId()).forEach(friendRequest -> friendRequestDao.deleteById(friendRequest.getId()));
            deleteUser.getQuizResults().forEach(s->quizResultDao.deleteById(s.getId()));
            deleteUser.getTextMessages().values().forEach(textMessages -> textMessages.forEach(textMessage -> textMessageDao.deleteById(textMessage.getId())));
            activityDao.findAll().stream().filter(activity -> activity.getUserId() == deleteUser.getId()).forEach(activity -> activityDao.deleteById(activity.getId()));
            quizChallengeDao.findAll().stream().filter(quizChallenge -> quizChallenge.getReceiverId() == deleteUser.getId() || quizChallenge.getSenderId() == deleteUser.getId()).
                    forEach(quizChallenge -> quizChallengeDao.deleteById(quizChallenge.getId()));
        }else {
            logger.error("Unable to delete user, {}", deleteUser);
        }

    }

    private void insert(UserAchievement userAchievement) {
        executor.execute(() -> {
            if(userAchievementDao.insert(userAchievement)){
                userDao.findById(userAchievement.getUserId()).getAchievements().add(userAchievement);
                activityDao.insert(new Activity(userAchievement.getUserId(), "gained achievement "+userAchievement.getAchievement().getAchievementName(), LocalDateTime.now()));
            }
        });
    }

    public void insert(QuizResult quizResult) {
        executor.execute(() -> {
            if (quizResultDao.insert(quizResult)){
                activityDao.insert(new Activity(quizResult.getUserId(), "completed quiz, score:"+quizResult.getScore()+" time:"+quizResult.getTimeSpent()+"s", LocalDateTime.now()));
                User user = userDao.findById(quizResult.getUserId());
                user.getQuizResults().add(quizResult);
                Achievement possibleAchievement = new Achievement("Quiz Machine", "Gained for taking 10 quizzes");
                if (!user.getAchievements().stream().map(UserAchievement::getAchievement).collect(Collectors.toList()).contains(possibleAchievement) && user.getQuizResults().size() == 10) {
                    UserAchievement achievement = new UserAchievement(user.getId(), possibleAchievement);
                    userAchievementDao.insert(achievement);
                    user.getAchievements().add(achievement);
                }

                List<QuizResult> allQuizResultsOfThisQuiz = quizResultDao.findAll().stream().
                        filter(result -> result.getQuizId() == quizResult.getQuizId()).
                        sorted(Comparator.comparingInt(QuizResult::getScore).reversed().
                                thenComparing(QuizResult::getTimeSpent)).
                        collect(Collectors.toList());
                Achievement quizMachineAchievement = new Achievement("I Am The Greatest", "Gained for getting highest score in a quiz");
                if (allQuizResultsOfThisQuiz.size() > 0 &&
                        quizResult.equals(allQuizResultsOfThisQuiz.get(0)) &&
                        !user.getAchievements().stream().map(UserAchievement::getAchievement).
                                collect(Collectors.toList()).contains(quizMachineAchievement)) {
                    UserAchievement achievement = new UserAchievement(user.getId(), quizMachineAchievement);
                    userAchievementDao.insert(achievement);
                    user.getAchievements().add(achievement);
                }
            }
        });
    }

    public void delete(Quiz quiz) {
        if (quizDao.deleteById(quiz.getId())){
            //delete questions
            questionDao.deleteAll(quiz.getQuestionAnswerMap().keySet());
            //delete answers
            answerDao.deleteAll(quiz.getQuestionAnswerMap().values());

            User user = userDao.findById(quiz.getAuthorId());
            //remove this quiz from author's created quizzes
            user.getQuizzes().remove(quiz);
            List<QuizResult> resultsInThisQuiz = quizResultDao.findAll().stream().filter(quizResult -> quizResult.getQuizId() == quiz.getId()).collect(Collectors.toList());
            //delete quizResults of this quiz from db
            resultsInThisQuiz.forEach(quizResult -> quizResultDao.deleteById(quizResult.getId()));
            //delete quizResults of this quiz from runtime users
            resultsInThisQuiz.forEach(quizResult -> userDao.findById(quizResult.getUserId()).getQuizResults().remove(quizResult));
            //delete quiz challenges
            quizChallengeDao.findAll().stream().filter(quizChallenge -> quizChallenge.getQuizId() == quiz.getId()).forEach(quizChallenge -> quizChallengeDao.deleteById(quizChallenge.getId()));
            activityDao.insert(new Activity(quiz.getAuthorId(), "deleted quiz "+quiz.getQuizName(), LocalDateTime.now()));
        }
    }

    public void insert(TextMessage mes) {
        executor.execute(() -> {
            if (textMessageDao.insert(mes)){
                setMessages(mes);
            }
        });
    }

    private void setMessages(TextMessage mes) {
        User sender = userDao.findById(mes.getSenderId());
        User receiver = userDao.findById(mes.getReceiverId());

        sender.getTextMessages().putIfAbsent(receiver.getUserName(), new ArrayList<>());
        sender.getTextMessages().get(receiver.getUserName()).add(mes);

        receiver.getTextMessages().putIfAbsent(sender.getUserName(), new ArrayList<>());
        receiver.getTextMessages().get(sender.getUserName()).add(mes);
    }

    public void update(FriendRequest request) {
        friendRequestDao.update(request);
        User receiver = userDao.findById(request.getReceiverId());
        User sender = userDao.findById(request.getSenderId());
        System.out.println(receiver);
        System.out.println(sender);
        sender.getFriends().add(receiver);
        receiver.getFriends().add(sender);
        receiver.getPendingFriendRequests().remove(sender);
        activityDao.insert(new Activity(receiver.getId(), "accepted "+sender.getUserName()+"'s friend request", LocalDateTime.now()));
    }

    public void delete(FriendRequest request) {
        if (friendRequestDao.deleteById(request.getId())) {
            User sender = userDao.findById(request.getSenderId());
            User receiver = userDao.findById(request.getReceiverId());
            sender.getFriends().remove(receiver);
            receiver.getFriends().remove(sender);
            receiver.getPendingFriendRequests().remove(sender);
            activityDao.insert(new Activity(receiver.getId(), "rejected friendship with "+receiver.getUserName(), LocalDateTime.now()));
        }
    }

    public void insert(User user) {
        if (userDao.insert(user)){
            activityDao.insert(new Activity(user.getId(), "Registered", LocalDateTime.now()));
        }
    }

    public void update(User user) {
        if (userDao.update(user)){
            activityDao.insert(new Activity(user.getId(), "updated profile", LocalDateTime.now()));
        }
    }

    public void insert(Announcement announcement) {
        if (announcementDao.insert(announcement)){
            activityDao.insert(new Activity(announcement.getUserId(), "created announcement "+announcement, LocalDateTime.now()));
        }
    }

    public void update(Announcement announcement) {
        if (announcementDao.update(announcement)){
            activityDao.insert(new Activity(announcement.getUserId(), "updated announcement "+announcement, LocalDateTime.now()));
        }
    }

    public void insert(QuizChallenge challenge) {
        if (quizChallengeDao.insert(challenge)) {
            User receiver = userDao.findById(challenge.getReceiverId());
            receiver.getQuizChallenges().add(challenge);
        }
    }

    public void delete(Integer deleterId, Announcement announcement) {
        if (announcementDao.deleteById(announcement.getId())) {
            activityDao.insert(new Activity(deleterId,"deleted announcement", LocalDateTime.now()));
        }
    }
}
