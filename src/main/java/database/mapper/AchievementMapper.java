package database.mapper;

import datatypes.Achievement;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AchievementMapper implements DBRowMapper<Achievement> {
    public static final String ACHIEVEMENT_ID = "id";
    public static final String ACHIEVEMENT_NAME = "achievement_name";
    public static final String TABLE_NAME = "achievements";
    @Override
    public Achievement mapRow(ResultSet rs) {
        try {
            int id = rs.getInt(ACHIEVEMENT_ID);
            String achievementName = rs.getString(ACHIEVEMENT_NAME);
            Achievement achievement = new Achievement(achievementName);
            achievement.setId(id);
            return achievement;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
