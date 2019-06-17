package manager;

import dao.*;
import enums.DaoType;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

class DaoFactory {
    private static Map<DaoType, Dao > map;
    static void initDaos(){
        map = new HashMap<>();
        map.put(DaoType.User, new UserDao());
        map.put(DaoType.Quiz, new QuizDao(new QuestionDao(), new AnswerDao()));
        map.put(DaoType.Announcement, new AnnouncementDao());
        map.put(DaoType.FriendRequest, new FriendRequestDao());
        map.put(DaoType.TextMessage, new TextMessageDao());
        cache();
    }

    private static void cache() {
        CountDownLatch latch = new CountDownLatch(map.size());
        map.values().stream().forEach(dao -> {
            new Thread(() -> {
                dao.cache();
                latch.countDown();
            }).start();
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static <E extends Dao> E dispatch(DaoType daoType) {
        if (map.containsKey(daoType)) {
            assert map.get(daoType).getDaoType().equals(daoType);
            return (E) map.get(daoType);
        }
        throw new IllegalArgumentException("Dao not found for "+daoType.name());
    }
}
