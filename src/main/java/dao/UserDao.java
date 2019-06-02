package dao;

import database.CreateConnection;
import datatypes.User;
import enums.DaoType;
import enums.UserType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static dao.FinalBlockExecutor.executeFinalBlock;

public class UserDao implements Dao<Integer, User> {

    @Override
    public User findById(Integer id) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        try  {
            statement = connection.prepareStatement("SELECT * FROM users WHERE user_id=?");
            statement.setInt(1, id);
            return getUser(statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            executeFinalBlock(connection, statement);
        }
        return null;
    }

    public User findByUserName(String usrName) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        try   {
            statement = connection.prepareStatement("SELECT * FROM users WHERE user_name=?");
            statement.setString(1, usrName);
            return getUser(statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            executeFinalBlock(connection, statement);
        }
        return null;
    }

    private User getUser(PreparedStatement stmt) {
        ResultSet rs;
        try {
            rs = stmt.executeQuery();
            while (rs.next()) {
                int userId = rs.getInt("user_id");
                String userName = rs.getString("user_name");
                String passwordHash = rs.getString("pass");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                UserType type = UserType.getById(rs.getInt("user_type"));
                String mail = rs.getString("mail");
                User user = new User(userName, passwordHash, firstName, lastName, mail);
                user.setId(userId);
                user.setUserType(type);
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void insert(User entity) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("INSERT INTO users (user_name, pass, first_name, last_name, user_type, mail) values (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, entity.getUserName());
            statement.setString(2, entity.getPassword());
            statement.setString(3, entity.getFirstName());
            statement.setString(4, entity.getLastName());
            statement.setInt(5, entity.getUserType().getValue());
            statement.setString(6, entity.getMail());
            int result = statement.executeUpdate();
            if (result == 1) System.out.println("Record inserted sucessfully");
            else System.out.println("Error inserting record");
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            entity.setId(rs.getInt(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            executeFinalBlock(connection, statement);
        }
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("SELECT * FROM users");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int userId = rs.getInt("user_id");
                String userName = rs.getString("user_name");
                String passwordHash = rs.getString("pass");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String mail = rs.getString("mail");
                UserType type = UserType.getById(rs.getInt("user_type"));
                User user = new User(userName, passwordHash, firstName, lastName, mail);
                user.setUserType(type);
                user.setId(userId);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            executeFinalBlock(connection, statement);
        }
        return users;
    }

    @Override
    public void deleteById(Integer id) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("DELETE FROM users WHERE user_id=" + id);
            int result = statement.executeUpdate();
            if (result == 1) System.out.println("Record deleted sucessfully");
            else System.out.println("Error deleting record");
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            executeFinalBlock(connection, statement);
        }

    }

    public void update(User user) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("UPDATE users set first_name = ?, last_name = ?, pass = ? WHERE user_id=" + user.getId());
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getPassword());
            int result = statement.executeUpdate();
            if (result == 1) System.out.println("User updated sucessfully");
            else System.out.println("Error updating user");
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            executeFinalBlock(connection, statement);
        }
    }

    @Override
    public DaoType getDaoType() {
        return DaoType.User;
    }
}
