package manager;


import dao.UserDao;
import database.DBConnector;

import java.sql.Connection;

public class DaoManager {
    private UserDao userDao;
    private Connection connection;
    public DaoManager(){
        DBConnector connector = new DBConnector();
        this.connection = connector.getConnection();
        userDao = new UserDao(connection);
    }

    public UserDao getUserDao() {
        return userDao;
    }
}
