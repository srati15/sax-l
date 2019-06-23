package dao;

import database.CreateConnection;
import database.mapper.DBRowMapper;
import database.mapper.UserMapper;
import datatypes.User;
import enums.DaoType;

import java.sql.*;
import java.util.Collection;

import static dao.helpers.FinalBlockExecutor.executeFinalBlock;
import static dao.helpers.QueryGenerator.*;
import static database.mapper.UserMapper.*;

public class UserDao implements Dao<Integer, User> {
    private DBRowMapper<User> mapper = new UserMapper();
    private Cao<Integer, User> cao = new Cao<>();
    private static final UserDao userDao = new UserDao();
    public static UserDao getInstance() {
        return userDao;
    }
    private UserDao() {

    }
    @Override
    public User findById(Integer id) {
        return cao.findById(id);
    }

    public User findByUserName(String usrName) {
        return cao.findAll().stream().filter(s -> s.getUserName().equals(usrName)).findFirst().orElse(null);
    }

    @Override
    public void insert(User entity) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            String query = getInsertQuery(TABLE_NAME, USER_NAME, USER_PASSWORD, FIRST_NAME, LAST_NAME, USER_TYPE, USER_MAIL);
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, entity.getUserName());
            statement.setString(2, entity.getPassword());
            statement.setString(3, entity.getFirstName());
            statement.setString(4, entity.getLastName());
            statement.setInt(5, entity.getUserType().getValue());
            statement.setString(6, entity.getMail());
            int result = statement.executeUpdate();
            if (result == 1) System.out.println("Record inserted sucessfully");
            else System.out.println("Error inserting record");
            rs = statement.getGeneratedKeys();
            rs.next();
            entity.setId(rs.getInt(1));
            cao.add(entity);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            executeFinalBlock(connection, statement, rs);
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
            for (User user : cao.findAll()) {
                user.setQuizzes(QuizDao.getInstance().findAllForUser(user.getId()));
                user.setFriends(FriendRequestDao.getInstance().getFriendsForUser(user.getId()));
                user.setPendingFriendRequests(FriendRequestDao.getInstance().getPendingRequestsFor(user.getId()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            executeFinalBlock(connection, statement);
        }
    }
}
