package datatypes;

import enums.RequestStatus;

import java.sql.Timestamp;

public class FriendRequest {
    private int senderId;
    private int recieverId;
    private RequestStatus status;
    private Timestamp sendDate;
    private int id;
    public FriendRequest(int senderId, int recieverId, RequestStatus status, Timestamp sendDate) {
        this.senderId = senderId;
        this.recieverId = recieverId;
        this.status = status;
        this.sendDate = sendDate;
    }

    public int getSenderId() {
        return senderId;
    }

    public int getRecieverId() {
        return recieverId;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public Timestamp getSendDate() {
        return sendDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "FriendRequest{" +
                "senderId=" + senderId +
                ", recieverId=" + recieverId +
                ", status=" + status +
                ", sendDate=" + sendDate +
                ", id=" + id +
                '}';
    }
}