package database.mapper;

import datatypes.User;
import datatypes.messages.FriendRequest;
import enums.RequestStatus;
import enums.UserType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class FriendRequestMapper implements DBRowMapper<FriendRequest> {
    public static final String SENDER_ID = "sender_id";
    public static final String RECEIVER_ID = "receiver_id";
    public static final String REQUEST_STATUS = "request_status";
    public static final String DATE_SENT = "send_date";
    public static final String REQUEST_ID = "id";
    public static final String TABLE_NAME = "friend_requests";

    @Override
    public FriendRequest mapRow(ResultSet rs) {
        try {
            int requestId = rs.getInt(REQUEST_ID);
            int senderId = rs.getInt(SENDER_ID);
            int receiverId = rs.getInt(RECEIVER_ID);
            int requestStatus = rs.getInt(REQUEST_STATUS);
            RequestStatus status = RequestStatus.getByValue(requestStatus);
            Timestamp sendDate = rs.getTimestamp(DATE_SENT);
            FriendRequest friendRequest = new FriendRequest(requestId, senderId, receiverId, status, sendDate);
            return friendRequest;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
