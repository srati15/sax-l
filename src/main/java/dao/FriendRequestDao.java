package dao;

import database.CreateConnection;
import datatypes.messages.FriendRequest;
import enums.DaoType;
import enums.RequestStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static dao.FinalBlockExecutor.executeFinalBlock;

public class FriendRequestDao implements Dao<Integer, FriendRequest> {

    @Override
    public FriendRequest findById(Integer id) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("SELECT * FROM friend_requests WHERE id = ?");
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                int senderId = rs.getInt("sender_id");
                int receiverId = rs.getInt("receiver_id");
                int requestStatus = rs.getInt("request_status");
                RequestStatus status = RequestStatus.getByValue(requestStatus);
                Timestamp sendDate = rs.getTimestamp("send_date");

                FriendRequest friendRequest = new FriendRequest(id, senderId, receiverId, status, sendDate);
                return friendRequest;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            executeFinalBlock(connection, statement);
        }
        return null;
    }

    @Override
    public void insert(FriendRequest entity) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("INSERT INTO friend_requests (sender_id, receiver_id, request_status, send_date) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, entity.getSenderId());
            statement.setInt(2, entity.getReceiverId());
            statement.setInt(3, entity.getStatus().getValue());
            statement.setTimestamp(4, entity.getTimestamp());
            int result = statement.executeUpdate();
            if(result == 1){
                ResultSet rs = statement.getGeneratedKeys();
                rs.next();
                int id = rs.getInt(1);
                entity.setId(id);
                System.out.println("Request Added Successfully");
            }else
                System.out.println("Error Adding Request");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            executeFinalBlock(connection, statement);
        }

    }

    @Override
    public List<FriendRequest> findAll() {
        List<FriendRequest> list = new ArrayList<>();
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("SELECT * FROM friend_requests");
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                int senderId = rs.getInt("sender_id");
                int receiverId = rs.getInt("receiver_id");
                int requestStatus = rs.getInt("request_status");
                RequestStatus status = RequestStatus.getByValue(requestStatus);
                Timestamp sendDate = rs.getTimestamp("send_date");

                FriendRequest friendRequest = new FriendRequest(id, senderId, receiverId, status, sendDate);
                list.add(friendRequest);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            executeFinalBlock(connection, statement);
        }

        return list;
    }

    @Override
    public void deleteById(Integer id) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("DELETE FROM friend_requests WHERE id = ?");
            statement.setInt(1, id);
            int result = statement.executeUpdate();
            if(result == 1)
                System.out.println("Request Deleted Successfully");
            else
                System.out.println("Error Deleting Request");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            executeFinalBlock(connection, statement);
        }
    }

    @Override
    public void update(FriendRequest entity) {
        // TODO: 6/2/19  
    }

    @Override
    public DaoType getDaoType() {
        return DaoType.FriendRequest;
    }

}
