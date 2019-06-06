package dao;

import database.CreateConnection;
import database.mapper.DBRowMapper;
import database.mapper.UserMapper;
import datatypes.User;
import enums.DaoType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static dao.FinalBlockExecutor.executeFinalBlock;
import static dao.QueryGenerator.*;
import static database.mapper.UserMapper.*;
public class UserDao implements Dao<Integer, User> {
    private DBRowMapper<User> mapper = new UserMapper();

    @Override
    public User findById(Integer id) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try  {
            String query = getSelectQuery(TABLE_NAME, USER_ID);
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapper.mapRow(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            executeFinalBlock(connection, statement, resultSet);
        }
        return null;
    }

    public User findByUserName(String usrName) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try   {
            String query = getSelectQuery(TABLE_NAME, USER_NAME);
            statement = connection.prepareStatement(query);
            statement.setString(1, usrName);
            resultSet = statement.executeQuery();
            if (resultSet.next()){
                return mapper.mapRow(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            executeFinalBlock(connection, statement, resultSet);
        }
        return null;
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
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            executeFinalBlock(connection, statement, rs);
        }
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        try {
            String query = getSelectQuery(TABLE_NAME);
            statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                users.add(mapper.mapRow(rs));
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
            String query = getDeleteQuery(TABLE_NAME, USER_ID);
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
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
            String query = getUpdateQuery(TABLE_NAME, USER_ID, FIRST_NAME, LAST_NAME, USER_PASSWORD, USER_TYPE);
            statement = connection.prepareStatement(query);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getPassword());
            statement.setInt(4, user.getUserType().getValue());
            statement.setInt(5, user.getId());
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
