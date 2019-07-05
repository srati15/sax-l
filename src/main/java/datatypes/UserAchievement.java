package datatypes;

public class UserAchievement extends Domain<Integer> {
    private int userId;
    private Achievement achievement;

    public UserAchievement(int userId, Achievement achievement) {
        this.userId = userId;
        this.achievement = achievement;
    }

    public int getUserId() {
        return userId;
    }

    public Achievement getAchievement() {
        return achievement;
    }

    @Override
    public String toString() {
        return "UserAchievement{" +
                "userId=" + userId +
                ", achievement=" + achievement +
                ", id=" + id +
                '}';
    }
}
