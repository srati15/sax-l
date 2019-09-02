package enums;

public enum DaoType {
    User(1),
    FriendRequest(3),
    Announcement(4),
    TextMessage(5),
    UserAchievement(9),
    Activity(11),
    AdminMessage(12),
    AdminReply(13);
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
