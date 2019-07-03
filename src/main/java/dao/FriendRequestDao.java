package dao;

import database.CreateConnection;
import database.mapper.DBRowMapper;
import database.mapper.FriendRequestMapper;
import datatypes.Person;
import datatypes.User;
import datatypes.messages.FriendRequest;
import enums.DaoType;
import enums.RequestStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static dao.helpers.FinalBlockExecutor.executeFinalBlock;
import static dao.helpers.QueryGenerator.*;
import static database.mapper.FriendRequestMapper.*;

public class FriendRequestDao implements Dao<Integer, FriendRequest> {
    private DBRowMapper<FriendRequest> mapper = new FriendRequestMapper();
    private Cao<Integer, FriendRequest> cao = new Cao<>();
    public FriendRequestDao(){
    }
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
            FriendRequest request = findById(id);
            String query = getDeleteQuery(TABLE_NAME, REQUEST_ID);
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            int result = statement.executeUpdate();
            if (result == 1) {
                User receiver = UserDao.getInstance().findById(request.getReceiverId());
                receiver.getPendingFriendRequests().removeIf(person->person.getId().equals(request.getSenderId()));
                receiver.getFriends().removeIf(person -> person.getId() == request.getSenderId());
                User sender = UserDao.getInstance().findById(request.getSenderId());
                sender.getFriends().removeIf(person -> person.getId() == request.getReceiverId());
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
                User sender = UserDao.getInstance().findById(entity.getSenderId());
                User receiver = UserDao.getInstance().findById(entity.getReceiverId());
                receiver.getPendingFriendRequests().removeIf(request-> request.getId().equals(sender.getId()));
                sender.getPendingFriendRequests().removeIf(request->request.getId().equals(receiver.getId()));
                List<Person> friends = sender.getFriends();
                friends.add(receiver);
                sender.setFriends(friends);
                List<Person> receiverFriends = receiver.getFriends();
                receiverFriends.add(sender);
                receiver.setFriends(receiverFriends);
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

    public List<Person> getPendingRequestsFor(int receiverId) {
        List<Integer> pendingRequests = new ArrayList<>();
        cao.findAll().stream().filter(s -> s.getReceiverId() == receiverId && s.getStatus()==RequestStatus.Pending).
                forEach(s->pendingRequests.add(s.getSenderId()));
        List<Person> friendRequests = new ArrayList<>();
        pendingRequests.forEach(request->friendRequests.add(UserDao.getInstance().findById(request)));
        return friendRequests;
    }

    public List<Person> getFriendsForUser(int id) {
        List<Integer> friendsIds = new ArrayList<>();
        cao.findAll().stream().filter(s -> s.getReceiverId() == id && s.getStatus() == RequestStatus.Accepted).forEach(s -> friendsIds.add(s.getSenderId()));
        cao.findAll().stream().filter(s -> s.getSenderId() == id && s.getStatus() == RequestStatus.Accepted).forEach(s -> friendsIds.add(s.getReceiverId()));
        List<Person> people = new ArrayList<>();
        friendsIds.forEach(friendId-> people.add(UserDao.getInstance().findById(friendId)));
        return people;
    }

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
