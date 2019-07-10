package datatypes.user;

import datatypes.Domain;

import java.util.Objects;

public class Achievement extends Domain<Integer> {
    private final String achievementName;
    private final String achievementCriteria;
    public Achievement(String achievementName, String achievementCriteria) {
        this.achievementName = achievementName;
        this.achievementCriteria = achievementCriteria;
    }

    public String getAchievementName() {
        return achievementName;
    }

    public String getAchievementCriteria() {
        return achievementCriteria;
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
                ", achievementCriteria='" + achievementCriteria + '\'' +
                ", id=" + id +
                '}';
    }
}
