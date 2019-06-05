package dao;

import database.CreateConnection;
import database.mapper.AnnouncementMapper;
import database.mapper.DBRowMapper;
import datatypes.Announcement;
import enums.DaoType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static dao.FinalBlockExecutor.executeFinalBlock;
import static dao.QueryGenerator.*;
import static database.mapper.AnnouncementMapper.*;
public class AnnouncementDao implements Dao<Integer, Announcement> {
    private DBRowMapper<Announcement> mapper = new AnnouncementMapper();
    @Override
    public Announcement findById(Integer id) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            String query = getSelectQuery(TABLE_NAME, ANNOUNCEMENT_ID);
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            rs = statement.executeQuery();
            if (rs.next()) return mapper.mapRow(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            executeFinalBlock(connection, statement, rs);
        }

        return null;
    }



    @Override
    public void insert(Announcement entity) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            String query = getInsertQuery(TABLE_NAME, ANNOUNCEMENT_TEXT, HYPERLINK, STATUS);
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, entity.getAnnouncementText());
            statement.setString(2, entity.getHyperLink());
            statement.setBoolean(3, entity.isActive());
            int result = statement.executeUpdate();
            if (result == 1)
                System.out.println("Announcement inserted successfully");
            else
                System.out.println("Error inserting announcement");
            rs = statement.getGeneratedKeys();
            rs.next();
            entity.setId(rs.getInt(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            executeFinalBlock(connection, statement, rs);
        }
    }

    @Override
    public List<Announcement> findAll() {
        List<Announcement> list = new ArrayList<>();
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
        }finally {
            executeFinalBlock(connection, statement, rs);
        }
        return list;
    }

    @Override
    public void deleteById(Integer id) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        try {
            String query = getDeleteQuery(TABLE_NAME, ANNOUNCEMENT_ID);
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            int result = statement.executeUpdate();
            if(result == 1) System.out.println("Announcement Deleted Successfully");
            else
                System.out.println("Error Deleting Announcement");
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            executeFinalBlock(connection,statement);
        }
    }

    @Override
    public void update(Announcement entity) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        try {
            String query = getUpdateQuery(TABLE_NAME, ANNOUNCEMENT_ID, ANNOUNCEMENT_TEXT, HYPERLINK, STATUS);
            statement = connection.prepareStatement(query);
            statement.setString(1, entity.getAnnouncementText());
            statement.setString(2, entity.getHyperLink());
            statement.setBoolean(3, entity.isActive());
            statement.setInt(4, entity.getId());
            int result = statement.executeUpdate();
            if (result == 1) System.out.println("Announcement updated sucessfully");
            else System.out.println("Error updating announcement");
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            executeFinalBlock(connection, statement);
        }
    }

    @Override
    public DaoType getDaoType() {
        return DaoType.Announcement;
    }
}
