package dao;

import datatypes.User;
import enums.UserType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao implements Dao<User> {
    private Connection connection;

    public UserDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public User findById(int id) {
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM users WHERE user_id=?")) {
            stmt.setInt(1, id);
            return getUser(stmt);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User findByUserName(String usrName) {
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM users WHERE user_name=?")) {
            stmt.setString(1, usrName);
            return getUser(stmt);
        } catch (SQLException e) {
            e.printStackTrace();
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
        PreparedStatement stmt;
        try {
            stmt = connection.prepareStatement("INSERT INTO users (user_name, pass, first_name, last_name, user_type, mail) values (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, entity.getUserName());
            stmt.setString(2, entity.getPassword());
            stmt.setString(3, entity.getFirstName());
            stmt.setString(4, entity.getLastName());
            stmt.setInt(5, entity.getUserType().getValue());
            stmt.setString(6, entity.getMail());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            entity.setId(rs.getInt(1));
            System.out.println(findById(entity.getId()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM users")) {
            ResultSet rs = stmt.executeQuery();
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
        }
        return users;
    }

    @Override
    public void deleteById(int id) {
        try (PreparedStatement stmt = connection.prepareStatement("DELETE FROM users WHERE user_id=" + id)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateById(User user) {
        try (PreparedStatement stmt = connection.prepareStatement("UPDATE users set first_name = ?, last_name = ?, pass = ? WHERE user_id=" + user.getId())) {
            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getPassword());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
