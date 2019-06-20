package dao;

import dao.helpers.EntityPersister;
import database.CreateConnection;
import database.mapper.DBRowMapper;
import database.mapper.TextMessageMapper;
import datatypes.messages.TextMessage;
import enums.DaoType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static dao.helpers.FinalBlockExecutor.executeFinalBlock;
import static dao.helpers.QueryGenerator.getDeleteQuery;
import static dao.helpers.QueryGenerator.getSelectQuery;
import static database.mapper.TextMessageMapper.TABLE_NAME;
import static database.mapper.TextMessageMapper.TEXT_MESSAGE_ID;

public class TextMessageDao implements Dao<Integer, TextMessage> {
    private DBRowMapper<TextMessage> mapper = new TextMessageMapper();
    private Cao<Integer, TextMessage> cao = new Cao<>();

    @Override
    public TextMessage findById(Integer id) {
        return cao.findById(id);
    }

    @Override
    public void insert(TextMessage entity) {
        if (EntityPersister.executeInsert(entity)) {
            System.out.println("message inserted successfully");
            cao.add(entity);
        } else {
            System.out.println("Error inserting message");
        }
    }

    @Override
    public Collection<TextMessage> findAll() {
        return cao.findAll();
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
            if (result == 1) {
                System.out.println("message Deleted Successfully");
                cao.delete(id);
            } else
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

    @Override
    public void cache() {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            String query = getSelectQuery(TABLE_NAME);
            statement = connection.prepareStatement(query);
            rs = statement.executeQuery();
            while (rs.next()) {
                TextMessage message = mapper.mapRow(rs);
                cao.add(message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            executeFinalBlock(connection, statement, rs);
        }

    }

    public List<TextMessage> getTextMessagesOfGivenUsers(int senderId, int receiverId) {
        //not both sides
        return cao.findAll().stream().filter(s -> s.getSenderId() == senderId && s.getReceiverId() == receiverId).collect(Collectors.toList());
    }

}
