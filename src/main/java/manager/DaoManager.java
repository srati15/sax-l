package manager;


import dao.AnnouncementDao;
import dao.UserDao;
import database.DBConnector;

import java.sql.Connection;

public class DaoManager {
    private UserDao userDao;
    private AnnouncementDao announcementDao;
    private Connection connection;
    public DaoManager(){
        DBConnector connector = new DBConnector();
        this.connection = connector.getConnection();
        userDao = new UserDao(connection);
        announcementDao = new AnnouncementDao(connection);
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public AnnouncementDao getAnnouncementDao() {
        return announcementDao;
    }
}
