package datatypes.messages;

import enums.MessageType;
import enums.RequestStatus;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class FriendRequest extends Message{
    private RequestStatus status;
    private final MessageType messageType = MessageType.FriendRequest;

    public FriendRequest(int senderId, int recieverId, RequestStatus status, LocalDateTime sendDate) {
        super(senderId, recieverId, sendDate);
        this.status = status;
    }
    public FriendRequest(int id, int senderId, int recieverId, RequestStatus status, LocalDateTime sendDate) {
        this(senderId, recieverId, status, sendDate);
        this.id = id;
    }


    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
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
