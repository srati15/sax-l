package dao;

import database.CreateConnection;
import datatypes.User;
import enums.DaoType;
import enums.UserType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;

import static dao.helpers.FinalBlockExecutor.executeFinalBlock;
import static dao.helpers.FinalBlockExecutor.rollback;
import static dao.helpers.QueryGenerator.*;

public class UserDao implements Dao<Integer, User> {
    private static final Logger logger = LogManager.getLogger(UserDao.class);

    private final DBRowMapper<User> mapper = new UserMapper();
    private final Cao<Integer, User> cao = new Cao<>();
    private final AtomicBoolean isCached = new AtomicBoolean(false);
    private static final String USER_ID = "id";
    private static final String USER_NAME = "user_name";
    private static final String USER_PASSWORD = "pass";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String USER_MAIL = "mail";
    private static final String USER_TYPE = "user_type";
    private static final String TABLE_NAME = "users";

    @Override
    public User findById(Integer id) {
        if (!isCached.get()) cache();
        return cao.findById(id);
    }

    public User findByUserName(String usrName) {
        if (!isCached.get()) cache();
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
            connection.commit();
            if (result == 1) System.out.println("User inserted sucessfully");
            else System.out.println("Error inserting User");
            rs = statement.getGeneratedKeys();
            rs.next();
            entity.setId(rs.getInt(1));
            cao.add(entity);
        } catch (SQLException e) {
            e.printStackTrace();
            rollback(connection);
        } finally {
            executeFinalBlock(connection, statement, rs);
        }
    }

    @Override
    public Collection<User> findAll() {
        if (!isCached.get()) cache();
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
                System.out.println("User deleted sucessfully");
            } else System.out.println("Error deleting User");
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
            connection.commit();
            if (result == 1) {
                System.out.println("User updated sucessfully");
                cao.add(user);
            } else System.out.println("Error updating user");
        } catch (SQLException e) {
            e.printStackTrace();
            rollback(connection);
        } finally {
            executeFinalBlock(connection, statement);
        }
    }

    @Override
    public DaoType getDaoType() {
        return DaoType.User;
    }

    public void cache() {
        if (isCached.get()) return;
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
            isCached.set(true);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            executeFinalBlock(connection, statement);
        }
    }

    private class UserMapper implements DBRowMapper<User> {
        @Override
        public User mapRow(ResultSet rs) {
            try {
                int userId = rs.getInt(USER_ID);
                String userName = rs.getString(USER_NAME);
                String passwordHash = rs.getString(USER_PASSWORD);
                String firstName = rs.getString(FIRST_NAME);
                String lastName = rs.getString(LAST_NAME);
                String mail = rs.getString(USER_MAIL);
                UserType type = UserType.getById(rs.getInt(USER_TYPE));
                User user = new User(userName, passwordHash, firstName, lastName, mail);
                user.setUserType(type);
                user.setId(userId);
                return user;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
