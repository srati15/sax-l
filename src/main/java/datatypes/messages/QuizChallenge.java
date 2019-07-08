package datatypes.messages;

import enums.MessageType;
import enums.RequestStatus;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class QuizChallenge extends Message {
    private final RequestStatus requestStatus;
    private final MessageType messageType = MessageType.Challenge;
    private final int quizId;
    public QuizChallenge(int senderId, int receiverId, LocalDateTime timestamp, int quizID, RequestStatus requestStatus) {
        super(senderId, receiverId, timestamp);
        quizId = quizID;
        this.requestStatus = requestStatus;
    }
    public QuizChallenge(int id, int senderId, int receiverId,int quizID, RequestStatus requestStatus,  LocalDateTime timestamp) {
        super(senderId, receiverId, timestamp);
        quizId = quizID;
        this.requestStatus = requestStatus;
        this.id = id;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public int getQuizId(){return quizId;}

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
