package dao;

import database.CreateConnection;
import datatypes.messages.FriendRequest;
import enums.DaoType;
import enums.RequestStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;

import static dao.helpers.FinalBlockExecutor.executeFinalBlock;
import static dao.helpers.FinalBlockExecutor.rollback;
import static dao.helpers.QueryGenerator.*;

public class FriendRequestDao implements Dao<Integer, FriendRequest> {
    private static final Logger logger = LogManager.getLogger(FriendRequestDao.class);

    private final DBRowMapper<FriendRequest> mapper = new FriendRequestMapper();
    private final Cao<Integer, FriendRequest> cao = new Cao<>();
    private final AtomicBoolean isCached = new AtomicBoolean(false);
    private static final String SENDER_ID = "sender_id";
    private static final String RECEIVER_ID = "receiver_id";
    private static final String REQUEST_STATUS = "request_status";
    private static final String DATE_SENT = "date_sent";
    private static final String REQUEST_ID = "id";
    private static final String TABLE_NAME = "friend_requests";

    @Override
    public FriendRequest findById(Integer id) {
        if (!isCached.get()) cache();
        return cao.findById(id);
    }

    @Override
    public void insert(FriendRequest entity) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            String query = getInsertQuery(TABLE_NAME, SENDER_ID, RECEIVER_ID, REQUEST_STATUS, DATE_SENT);
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, entity.getSenderId());
            statement.setInt(2, entity.getReceiverId());
            statement.setInt(3, entity.getStatus().getValue());
            statement.setTimestamp(4, entity.getTimestamp());
            int result = statement.executeUpdate();
            connection.commit();
            if (result == 1) {
                rs = statement.getGeneratedKeys();
                rs.next();
                int id = rs.getInt(1);
                entity.setId(id);
                cao.add(entity);
                System.out.println("Request Added Successfully");
            } else
                System.out.println("Error Adding Request");
        } catch (SQLException e) {
            e.printStackTrace();
            rollback(connection);
        } finally {
            executeFinalBlock(connection, statement, rs);
        }

    }

    @Override
    public Collection<FriendRequest> findAll() {
        if (!isCached.get()) cache();
        return cao.findAll();
    }

    @Override
    public void deleteById(Integer id) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        try {
            FriendRequest request = findById(id);
            String query = getDeleteQuery(TABLE_NAME, REQUEST_ID);
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            int result = statement.executeUpdate();
            connection.commit();
            if (result == 1) {
                System.out.println("Request Deleted Successfully");
                cao.delete(id);
            } else
                System.out.println("Error Deleting Request");
        } catch (SQLException e) {
            rollback(connection);
            e.printStackTrace();
        } finally {
            executeFinalBlock(connection, statement);
        }
    }

    @Override
    public void update(FriendRequest entity) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        try {
            String query = getUpdateQuery(TABLE_NAME, REQUEST_ID, REQUEST_STATUS);
            statement = connection.prepareStatement(query);
            statement.setInt(1, entity.getStatus().getValue());
            statement.setInt(2, entity.getId());
            int result = statement.executeUpdate();
            connection.commit();
            if (result == 1) {
                System.out.println("Request accepted Successfully");
                cao.add(entity);
            } else
                System.out.println("Error accepting Request");
        } catch (SQLException e) {
            e.printStackTrace();
            rollback(connection);
        } finally {
            executeFinalBlock(connection, statement);
        }
    }

    @Override
    public DaoType getDaoType() {
        return DaoType.FriendRequest;
    }


    public FriendRequest findBySenderReceiverId(int senderId, int receiverId) {
        return cao.findAll().stream().filter(s -> s.getSenderId() == senderId && s.getReceiverId() == receiverId).findFirst().orElse(null);
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
            while (rs.next()) {
                FriendRequest request = mapper.mapRow(rs);
                cao.add(request);
            }
            isCached.set(true);
            logger.info("{} is Cached", this.getClass().getSimpleName());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            executeFinalBlock(connection, statement, rs);
        }
    }
    private class FriendRequestMapper implements DBRowMapper<FriendRequest> {
        @Override
        public FriendRequest mapRow(ResultSet rs) {
            try {
                int requestId = rs.getInt(REQUEST_ID);
                int senderId = rs.getInt(SENDER_ID);
                int receiverId = rs.getInt(RECEIVER_ID);
                int requestStatus = rs.getInt(REQUEST_STATUS);
                RequestStatus status = RequestStatus.getByValue(requestStatus);
                Timestamp sendDate = rs.getTimestamp(DATE_SENT);
                return new FriendRequest(requestId, senderId, receiverId, status, sendDate);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
