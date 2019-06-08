package dao;

import database.CreateConnection;
import database.mapper.DBRowMapper;
import database.mapper.FriendRequestMapper;
import datatypes.messages.FriendRequest;
import enums.DaoType;
import enums.RequestStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import static dao.QueryGenerator.*;
import static dao.FinalBlockExecutor.executeFinalBlock;
import static database.mapper.FriendRequestMapper.*;
public class FriendRequestDao implements Dao<Integer, FriendRequest> {
    private DBRowMapper<FriendRequest> mapper = new FriendRequestMapper();
    @Override
    public FriendRequest findById(Integer id) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            String query = getSelectQuery(TABLE_NAME, REQUEST_ID);
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
    public void insert(FriendRequest entity) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            String query = getInsertQuery(TABLE_NAME, SENDER_ID, RECEIVER_ID, REQUEST_STATUS, DATE_SENT );
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, entity.getSenderId());
            statement.setInt(2, entity.getReceiverId());
            statement.setInt(3, entity.getStatus().getValue());
            statement.setTimestamp(4, entity.getTimestamp());
            int result = statement.executeUpdate();
            if(result == 1){
                rs = statement.getGeneratedKeys();
                rs.next();
                int id = rs.getInt(1);
                entity.setId(id);
                System.out.println("Request Added Successfully");
            }else
                System.out.println("Error Adding Request");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            executeFinalBlock(connection, statement, rs);
        }

    }

    @Override
    public List<FriendRequest> findAll() {
        List<FriendRequest> list = new ArrayList<>();
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
            String query = getDeleteQuery(TABLE_NAME, REQUEST_ID);
            statement = connection.prepareStatement(query);
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

    public FriendRequest findBySenderReceiverId(int senderId, int receiverId) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            String query = getSelectQuery(TABLE_NAME, SENDER_ID, RECEIVER_ID);
            statement = connection.prepareStatement(query);
            statement.setInt(1, senderId);
            statement.setInt(2, receiverId);
            System.out.println(statement);
            rs = statement.executeQuery();
            if(rs.next()){
                return (mapper.mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            executeFinalBlock(connection, statement);
        }
        return null;
    }
}
