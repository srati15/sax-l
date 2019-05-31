package manager;


import dao.AnnouncementDao;
import dao.UserDao;
import database.CreateConnection;

import java.sql.Connection;

public class DaoManager {
    private UserDao userDao;
    private AnnouncementDao announcementDao;
    public DaoManager(){
        userDao = new UserDao();
        announcementDao = new AnnouncementDao();
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public AnnouncementDao getAnnouncementDao() {
        return announcementDao;
    }
}
