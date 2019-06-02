package enums;

public enum DaoType {
    User(1),
    Quiz(2),
    FriendRequest(3),
    Announcement(4);
    private int value;
    DaoType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    public static DaoType getByValue(int value) {
        for (DaoType daoType: DaoType.values()) {
            if (daoType.value == value) return daoType;
        }
        return null;
    }
}
