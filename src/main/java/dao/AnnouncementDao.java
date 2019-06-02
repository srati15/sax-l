package dao;

import database.CreateConnection;
import datatypes.Announcement;
import datatypes.User;
import enums.UserType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import static dao.FinalBlockExecutor.executeFinalBlock;

public class AnnouncementDao implements Dao<Announcement> {

    @Override
    public Announcement findById(int id) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("SELECT * FROM announcements WHERE id = ?");
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                String txt = rs.getString("announcement_text");
                String hyperLink = rs.getString("hyperlink");
                Boolean isActive = rs.getBoolean("active");

                Announcement announcement = new Announcement(id,txt,hyperLink, isActive);
                return  announcement;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            executeFinalBlock(connection, statement);
        }

        return null;
    }



    @Override
    public void insert(Announcement entity) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("INSERT INTO announcements (announcement_text, hyperlink, active) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, entity.getAnnouncementText());
            statement.setString(2, entity.getHyperLink());
            statement.setBoolean(3, entity.isActive());
            int result = statement.executeUpdate();
            if (result == 1)
                System.out.println("Announcement inserted successfully");
            else
                System.out.println("Error inserting announcement");
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            entity.setId(rs.getInt(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            executeFinalBlock(connection, statement);
        }
    }

    @Override
    public List<Announcement> findAll() {
        List<Announcement> list = new ArrayList<>();
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("SELECT * FROM announcements");
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String txt = rs.getString("announcement_text");
                String hyperLink = rs.getString("hyperlink");
                Boolean isActive = rs.getBoolean("active");
                Announcement announcement = new Announcement(id, txt,hyperLink, isActive);
                list.add(announcement);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            executeFinalBlock(connection, statement);
        }
        return list;
    }

    @Override
    public void deleteById(int id) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("DELETE FROM announcements WHERE id = ?");
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
            statement = connection.prepareStatement("UPDATE announcements set announcement_text = ?, hyperlink = ?, active = ? WHERE id=" + entity.getId());
            statement.setString(1, entity.getAnnouncementText());
            statement.setString(2, entity.getHyperLink());
            statement.setBoolean(3, entity.isActive());
            int result = statement.executeUpdate();
            if (result == 1) System.out.println("Announcement updated sucessfully");
            else System.out.println("Error updating announcement");
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            executeFinalBlock(connection, statement);
        }
    }
}
