package dao;

import datatypes.Announcement;
import datatypes.User;
import enums.UserType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnnouncementDao implements Dao<Announcement> {
    private final Connection connection;

    public AnnouncementDao(Connection connection) {
        this.connection = connection;
    }
    @Override
    public Announcement findById(int id) {
        return null;
    }

    @Override
    public void insert(Announcement entity) {

    }

    @Override
    public List<Announcement> findAll() {
        List<Announcement> announcements = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM announcements")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                if (rs.getBoolean("active")) {
                    String announcementText = rs.getString("announcement_text");
                    String hyperLink = rs.getString("hyperlink");
                    announcements.add(new Announcement(announcementText, hyperLink));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return announcements;
    }

    @Override
    public void deleteById(int id) {

    }
}
