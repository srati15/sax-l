package dao.impl;

import dao.AdminMessageDao;
import dao.DBRowMapper;
import database.CreateConnection;
import datatypes.messages.AdminMessage;
import datatypes.promise.DaoResult;
import datatypes.promise.Promise;
import enums.DaoType;
import enums.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;

import static dao.helpers.FinalBlockExecutor.executeFinalBlock;
import static dao.helpers.FinalBlockExecutor.rollback;
import static dao.helpers.QueryGenerator.*;

@Repository
public class AdminMessageDaoImpl implements AdminMessageDao {
    private static final Logger logger = LogManager.getLogger(AdminMessageDaoImpl.class);

    private final DBRowMapper<AdminMessage> mapper = new AdminMessageMapper();
    private final Cao<Integer, AdminMessage> cao;
    private final AtomicBoolean isCached = new AtomicBoolean(false);
    private static final String MESSAGE_ID = "id";
    private static final String FIRST_NAME = "first_name";
    private static final String SENDER_MAIL = "sender_mail";
    private static final String MESSAGE_SUBJECT = "message_subject";
    private static final String MESSAGE_TEXT = "message_text";
    private static final String SEEN = "seen";
    private static final String DATE_SENT = "date_sent";

    private static final String TABLE_NAME = "inbox";

    public AdminMessageDaoImpl(Cao<Integer, AdminMessage> cao) {
        this.cao = cao;
    }

    @Override
    public AdminMessage findById(Integer id) {
        if (!isCached.get()) cache();
        return cao.findById(id);
    }

    @Override
    public Promise insert(AdminMessage entity) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            String query = getInsertQuery(TABLE_NAME, SENDER_MAIL, FIRST_NAME, MESSAGE_SUBJECT, MESSAGE_TEXT, DATE_SENT, SEEN);
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1,entity.getMail());
            statement.setString(2, entity.getName());
            statement.setString(3, entity.getSubject());
            statement.setString(4, entity.getMessageText());
            statement.setTimestamp(5, Timestamp.valueOf(entity.getTime()));
            statement.setBoolean(6, false);
            logger.debug("Executing statement: {}", statement);
            int result = statement.executeUpdate();
            connection.commit();
            if (result == 1) {
                rs = statement.getGeneratedKeys();
                rs.next();
                entity.setId(rs.getInt(1));
                cao.add(entity);
                logger.info("Message to admin inserted successfully {}", entity);
                return new DaoResult(Level.INFO, "Message sent, we will reply soon..");
            } else{
                logger.error("Error inserting Message to admin {}", entity);
            }
        } catch (SQLException e) {
            rollback(connection);
            logger.error(e);
        } finally {
            executeFinalBlock(connection, statement, rs);
        }
        return new DaoResult(Level.ERROR, "Message not sent, please try again..");
    }

    @Override
    public Collection<AdminMessage> findAll() {
        if (!isCached.get()) cache();
        return cao.findAll();
    }

    @Override
    public Promise deleteById(Integer id) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        try {
            String query = getDeleteQuery(TABLE_NAME, MESSAGE_ID);
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            logger.debug("Executing statement: {}", statement);
            int result = statement.executeUpdate();
            connection.commit();
            if (result == 1) {
                logger.info("Admin Message deleted sucessfully, {}", findById(id));
                cao.delete(id);
                return new DaoResult(Level.INFO, "Message deleted");
            } else {
                logger.error("Error deleting AdminMessage, {}", findById(id));
            }
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            executeFinalBlock(connection, statement);
        }
        return new DaoResult(Level.ERROR, "Error deleting Message");
    }

    public Promise update(AdminMessage adminMessage) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        try {
            String query = getUpdateQuery(TABLE_NAME, MESSAGE_ID, SEEN);
            statement = connection.prepareStatement(query);
            statement.setBoolean(1, adminMessage.isSeen());
            statement.setInt(2, adminMessage.getId());
            logger.debug("Executing statement: {}", statement);
            int result = statement.executeUpdate();
            connection.commit();
            if (result == 1) {
                logger.info("Admin Message Updated sucessfully, {}", adminMessage);
                cao.add(adminMessage);
                return new DaoResult(Level.INFO, "Message updated");
            } else {
                logger.error("Error deleting AdminMessage, {}", adminMessage);
            }
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            executeFinalBlock(connection, statement);
        }
        return new DaoResult(Level.ERROR, "Error updating Message");
    }

    @Override
    public DaoType getDaoType() {
        return DaoType.AdminMessage;
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
                AdminMessage AdminMessage = mapper.mapRow(rs);
                cao.add(AdminMessage);
            }
            isCached.set(true);
            logger.info("{} is Cached", this.getClass().getSimpleName());
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            executeFinalBlock(connection, statement);
        }
    }

    private class AdminMessageMapper implements DBRowMapper<AdminMessage> {
        @Override
        public AdminMessage mapRow(ResultSet rs) {
            try {
                int AdminMessageId = rs.getInt(MESSAGE_ID);
                String senderName = rs.getString(FIRST_NAME);
                String mail = rs.getString(SENDER_MAIL);
                String subject = rs.getString(MESSAGE_SUBJECT);
                String message = rs.getString(MESSAGE_TEXT);
                LocalDateTime time = rs.getTimestamp(DATE_SENT).toLocalDateTime();
                boolean seen = rs.getBoolean(SEEN);
                AdminMessage AdminMessage = new AdminMessage(senderName, mail, subject, message, time, seen);
                AdminMessage.setId(AdminMessageId);
                return AdminMessage;
            } catch (SQLException e) {
                logger.error(e);
            }
            return null;
        }
    }

}
