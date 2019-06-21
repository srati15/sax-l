package datatypes.messages;

import anotations.Column;
import datatypes.Domain;
import enums.MessageType;

import java.sql.Timestamp;

public abstract class Message extends Domain<Integer> {
    @Column("date_sent")
    private Timestamp timestamp;
    @Column("sender_id")
    private int senderId;
    @Column("receiver_id")
    private int receiverId;
    public Message(){}
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
