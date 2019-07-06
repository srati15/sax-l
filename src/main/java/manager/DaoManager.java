package manager;


import dao.*;
import datatypes.*;
import datatypes.answer.Answer;
import datatypes.messages.FriendRequest;
import datatypes.messages.Message;
import datatypes.messages.TextMessage;
import datatypes.question.Question;
import enums.DaoType;
import enums.RequestStatus;

import java.util.*;
import java.util.stream.Collectors;

public class DaoManager {
    private Map<DaoType, Dao > map;
    private AnnouncementDao announcementDao = new AnnouncementDao();
    private AnswerDao answerDao = new AnswerDao();
    private QuestionDao questionDao = new QuestionDao();
    private FriendRequestDao friendRequestDao = new FriendRequestDao();
    private TextMessageDao textMessageDao = new TextMessageDao();
    private QuizDao quizDao = new QuizDao();
    private UserDao userDao = new UserDao();
    private QuizResultDao quizResultDao = new QuizResultDao();
    private UserAchievementDao userAchievementDao = new UserAchievementDao();
    public DaoManager(){
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
            if (userQuizResultMap.containsKey(quizResult.getUserId())) {
                userQuizResultMap.get(quizResult.getUserId()).add(quizResult);
            }else {
                userQuizResultMap.put(quizResult.getUserId(), new ArrayList<>(Arrays.asList(quizResult)));
            }
        });
        userQuizResultMap.keySet().forEach(userId->userDao.findById(userId).setQuizResults(userQuizResultMap.get(userId)));
    }

    private void setAchievements() {
        Collection<UserAchievement> achievements = userAchievementDao.findAll();
        Map<Integer, List<Achievement>> userAchievementMap = new HashMap<>();
        achievements.forEach(userAchievement -> {
            if (userAchievementMap.containsKey(userAchievement.getUserId())) {
                userAchievementMap.get(userAchievement.getUserId()).add(userAchievement.getAchievement());
            }else {
                userAchievementMap.put(userAchievement.getUserId(), new ArrayList<>(Arrays.asList(userAchievement.getAchievement())));
            }
        });
        userAchievementMap.keySet().forEach(userId->userDao.findById(userId).setAchievements(userAchievementMap.get(userId)));
    }

    private void setTextMessages() {
        for (TextMessage message : textMessageDao.findAll()) {
            User sender = userDao.findById(message.getSenderId());
            User receiver = userDao.findById(message.getReceiverId());
            List<TextMessage> senderMessages = sender.getTextMessages().getOrDefault(receiver, new ArrayList<>());
            senderMessages.add(message);
            senderMessages.sort(Comparator.comparing(Message::getTimestamp));
            sender.getTextMessages().put(receiver, senderMessages);
            List<TextMessage> receiverMessages = receiver.getTextMessages().getOrDefault(sender, new ArrayList<>());
            receiverMessages.add(message);
            receiverMessages.sort(Comparator.comparing(Message::getTimestamp));
            receiver.getTextMessages().put(sender, senderMessages);
        }
    }

    private void setQuizFields() {
        for (Quiz quiz : quizDao.findAll()) {
            Map<Question, Answer> questionAnswerMap = new HashMap<>();
            List<Question> questions =  questionDao.getQuestionForQuiz(quiz.getId());
            questions.forEach(question->questionAnswerMap.put(question, answerDao.findAnswerForQuestion(question.getId())));
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
        friendRequestDao.findAll().stream().filter(s -> s.getReceiverId() == receiverId && s.getStatus()==RequestStatus.Pending).
                forEach(s->pendingRequests.add(s.getSenderId()));
        List<Person> friendRequests = new ArrayList<>();
        pendingRequests.forEach(request->friendRequests.add(userDao.findById(request)));
        return friendRequests;
    }
    private List<Person> getFriendsForUser(int id) {
        List<Integer> friendsIds = new ArrayList<>();
        friendRequestDao.findAll().stream().filter(s -> s.getReceiverId() == id && s.getStatus() == RequestStatus.Accepted).forEach(s -> friendsIds.add(s.getSenderId()));
        friendRequestDao.findAll().stream().filter(s -> s.getSenderId() == id && s.getStatus() == RequestStatus.Accepted).forEach(s -> friendsIds.add(s.getReceiverId()));
        List<Person> people = new ArrayList<>();
        friendsIds.forEach(friendId-> people.add(userDao.findById(friendId)));
        return people;
    }
    public  <E extends Dao> E getDao(DaoType daoType){
        return (E) map.get(daoType);
    }

    public void insert(FriendRequest friendRequest) {
        Person sender = userDao.findById(friendRequest.getSenderId());
        userDao.findById(friendRequest.getReceiverId()).getPendingFriendRequests().add(sender);
    }

    public void insert(TextMessage message){
        textMessageDao.insert(message);
        textMessageDao.cache();
    }
    public void insert(Quiz quiz) {
        quizDao.insert(quiz);
        

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
    }

    public void delete(User deleteUser) {
        userDao.deleteById(deleteUser.getId());
        // TODO: 7/5/19 delete all user fields from db
    }

    public void insert(UserAchievement userAchievement) {
        userAchievementDao.insert(userAchievement);
        userDao.findById(userAchievement.getUserId()).getAchievements().add(userAchievement.getAchievement());
    }

    public void insert(QuizResult quizResult) {
        quizResultDao.insert(quizResult);
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
        if (allQuizResultsOfThisQuiz.size()> 0 && quizResult.equals(allQuizResultsOfThisQuiz.get(0)) && !user.getAchievements().contains(quizMachineAchievement)) {
            userAchievementDao.insert(new UserAchievement(user.getId(), quizMachineAchievement));
            user.getAchievements().add(quizMachineAchievement);
        }
    }

    public void delete(Quiz quiz) {
        quizDao.deleteById(quiz.getId());

        //delete questions
        questionDao.deleteAll(quiz.getQuestionAnswerMap().keySet());
        quiz.getQuestionAnswerMap().keySet().forEach(question -> questionDao.deleteById(question.getId()));
        //delete answers
        answerDao.deleteAll(quiz.getQuestionAnswerMap().values());

        User user = userDao.findById(quiz.getAuthorId());
        Quiz quiz1 = user.getQuizzes().stream().filter(q-> q.getId().equals(quiz.getId())).findFirst().get();
        //remove this quiz from author's created quizzes
        user.getQuizzes().remove(quiz1);
        List<QuizResult> resultsInThisQuiz = quizResultDao.findAll().stream().filter(quizResult -> quizResult.getQuizId() == quiz.getId()).collect(Collectors.toList());
        //delete quizResults of this quiz from db
        resultsInThisQuiz.forEach(quizResult -> quizResultDao.deleteById(quizResult.getId()));
        //delete quizResults of this quiz from runtime users
        resultsInThisQuiz.forEach(quizResult -> userDao.findById(quizResult.getUserId()).getQuizResults().remove(quizResult));
    }
}
