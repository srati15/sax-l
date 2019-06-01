package enums;

public enum RequestStatus {
    Pending(1),
    Accepted(2),
    Rejected(3);
    private int value;
    RequestStatus(int value) {
        this.value = value;
    }
}
