package dao;

import database.CreateConnection;
import database.mapper.DBRowMapper;
import database.mapper.FriendRequestMapper;
import datatypes.messages.FriendRequest;
import enums.DaoType;
import enums.RequestStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static dao.helpers.FinalBlockExecutor.executeFinalBlock;
import static dao.helpers.QueryGenerator.*;
import static database.mapper.FriendRequestMapper.*;

public class FriendRequestDao implements Dao<Integer, FriendRequest> {
    private DBRowMapper<FriendRequest> mapper = new FriendRequestMapper();
    private Cao<Integer, FriendRequest> cao = new Cao<>();

    @Override
    public FriendRequest findById(Integer id) {
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
        } finally {
            executeFinalBlock(connection, statement, rs);
        }

    }

    @Override
    public Collection<FriendRequest> findAll() {
        return cao.findAll();
    }

    @Override
    public void deleteById(Integer id) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        try {
            String query = getDeleteQuery(TABLE_NAME, REQUEST_ID);
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            int result = statement.executeUpdate();
            if (result == 1) {
                System.out.println("Request Deleted Successfully");
                cao.delete(id);
            } else
                System.out.println("Error Deleting Request");
        } catch (SQLException e) {
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
            if (result == 1) {
                System.out.println("Request accepted Successfully");
                cao.add(entity);
            } else
                System.out.println("Error accepting Request");
        } catch (SQLException e) {
            e.printStackTrace();
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

    public List<FriendRequest> getPendingRequestsFor(int receiverId) {
        return cao.findAll().stream().filter(s -> s.getReceiverId() == receiverId).collect(Collectors.toList());
    }

    public List<Integer> getFriendsIdsFor(int id) {
        List<Integer> friendsIds = new ArrayList<>();
        cao.findAll().stream().filter(s -> s.getReceiverId() == id && s.getStatus() == RequestStatus.Accepted).forEach(s -> friendsIds.add(s.getSenderId()));
        cao.findAll().stream().filter(s -> s.getSenderId() == id && s.getStatus() == RequestStatus.Accepted).forEach(s -> friendsIds.add(s.getReceiverId()));
        return friendsIds;
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
                FriendRequest request = mapper.mapRow(rs);
                cao.add(request);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            executeFinalBlock(connection, statement, rs);
        }
    }
}
