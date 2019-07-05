package dao;

import database.CreateConnection;
import database.mapper.DBRowMapper;
import database.mapper.TextMessageMapper;
import datatypes.messages.TextMessage;
import enums.DaoType;

import java.sql.*;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;

import static dao.helpers.FinalBlockExecutor.executeFinalBlock;
import static dao.helpers.FinalBlockExecutor.rollback;
import static dao.helpers.QueryGenerator.*;
import static database.mapper.TextMessageMapper.*;

public class TextMessageDao implements Dao<Integer, TextMessage> {
    private DBRowMapper<TextMessage> mapper = new TextMessageMapper();
    private Cao<Integer, TextMessage> cao = new Cao<>();
    private AtomicBoolean isCached = new AtomicBoolean(false);
    public TextMessageDao() {

    }
    @Override
    public TextMessage findById(Integer id) {
        if (!isCached.get()) cache();
        return cao.findById(id);
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
            connection.commit();
            if (result == 1) {
                rs = statement.getGeneratedKeys();
                rs.next();
                entity.setId(rs.getInt(1));
                System.out.println("Text Message inserted successfully");
            }
            else System.out.println("Error inserting Text Message");

        } catch (SQLException e) {
            e.printStackTrace();
            rollback(connection);
        }finally {
            executeFinalBlock(connection, statement, rs);
        }
    }

    @Override
    public Collection<TextMessage> findAll() {
        if (!isCached.get()) cache();
        return cao.findAll();
    }

    @Override
    public void deleteById(Integer id) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        TextMessage message = findById(id);
        try {
            String query = getDeleteQuery(TABLE_NAME, TEXT_MESSAGE_ID);
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            int result = statement.executeUpdate();
            connection.commit();
            if(result == 1){
                System.out.println("message Deleted Successfully");
                cao.delete(id);
            }
            else
                System.out.println("Error Deleting message");
        } catch (SQLException e) {
            e.printStackTrace();
            rollback(connection);
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

    public void cache() {
        if (isCached.get()) return;
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            String query = getSelectQuery(TABLE_NAME);
            statement = connection.prepareStatement(query);
            rs = statement.executeQuery();
            while(rs.next()){
                TextMessage message = mapper.mapRow(rs);
                cao.add(message);
            }
            isCached.set(true);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            executeFinalBlock(connection, statement, rs);
        }

    }

}
