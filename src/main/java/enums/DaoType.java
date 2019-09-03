package enums;

public enum DaoType {
    User(1),
    FriendRequest(2),
    Announcement(3),
    TextMessage(4),
    UserAchievement(5),
    Activity(6),
    AdminMessage(7),
    AdminReply(8),
    Toast(9);
    private final int value;
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
