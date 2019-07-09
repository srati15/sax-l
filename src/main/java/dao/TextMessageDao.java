package dao;

import database.CreateConnection;
import datatypes.messages.Message;
import datatypes.messages.TextMessage;
import enums.DaoType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import static dao.helpers.FinalBlockExecutor.executeFinalBlock;
import static dao.helpers.FinalBlockExecutor.rollback;
import static dao.helpers.QueryGenerator.*;

public class TextMessageDao implements Dao<Integer, TextMessage> {
    private static final Logger logger = LogManager.getLogger(TextMessageDao.class);

    private final DBRowMapper<TextMessage> mapper = new TextMessageMapper();
    private final Cao<Integer, TextMessage> cao = new Cao<>();
    private final AtomicBoolean isCached = new AtomicBoolean(false);
    private static final String TEXT_MESSAGE_ID= "id";
    private static final String SENDER_ID = "sender_id";
    private static final String RECEIVER_ID = "receiver_id";
    private static final String DATE_SENT = "date_sent";
    private static final String MESSAGE_SENT = "message_sent";
    private static final String TABLE_NAME = "text_message";

    public TextMessageDao() {

    }
    @Override
    public TextMessage findById(Integer id) {
        if (!isCached.get()) cache();
        return cao.findById(id);
    }

    @Override
    public boolean insert(TextMessage entity) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            String query = getInsertQuery(TABLE_NAME, SENDER_ID, RECEIVER_ID, DATE_SENT, MESSAGE_SENT);
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, entity.getSenderId());
            statement.setInt(2, entity.getReceiverId());
            statement.setTimestamp(3, Timestamp.valueOf(entity.getTimestamp()));
            statement.setString(4, entity.getTextMessage());
            logger.debug("Executing statement: {}", statement);
            int result = statement.executeUpdate();
            connection.commit();
            if (result == 1) {
                rs = statement.getGeneratedKeys();
                rs.next();
                entity.setId(rs.getInt(1));
                cao.add(entity);
                logger.info("Text Message inserted successfully, {}", entity);
                return true;
            }
            else logger.error("Error inserting Text Message, {}", entity);

        } catch (SQLException e) {
            logger.error(e);
            rollback(connection);
        }finally {
            executeFinalBlock(connection, statement, rs);
        }
        return false;
    }

    @Override
    public Collection<TextMessage> findAll() {
        if (!isCached.get()) cache();
        return cao.findAll();
    }

    @Override
    public boolean deleteById(Integer id) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        try {
            String query = getDeleteQuery(TABLE_NAME, TEXT_MESSAGE_ID);
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            logger.debug("Executing statement: {}", statement);
            int result = statement.executeUpdate();
            connection.commit();
            if(result == 1){
                logger.info("message Deleted Successfully, {}", findById(id));
                cao.delete(id);
                return true;
            }
            else
                logger.error("Error Deleting message, {}", findById(id));
        } catch (SQLException e) {
            logger.error(e);
            rollback(connection);
        } finally {
            executeFinalBlock(connection, statement);
        }
        return false;
    }
    @Deprecated
    @Override
    public boolean update(TextMessage entity) {
        throw new UnsupportedOperationException();
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
            logger.info("{} is Cached", this.getClass().getSimpleName());
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            executeFinalBlock(connection, statement, rs);
        }

    }
    /*messeges of given users sorted by send time*/
    public List<TextMessage> getTextMessagesOfGivenUsers(int senderId, int receiverId){
        if (!isCached.get()) cache();
        List<TextMessage> m1 = cao.findAll().stream().filter(s->s.getSenderId()==senderId && s.getReceiverId() == receiverId).collect(Collectors.toList());
        List<TextMessage> m2 = cao.findAll().stream().filter(s->s.getSenderId()==receiverId && s.getReceiverId() == senderId).collect(Collectors.toList());
        m1.addAll(m2);
        m1.sort(Comparator.comparing(Message::getTimestamp));
        return m1;
    }

    private class TextMessageMapper implements DBRowMapper<TextMessage> {
        @Override
        public TextMessage mapRow(ResultSet rs) {
            try {
                int textMessageId = rs.getInt(TEXT_MESSAGE_ID);
                int senderId = rs.getInt(SENDER_ID);
                int receiverId = rs.getInt(RECEIVER_ID);
                Timestamp sendDate = rs.getTimestamp(DATE_SENT);
                String messageSent = rs.getString(MESSAGE_SENT);
                return new TextMessage(textMessageId, senderId, receiverId, sendDate.toLocalDateTime(), messageSent);
            } catch (SQLException e) {
                logger.error(e);
            }
            return null;
        }
    }

}
