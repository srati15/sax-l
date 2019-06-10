package dao;

import database.CreateConnection;
import database.mapper.DBRowMapper;
import database.mapper.TextMessageMapper;
import datatypes.messages.TextMessage;
import enums.DaoType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static dao.helpers.FinalBlockExecutor.executeFinalBlock;
import static dao.helpers.QueryGenerator.*;
import static database.mapper.TextMessageMapper.*;

public class TextMessageDao implements Dao<Integer, TextMessage> {
    private DBRowMapper<TextMessage> mapper = new TextMessageMapper();
    @Override
    public TextMessage findById(Integer id) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            String query = getSelectQuery(TABLE_NAME, TEXT_MESSAGE_ID);
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            rs = statement.executeQuery();
            rs.next();
            return mapper.mapRow(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            executeFinalBlock(connection, statement, rs);
        }
        return null;
    }

    @Override
    public void insert(TextMessage entity) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            String query = getInsertQuery(TABLE_NAME, SENDER_ID, RECEIVER_ID, DATE_SENT, MESSAGE_SENT);
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, entity.getSenderId());
            statement.setInt(2, entity.getReceiverId());
            statement.setTimestamp(3, entity.getTimestamp());
            statement.setString(4, entity.getTextMessage());
            int result = statement.executeUpdate();
            if (result == 1) System.out.println("message inserted successfully");
            else System.out.println("Error inserting message");
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
    public List<TextMessage> findAll() {
        List<TextMessage> list = new ArrayList<>();
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            String query = getSelectQuery(TABLE_NAME);
            statement = connection.prepareStatement(query);
            rs = statement.executeQuery();
            while(rs.next()){
                list.add(mapper.mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            executeFinalBlock(connection, statement, rs);
        }

        return list;
    }

    @Override
    public void deleteById(Integer id) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        try {
            String query = getDeleteQuery(TABLE_NAME, TEXT_MESSAGE_ID);
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            int result = statement.executeUpdate();
            if(result == 1)
                System.out.println("message Deleted Successfully");
            else
                System.out.println("Error Deleting message");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            executeFinalBlock(connection, statement);
        }
    }

    @Override
    public void update(TextMessage entity) {

    }

    @Override
    public DaoType getDaoType() {
        return DaoType.TextMessage;
    }


    public List<TextMessage> getTextMessagesOfGivenUsers(int senderId, int receiverId){
        List<TextMessage> list = new ArrayList<>();
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            String query = getSelectQuery(TABLE_NAME);
            query = query + " ORDER BY " + DATE_SENT + " ASC";
            statement = connection.prepareStatement(query);
            rs = statement.executeQuery();
            while(rs.next()){
                list.add(mapper.mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            executeFinalBlock(connection, statement, rs);
        }

        return list;
    }

}
