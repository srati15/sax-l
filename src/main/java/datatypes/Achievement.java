package datatypes;

import java.util.Objects;

public class Achievement extends Domain<Integer>{
    private final String achievementName;

    public Achievement(String achievementName) {
        this.achievementName = achievementName;
    }

    public String getAchievementName() {
        return achievementName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Achievement that = (Achievement) o;
        return Objects.equals(achievementName, that.achievementName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(achievementName);
    }

    @Override
    public String toString() {
        return "Achievement{" +
                "achievementName='" + achievementName + '\'' +
                ", id=" + id +
                '}';
    }
}
