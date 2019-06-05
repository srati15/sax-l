package database.mapper;

import datatypes.User;
import enums.UserType;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements DBRowMapper<User> {
    public static final String USER_ID = "user_id";
    public static final String USER_NAME = "user_name";
    public static final String USER_PASSWORD = "pass";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String USER_MAIL = "mail";
    public static final String USER_TYPE = "user_type";
    public static final String TABLE_NAME = "users";

    @Override
    public User mapRow(ResultSet rs) {
        try {
            int userId = rs.getInt(USER_ID);
            String userName = rs.getString(USER_NAME);
            String passwordHash = rs.getString(USER_PASSWORD);
            String firstName = rs.getString(FIRST_NAME);
            String lastName = rs.getString(LAST_NAME);
            String mail = rs.getString(USER_MAIL);
            UserType type = UserType.getById(rs.getInt(USER_TYPE));
            User user = new User(userName, passwordHash, firstName, lastName, mail);
            user.setUserType(type);
            user.setId(userId);
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
