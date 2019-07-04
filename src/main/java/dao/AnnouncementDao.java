package dao;

import database.CreateConnection;
import database.mapper.AnnouncementMapper;
import database.mapper.DBRowMapper;
import datatypes.Announcement;
import enums.DaoType;

import java.sql.*;
import java.util.Collection;

import static dao.helpers.FinalBlockExecutor.executeFinalBlock;
import static dao.helpers.FinalBlockExecutor.rollback;
import static dao.helpers.QueryGenerator.*;
import static database.mapper.AnnouncementMapper.*;
public class AnnouncementDao implements Dao<Integer, Announcement> {
    private DBRowMapper<Announcement> mapper = new AnnouncementMapper();
    private Cao<Integer, Announcement> cao = new Cao<>();

    public AnnouncementDao(){
    }

    @Override
    public Announcement findById(Integer id) {
        return cao.findById(id);
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
            connection.commit();
            if (result == 1){
                System.out.println("Announcement inserted successfully");
                rs = statement.getGeneratedKeys();
                rs.next();
                entity.setId(rs.getInt(1));
                cao.add(entity);
            }
            else
                System.out.println("Error inserting announcement");
        } catch (SQLException e) {
            rollback(connection);
            e.printStackTrace();
        }finally {
            executeFinalBlock(connection, statement, rs);
        }
    }



    @Override
    public Collection<Announcement> findAll() {
        return cao.findAll();
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
            connection.commit();
            if(result == 1) {
                cao.delete(id);
                System.out.println("Announcement Deleted Successfully");
            }
            else
                System.out.println("Error Deleting Announcement");
        } catch (SQLException e) {
            rollback(connection);
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
            connection.commit();
            if (result == 1) {
                cao.add(entity);
                System.out.println("Announcement updated sucessfully");
            }
            else System.out.println("Error updating announcement");
        } catch (SQLException e) {
            rollback(connection);
            e.printStackTrace();
        }finally {
            executeFinalBlock(connection, statement);
        }
    }

    @Override
    public DaoType getDaoType() {
        return DaoType.Announcement;
    }

    public void cache() {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            String query = getSelectQuery(TABLE_NAME);
            statement = connection.prepareStatement(query);
            rs = statement.executeQuery();
            while(rs.next()){
                Announcement announcement = mapper.mapRow(rs);
                cao.add(announcement);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            executeFinalBlock(connection, statement, rs);
        }
    }
}
