package manager;

import dao.*;
import enums.DaoType;

import java.util.HashMap;
import java.util.Map;

class DaoFactory {
    private static Map<DaoType, Dao > map;
    static void initDaos(){
        map = new HashMap<>();
        map.put(DaoType.User, new UserDao());
        map.put(DaoType.Quiz, new QuizDao(new QuestionDao(), new AnswerDao()));
        map.put(DaoType.Announcement, new AnnouncementDao());
        map.put(DaoType.FriendRequest, new FriendRequestDao());
    }
    static <E extends Dao> E dispatch(DaoType daoType) {
        if (map.containsKey(daoType)) return (E) map.get(daoType);
        throw new IllegalArgumentException("Dao not found for "+daoType.name());
    }
}
