package datatypes.user;

import datatypes.Domain;
import datatypes.user.Achievement;

public class UserAchievement extends Domain<Integer> {
    private final int userId;
    private final Achievement achievement;

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
