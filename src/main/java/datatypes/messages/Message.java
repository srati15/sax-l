package datatypes.messages;

import datatypes.Domain;
import enums.MessageType;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public abstract class Message extends Domain<Integer> {
    private LocalDateTime timestamp;
    private int senderId;
    private int receiverId;
    public Message(){}
    public Message(int senderId, int receiverId, LocalDateTime timestamp) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.timestamp = timestamp;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getSenderId() {
        return senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public abstract MessageType getMessageType();

    @Override
    public String toString() {
        return "Message{" +
                "timestamp=" + timestamp +
                ", senderId=" + senderId +
                ", receiverId=" + receiverId +
                '}';
    }
}
