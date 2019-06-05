package database.mapper;

import datatypes.Announcement;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AnnouncementMapper implements DBRowMapper<Announcement> {
    public static final String ANNOUNCEMENT_ID = "id";
    public static final String ANNOUNCEMENT_TEXT = "announcement_text";
    public static final String HYPERLINK = "hyperlink";
    public static final String STATUS = "active";
    public static final String TABLE_NAME = "announcements";

    @Override
    public Announcement mapRow(ResultSet rs) {
        String txt = null;
        try {
            int id = rs.getInt(ANNOUNCEMENT_ID);
            txt = rs.getString(ANNOUNCEMENT_TEXT);
            String hyperLink = rs.getString(HYPERLINK);
            Boolean isActive = rs.getBoolean(STATUS);
            Announcement announcement = new Announcement(id,txt,hyperLink, isActive);
            return announcement;
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return null;
    }
}
