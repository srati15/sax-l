package enums;

public enum RequestStatus {
    Pending(1),
    Accepted(2),
    Rejected(3);
    private final int value;
    RequestStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    public static RequestStatus getByValue(int value) {
        for (RequestStatus requestStatus: RequestStatus.values()) {
            if (requestStatus.value == value) return requestStatus;
        }
        return null;
    }
}
