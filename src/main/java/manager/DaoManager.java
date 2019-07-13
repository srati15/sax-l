package manager;


import dao.*;
import datatypes.announcement.Announcement;
import datatypes.messages.*;
import datatypes.quiz.Comment;
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
import mail.ReplySender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class DaoManager {
    private static final Logger logger = LogManager.getLogger(DaoManager.class);
    private ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);

    private final Map<DaoType, Dao> map;
    private final AnnouncementDao announcementDao = new AnnouncementDao();
    private final AnswerDao answerDao = new AnswerDao((ThreadPoolExecutor) Executors.newFixedThreadPool(4));
    private final QuestionDao questionDao = new QuestionDao((ThreadPoolExecutor) Executors.newFixedThreadPool(4));
    private final FriendRequestDao friendRequestDao = new FriendRequestDao();
    private final TextMessageDao textMessageDao = new TextMessageDao();
    private final QuizDao quizDao = new QuizDao();
    private final UserDao userDao = new UserDao();
    private final QuizResultDao quizResultDao = new QuizResultDao();
    private final UserAchievementDao userAchievementDao = new UserAchievementDao();
    private final QuizChallengeDao quizChallengeDao = new QuizChallengeDao();
    private final AdminMessageDao adminMessageDao = new AdminMessageDao();
    private final AdminReplyMessageDao adminReplyMessageDao = new AdminReplyMessageDao();
    private final ActivityDao activityDao = new ActivityDao((ThreadPoolExecutor) Executors.newFixedThreadPool(4));
    private final CommentDao commentDao = new CommentDao();
    private CountDownLatch latch ;
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
        map.put(DaoType.AdminMessage, adminMessageDao);
        map.put(DaoType.AdminReply, adminReplyMessageDao);
        map.put(DaoType.Comment, commentDao);
        latch = new CountDownLatch(map.size());
        map.values().forEach(dao -> {
            executor.execute(()->{
                dao.cache();
                latch.countDown();
            });
        });
        try {
            latch.await();
            setQuizFields();
            setUserFields();
            setTextMessages();
            setAchievements();
            setQuizResults();
            setQuizComments();
            setQuizChallenges();
        } catch (InterruptedException e) {
            logger.error(e);
        }
    }

    private void setQuizComments() {
        quizDao.findAll().forEach(quiz -> {
            quiz.setComments(commentDao.findAll().stream()
                    .filter(comment -> comment.getQuizId() == quiz.getId())
                    .collect(Collectors.toList()));
        });
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
            quiz.setTimesDone((int) quizResultDao.findAll().stream().filter(s->s.getQuizId() == quiz.getId()).count());
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
        executor.execute(()->{
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
        });
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
                int current = quizDao.findById(quizResult.getQuizId()).getTimesDone();
                quizDao.findById(quizResult.getQuizId()).setTimesDone(current+1);
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
        executor.execute(()->{
            if (quizDao.deleteById(quiz.getId())){
                //delete questions
                questionDao.deleteAll(quiz.getQuestionAnswerMap().keySet());
                //delete answers
                answerDao.deleteAll(quiz.getQuestionAnswerMap().values());
                //delete comments
                quiz.getComments().forEach(comment -> commentDao.deleteById(comment.getId()));

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
        });
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

    public boolean insert(User user) {
        if (userDao.insert(user)){
            activityDao.insert(new Activity(user.getId(), "Registered", LocalDateTime.now()));
            return true;
        }
        return false;
    }

    public boolean update(User user) {
        if (userDao.update(user)){
            activityDao.insert(new Activity(user.getId(), "updated profile", LocalDateTime.now()));
            return true;
        }
        return false;
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

    public void deleteHistoryForQuiz(int userId, int quizId) {
        executor.execute(()->{
            List<QuizResult> quizResults = quizResultDao.findAll().stream().filter(quizResult -> quizResult.getQuizId() == quizId).collect(Collectors.toList());
            quizResults.forEach(result->{
                quizResultDao.deleteById(result.getId());
                userDao.findById(result.getUserId()).getQuizResults().remove(result);
            });
            Quiz quiz = quizDao.findById(quizId);
            quiz.setTimesDone(0);
            quizDao.update(quiz);
            activityDao.insert(new Activity(userId, "deleted history for quiz "+quizDao.findById(quizId).getQuizName(), LocalDateTime.now()));
            logger.info("{} cleared history for quiz {}",userDao.findById(userId).getUserName(), quizDao.findById(quizId).getQuizName());
        });
    }
    public void shutDown(){
        logger.info("DaoManager is shutting down..");
        executor.execute(()->{
            activityDao.shutDown();
            questionDao.shutDown();
            answerDao.shutDown();
        });
        awaitTerminationAfterShutdown(executor);
        logger.info("Dao manager has shut down !!");
    }
    private void awaitTerminationAfterShutdown(ExecutorService threadPool) {
        threadPool.shutdown();
        try {
            if (!threadPool.awaitTermination(60, TimeUnit.SECONDS)) {
                threadPool.shutdownNow();
            }
        } catch (InterruptedException ex) {
            threadPool.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    public void insert(AdminMessage adminMessage) {
        adminMessageDao.insert(adminMessage);
    }

    public boolean insert(AdminReply adminReply) {
        if (adminReplyMessageDao.insert(adminReply)){
            ReplySender.send(adminMessageDao.findById(adminReply.getMessageId()), adminReply.getReplyText());
            AdminMessage message = adminMessageDao.findById(adminReply.getMessageId());
            message.setSeen(true);
            adminMessageDao.update(message);
            return true;
        }
        return false;
    }

    public void insert(Comment comment) {
        if (commentDao.insert(comment)){
            activityDao.insert(new Activity(comment.getUserId(), "added a comment", comment.getCommentDate()));
            quizDao.findById(comment.getQuizId()).getComments().add(comment);
        }
    }

    public void delete(Comment comment) {
        if (commentDao.deleteById(comment.getId())) {
            activityDao.insert(new Activity(comment.getUserId(), "Deleted a comment", LocalDateTime.now()));
            quizDao.findById(comment.getQuizId()).getComments().remove(comment);
        }
    }
}
