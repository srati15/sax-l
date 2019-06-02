package datatypes.messages;

import enums.MessageType;

import java.sql.Timestamp;

public abstract class Message {
    private final Timestamp timestamp;
    private final int senderId;
    private final int receiverId;

    public Message(int senderId, int receiverId, Timestamp timestamp) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.timestamp = timestamp;
    }

    public Timestamp getTimestamp() {
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
