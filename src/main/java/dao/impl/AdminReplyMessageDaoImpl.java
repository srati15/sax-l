package dao.impl;

import dao.AdminReplyMessageDao;
import dao.DBRowMapper;
import database.CreateConnection;
import datatypes.messages.AdminReply;
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
public class AdminReplyMessageDaoImpl implements AdminReplyMessageDao {
    private static final Logger logger = LogManager.getLogger(AdminReplyMessageDaoImpl.class);

    private final DBRowMapper<AdminReply> mapper = new AdminReplyMapper();
    private final Cao<Integer, AdminReply> cao ;
    private final AtomicBoolean isCached = new AtomicBoolean(false);
    private static final String REPLYY_ID = "id";
    private static final String MESSAGE_ID = "message_id";
    private static final String REPLY_TEXT = "reply_text";
    private static final String DATE_SENT = "date_sent";

    private static final String TABLE_NAME = "reply_message";

    public AdminReplyMessageDaoImpl(Cao<Integer, AdminReply> cao) {
        this.cao = cao;
    }

    @Override
    public AdminReply findById(Integer id) {
        if (!isCached.get()) cache();
        return cao.findById(id);
    }

    @Override
    public Promise insert(AdminReply entity) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            String query = getInsertQuery(TABLE_NAME, MESSAGE_ID, REPLY_TEXT, DATE_SENT);
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1,entity.getMessageId());
            statement.setString(2, entity.getReplyText());
            statement.setTimestamp(3, Timestamp.valueOf(entity.getDateSent()));
            logger.debug("Executing statement: {}", statement);
            int result = statement.executeUpdate();
            connection.commit();
            if (result == 1) {
                rs = statement.getGeneratedKeys();
                rs.next();
                entity.setId(rs.getInt(1));
                cao.add(entity);
                logger.info("Message from admin inserted successfully {}", entity);
                return new DaoResult(Level.INFO, "Message sent");
            } else
                logger.error("Error inserting Message from admin {}", entity);
        } catch (SQLException e) {
            rollback(connection);
            logger.error(e);
        } finally {
            executeFinalBlock(connection, statement, rs);
        }
        return new DaoResult(Level.ERROR, "Message not sent, please try again..");
    }

    @Override
    public Collection<AdminReply> findAll() {
        if (!isCached.get()) cache();
        return cao.findAll();
    }

    @Override
    public Promise deleteById(Integer id) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        try {
            String query = getDeleteQuery(TABLE_NAME, REPLYY_ID);
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            logger.debug("Executing statement: {}", statement);
            int result = statement.executeUpdate();
            connection.commit();
            if (result == 1) {
                logger.info("Admin's Message deleted sucessfully, {}", findById(id));
                cao.delete(id);
                return new DaoResult(Level.INFO, "Message deleted");
            } else {
                logger.error("Error deleting Admin's Reply, {}", findById(id));
            }
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            executeFinalBlock(connection, statement);
        }
        return new DaoResult(Level.ERROR, "Error deleting message, please try again..");
    }

    @Deprecated
    public Promise update(AdminReply AdminReply) {
        throw new UnsupportedOperationException();
    }

    @Override
    public DaoType getDaoType() {
        return DaoType.AdminReply;
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
                AdminReply AdminReply = mapper.mapRow(rs);
                cao.add(AdminReply);
            }
            isCached.set(true);
            logger.info("{} is Cached", this.getClass().getSimpleName());
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            executeFinalBlock(connection, statement);
        }
    }

    private static class AdminReplyMapper implements DBRowMapper<AdminReply> {
        @Override
        public AdminReply mapRow(ResultSet rs) {
            try {
                int AdminReplyId = rs.getInt(REPLYY_ID);
                int messageId = rs.getInt(MESSAGE_ID);
                String replyText = rs.getString(REPLY_TEXT);
                LocalDateTime time = rs.getTimestamp(DATE_SENT).toLocalDateTime();
                AdminReply AdminReply = new AdminReply(messageId, replyText, time);
                AdminReply.setId(AdminReplyId);
                return AdminReply;
            } catch (SQLException e) {
                logger.error(e);
            }
            return null;
        }
    }

}
