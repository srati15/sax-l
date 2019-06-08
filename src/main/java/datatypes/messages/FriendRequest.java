package datatypes.messages;

import enums.MessageType;
import enums.RequestStatus;

import java.sql.Timestamp;

public class FriendRequest extends Message{
    private final RequestStatus status;
    private final MessageType messageType = MessageType.FriendRequest;
    private int id;
    public FriendRequest(int senderId, int recieverId, RequestStatus status, Timestamp sendDate) {
        super(senderId, recieverId, sendDate);
        this.status = status;
    }
    public FriendRequest(int id, int senderId, int recieverId, RequestStatus status, Timestamp sendDate) {
        this(senderId, recieverId, status, sendDate);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public MessageType getMessageType() {
        return messageType;
    }

    @Override
    public String toString() {
        return "FriendRequest{" +
                "status=" + status +
                ", messageType=" + messageType +
                ", id=" + id +
                "} " + super.toString();
    }
}
