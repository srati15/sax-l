package dao;

import datatypes.User;

import java.sql.Connection;
import java.util.List;

public class UserDao implements Dao<User> {
    private Connection connection;
    public UserDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public User findById(int id) {
        // TODO: 5/27/19  
        return null;
    }

    @Override
    public void insert(User entity) {
        // TODO: 5/27/19  
    }

    @Override
    public List<User> findAll() {
        // TODO: 5/27/19  
        return null;
    }

    @Override
    public void deleteById(int id) {
        // TODO: 5/27/19
    }

}
