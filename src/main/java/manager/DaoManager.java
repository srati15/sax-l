package manager;


import dao.*;
import datatypes.Person;
import datatypes.Quiz;
import datatypes.User;
import datatypes.answer.Answer;
import datatypes.messages.FriendRequest;
import datatypes.messages.Message;
import datatypes.messages.TextMessage;
import datatypes.question.Question;
import enums.DaoType;
import enums.RequestStatus;

import java.util.*;

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
    public DaoManager(){
        map = new HashMap<>();
        announcementDao.cache();
        answerDao.cache();
        questionDao.cache();
        quizDao.cache();
        userDao.cache();
        textMessageDao.cache();
        quizResultDao.cache();

        map.put(DaoType.Announcement, announcementDao);
        map.put(DaoType.Answer, answerDao);
        map.put(DaoType.Question, questionDao);
        map.put(DaoType.User, userDao);
        map.put(DaoType.Quiz, quizDao);
        map.put(DaoType.FriendRequest, friendRequestDao);
        map.put(DaoType.TextMessage, textMessageDao);
        map.put(DaoType.QuizResult, quizResultDao);

        setQuizFields();
        setUserFields();
        setTextMessages();
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
        pendingRequests.forEach(request->friendRequests.add(UserDao.getInstance().findById(request)));
        return friendRequests;
    }
    private List<Person> getFriendsForUser(int id) {
        List<Integer> friendsIds = new ArrayList<>();
        friendRequestDao.findAll().stream().filter(s -> s.getReceiverId() == id && s.getStatus() == RequestStatus.Accepted).forEach(s -> friendsIds.add(s.getSenderId()));
        friendRequestDao.findAll().stream().filter(s -> s.getSenderId() == id && s.getStatus() == RequestStatus.Accepted).forEach(s -> friendsIds.add(s.getReceiverId()));
        List<Person> people = new ArrayList<>();
        friendsIds.forEach(friendId-> people.add(UserDao.getInstance().findById(friendId)));
        return people;
    }
    public  <E extends Dao> E getDao(DaoType daoType){
        return (E) map.get(daoType);
    }

    public void insert(FriendRequest friendRequest) {
        Person sender = userDao.findById(friendRequest.getSenderId());
        userDao.findById(friendRequest.getReceiverId()).getPendingFriendRequests().add(sender);
    }

    public void insert(Quiz quiz) {
        quizDao.insert(quiz);
        User creator = userDao.findById(quiz.getAuthorId());
        creator.getQuizzes().add(quiz);
    }

    public void delete(User deleteUser) {
        userDao.deleteById(deleteUser.getId());

    }
}
