package manager;


import dao.*;
import enums.DaoType;

import java.util.HashMap;
import java.util.Map;

public class DaoManager {
    private Map<DaoType, Dao > map;
    public DaoManager(){
        map = new HashMap<>();
        AnnouncementDao announcementDao = AnnouncementDao.getInstance();
        announcementDao.cache();
        AnswerDao answerDao = AnswerDao.getInstance();
        answerDao.cache();
        QuestionDao questionDao = QuestionDao.getInstance();
        questionDao.cache();
        FriendRequestDao friendRequestDao = FriendRequestDao.getInstance();
        friendRequestDao.cache();
        TextMessageDao textMessageDao = TextMessageDao.getInstance();
        textMessageDao.cache();
        QuizDao quizDao = QuizDao.getInstance();
        quizDao.cache();
        UserDao userDao = UserDao.getInstance();
        userDao.cache();
        map.put(DaoType.Announcement, AnnouncementDao.getInstance());
        map.put(DaoType.Answer, AnswerDao.getInstance());
        map.put(DaoType.Question, QuestionDao.getInstance());
        map.put(DaoType.User, UserDao.getInstance());
        map.put(DaoType.Quiz, QuizDao.getInstance());
        map.put(DaoType.FriendRequest, FriendRequestDao.getInstance());
        map.put(DaoType.TextMessage, TextMessageDao.getInstance());
    }

    public  <E extends Dao> E getDao(DaoType daoType){
        return (E) map.get(daoType);
    }
}
