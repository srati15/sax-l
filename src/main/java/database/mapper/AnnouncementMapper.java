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
        try {
            int id = rs.getInt(ANNOUNCEMENT_ID);
            String txt = rs.getString(ANNOUNCEMENT_TEXT);
            String hyperLink = rs.getString(HYPERLINK);
            boolean isActive = rs.getBoolean(STATUS);
            return new Announcement(id,txt,hyperLink, isActive);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
