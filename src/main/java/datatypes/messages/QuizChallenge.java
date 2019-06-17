package datatypes.messages;

import enums.MessageType;
import enums.RequestStatus;

import java.sql.Timestamp;

public class QuizChallenge extends Message {
    private final RequestStatus requestStatus;
    private MessageType messageType = MessageType.Challenge;
    public QuizChallenge(int senderId, int receiverId, Timestamp timestamp, RequestStatus requestStatus) {
        super(senderId, receiverId, timestamp);
        this.requestStatus = requestStatus;
    }
    public QuizChallenge(int id, int senderId, int receiverId, Timestamp timestamp, RequestStatus requestStatus) {
        super(senderId, receiverId, timestamp);
        this.requestStatus = requestStatus;
        this.id = id;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
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
        return "QuizChallenge{" +
                "requestStatus=" + requestStatus +
                ", id=" + id +
                ", messageType=" + messageType +
                "} " + super.toString();
    }
}
