package dao;

import dao.helpers.EntityPersister;
import database.CreateConnection;
import database.mapper.DBRowMapper;
import database.mapper.UserMapper;
import datatypes.User;
import enums.DaoType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import static dao.helpers.FinalBlockExecutor.executeFinalBlock;
import static dao.helpers.QueryGenerator.*;
import static database.mapper.UserMapper.*;

public class UserDao implements Dao<Integer, User> {
    private DBRowMapper<User> mapper = new UserMapper();
    private Cao<Integer, User> cao = new Cao<>();

    @Override
    public User findById(Integer id) {
        return cao.findById(id);
    }

    public User findByUserName(String usrName) {
        return cao.findAll().stream().filter(s -> s.getUserName().equals(usrName)).findFirst().orElse(null);
    }

    @Override
    public void insert(User entity) {
        if (EntityPersister.executeInsert(entity)) {
            System.out.println("Record inserted sucessfully");
            cao.add(entity);
        } else {
            System.out.println("Error inserting record");
        }
    }

    @Override
    public Collection<User> findAll() {
        return cao.findAll();
    }

    @Override
    public void deleteById(Integer id) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        try {
            String query = getDeleteQuery(TABLE_NAME, USER_ID);
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            int result = statement.executeUpdate();
            if (result == 1) {
                cao.delete(id);
                System.out.println("Record deleted sucessfully");
            } else System.out.println("Error deleting record");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            executeFinalBlock(connection, statement);
        }
    }

    public void update(User user) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        try {
            String query = getUpdateQuery(TABLE_NAME, USER_ID, FIRST_NAME, LAST_NAME, USER_PASSWORD, USER_TYPE);
            statement = connection.prepareStatement(query);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getPassword());
            statement.setInt(4, user.getUserType().getValue());
            statement.setInt(5, user.getId());
            int result = statement.executeUpdate();
            if (result == 1) {
                System.out.println("User updated sucessfully");
                cao.add(user);
            } else System.out.println("Error updating user");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            executeFinalBlock(connection, statement);
        }
    }

    @Override
    public DaoType getDaoType() {
        return DaoType.User;
    }

    @Override
    public void cache() {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        try {
            String query = getSelectQuery(TABLE_NAME);
            statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                User user = mapper.mapRow(rs);
                cao.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            executeFinalBlock(connection, statement);
        }
    }
}
