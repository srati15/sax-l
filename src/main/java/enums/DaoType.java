package enums;

public enum DaoType {
    User(1),
    Quiz(2),
    FriendRequest(3),
    Announcement(4),
    TextMessage(5),
    Answer(6),
    Question(7),
    QuizResult(8),
    UserAchievement(9),
    QuizChallenge(10),
    Activity(11);
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
