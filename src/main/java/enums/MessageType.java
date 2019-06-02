package enums;

public enum MessageType {
    TextMessage(1),
    FriendRequest(2),
    Challenge(3);
    private int value;
    MessageType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    public MessageType getByValue(int value) {
        for (MessageType messageType: MessageType.values()){
            if (messageType.value == value) return messageType;
        }
        return null;
    }
}
