package manager;


import dao.*;
import datatypes.announcement.Announcement;
import datatypes.messages.AdminMessage;
import datatypes.messages.AdminReply;
import datatypes.messages.FriendRequest;
import datatypes.messages.TextMessage;
import datatypes.promise.Promise;
import datatypes.server.Activity;
import datatypes.toast.Toast;
import datatypes.user.Person;
import datatypes.user.User;
import datatypes.user.UserAchievement;
import enums.DaoType;
import enums.Level;
import enums.RequestStatus;
import mail.ReplySender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;

@Service
public class DaoManager {
    private static final Logger logger = LogManager.getLogger(DaoManager.class);
    private ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);

    private Map<DaoType, Dao> map;
    @Autowired
    private AnnouncementDao announcementDao;
    @Autowired
    private FriendRequestDao friendRequestDao;
    @Autowired
    private TextMessageDao textMessageDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserAchievementDao userAchievementDao;
    @Autowired
    private AdminMessageDao adminMessageDao;
    @Autowired
    private AdminReplyMessageDao adminReplyMessageDao;
    @Autowired
    private ToastDao toastDao;
    @Autowired
    private ActivityDao activityDao;
    private CountDownLatch latch;

    @PostConstruct
    private void post(){
        map = new HashMap<>();
        map.put(announcementDao.getDaoType(), announcementDao);
        map.put(userDao.getDaoType(), userDao);
        map.put(friendRequestDao.getDaoType(), friendRequestDao);
        map.put(textMessageDao.getDaoType(), textMessageDao);
        map.put(userAchievementDao.getDaoType(), userAchievementDao);
        map.put(activityDao.getDaoType(), activityDao);
        map.put(adminMessageDao.getDaoType(), adminMessageDao);
        map.put(adminReplyMessageDao.getDaoType(), adminReplyMessageDao);
        map.put(toastDao.getDaoType(), toastDao);
        latch = new CountDownLatch(map.size());
        map.values().forEach(dao -> {
            executor.execute(() -> {
                dao.cache();
                latch.countDown();
            });
        });
        try {
            latch.await();
            setUserFields();
            setTextMessages();
            setAchievements();
        } catch (InterruptedException e) {
            logger.error(e);
        }
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

    private void setUserFields() {
        for (User user : userDao.findAll()) {
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

    public Promise insert(FriendRequest friendRequest) {
        Promise promise = friendRequestDao.insert(friendRequest);
        if (promise.getLevel() == Level.INFO) {
            Person sender = userDao.findById(friendRequest.getSenderId());
            User receiver = userDao.findById(friendRequest.getReceiverId());
            receiver.getPendingFriendRequests().add(sender);
            activityDao.insert(new Activity(sender.getId(), "sent friend request to " + receiver.getUserName(), LocalDateTime.now()));
        }
        return promise;
    }


    public Promise delete(User deleteUser) {
        Promise promise = userDao.deleteById(deleteUser.getId());
        executor.execute(() -> {
            if (promise.getLevel() == Level.INFO) {
                activityDao.insert(new Activity(deleteUser.getId(), "'s account is being removed", LocalDateTime.now()));
                deleteUser.getAchievements().forEach(achievement -> userAchievementDao.deleteById(achievement.getId()));
                friendRequestDao.findAllForUser(deleteUser.getId()).forEach(friendRequest -> friendRequestDao.deleteById(friendRequest.getId()));
                deleteUser.getTextMessages().values().forEach(textMessages -> textMessages.forEach(textMessage -> textMessageDao.deleteById(textMessage.getId())));
                activityDao.findAll().stream().filter(activity -> activity.getUserId() == deleteUser.getId()).forEach(activity -> activityDao.deleteById(activity.getId()));

            } else {
                logger.error("Unable to delete user, {}", deleteUser);
            }
        });
        return promise;
    }

    private Promise insert(UserAchievement userAchievement) {
        Promise promise = userAchievementDao.insert(userAchievement);
        executor.execute(() -> {
            if (promise.getLevel() == Level.INFO) {
                userDao.findById(userAchievement.getUserId()).getAchievements().add(userAchievement);
                activityDao.insert(new Activity(userAchievement.getUserId(), "gained achievement " + userAchievement.getAchievement().getAchievementName(), LocalDateTime.now()));
            }
        });
        return promise;
    }

    public Promise insert(TextMessage mes) {
        Promise promise = textMessageDao.insert(mes);
        if (promise.getLevel() == Level.INFO) {
            activityDao.insert(new Activity(mes.getSenderId(), "sent message to " + userDao.findById(mes.getReceiverId()), LocalDateTime.now()));
            setMessages(mes);
        }
        return promise;
    }

    private void setMessages(TextMessage mes) {
        User sender = userDao.findById(mes.getSenderId());
        User receiver = userDao.findById(mes.getReceiverId());

        sender.getTextMessages().putIfAbsent(receiver.getUserName(), new ArrayList<>());
        sender.getTextMessages().get(receiver.getUserName()).add(mes);

        receiver.getTextMessages().putIfAbsent(sender.getUserName(), new ArrayList<>());
        receiver.getTextMessages().get(sender.getUserName()).add(mes);
    }

    public Promise update(FriendRequest request) {
        Promise promise = friendRequestDao.update(request);
        User receiver = userDao.findById(request.getReceiverId());
        User sender = userDao.findById(request.getSenderId());
        sender.getFriends().add(receiver);
        receiver.getFriends().add(sender);
        receiver.getPendingFriendRequests().remove(sender);
        activityDao.insert(new Activity(receiver.getId(), "accepted " + sender.getUserName() + "'s friend request", LocalDateTime.now()));
        return promise;
    }

    public Promise delete(FriendRequest request) {
        Promise promise = friendRequestDao.deleteById(request.getId());
        if (promise.getLevel() == Level.INFO) {
            User sender = userDao.findById(request.getSenderId());
            User receiver = userDao.findById(request.getReceiverId());
            sender.getFriends().remove(receiver);
            receiver.getFriends().remove(sender);
            receiver.getPendingFriendRequests().remove(sender);
            activityDao.insert(new Activity(receiver.getId(), "rejected friendship with " + sender.getUserName(), LocalDateTime.now()));
        }
        return promise;
    }

    public Promise insert(User user) {
        Promise promise = userDao.insert(user);
        if (promise.getLevel() == Level.INFO) {
            activityDao.insert(new Activity(user.getId(), "Registered", LocalDateTime.now()));
        }
        return promise;
    }

    public Promise update(User user) {
        Promise promise = userDao.update(user);
        if (promise.getLevel() == Level.INFO) {
            activityDao.insert(new Activity(user.getId(), "updated profile", LocalDateTime.now()));
        }
        return promise;
    }

    public Promise insert(Announcement announcement) {
        Promise promise = announcementDao.insert(announcement);
        if (promise.getLevel() == Level.INFO) {
            activityDao.insert(new Activity(announcement.getUserId(), "created announcement " + announcement, LocalDateTime.now()));
        }
        return promise;
    }

    public Promise update(Announcement announcement) {
        Promise promise = announcementDao.update(announcement);
        if (promise.getLevel() == Level.INFO) {
            activityDao.insert(new Activity(announcement.getUserId(), "updated announcement " + announcement, LocalDateTime.now()));
        }
        return promise;
    }

    public Promise delete(Integer deleterId, Announcement announcement) {
        Promise promise = announcementDao.deleteById(announcement.getId());
        if (promise.getLevel() == Level.INFO) {
            activityDao.insert(new Activity(deleterId, "deleted announcement", LocalDateTime.now()));
        }
        return promise;
    }


    public void shutDown() {
        logger.info("DaoManager is shutting down..");
        executor.execute(activityDao::shutDown);
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

    public Promise insert(AdminMessage adminMessage) {
        return adminMessageDao.insert(adminMessage);
    }

    public Promise insert(AdminReply adminReply) {
        Promise promise = adminReplyMessageDao.insert(adminReply);
        executor.execute(() -> {
            if (promise.getLevel() == Level.INFO) {
                ReplySender.send(adminMessageDao.findById(adminReply.getMessageId()), adminReply.getReplyText());
                AdminMessage message = adminMessageDao.findById(adminReply.getMessageId());
                message.setSeen(true);
                adminMessageDao.update(message);
            }
        });
        return promise;
    }

    public Promise insert(Toast toast) {
        return toastDao.insert(toast);
    }
}
