package database.mapper;

import datatypes.messages.TextMessage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class TextMessageMapper implements DBRowMapper<TextMessage> {
    public static final String TEXT_MESSAGE_ID= "text_message_id";
    public static final String SENDER_ID = "sender_id";
    public static final String RECEIVER_ID = "receiver_id";
    public static final String DATE_SENT = "date_sent";
    public static final String MESSAGE_SENT = "message_text";
    public static final String TABLE_NAME = "text_message";

    @Override
    public TextMessage mapRow(ResultSet rs) {
        try {
            int textMessageId = rs.getInt(TEXT_MESSAGE_ID);
            int senderId = rs.getInt(SENDER_ID);
            int receiverId = rs.getInt(RECEIVER_ID);
            Timestamp sendDate = rs.getTimestamp(DATE_SENT);
            String messageSent = rs.getString(MESSAGE_SENT);
            TextMessage tMessage = new TextMessage(textMessageId, senderId, receiverId, sendDate, messageSent);
            return tMessage;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
