package datatypes;

import java.time.LocalDateTime;

public class Activity extends Domain<Integer> {
    private int userId;
    private String activityName;
    private LocalDateTime dateTime;

    public Activity(int userId, String activityName, LocalDateTime dateTime) {
        this.userId = userId;
        this.activityName = activityName;
        this.dateTime = dateTime;
    }

    public Activity(int id, int userId, String activityName, LocalDateTime dateTime) {
        this(userId, activityName, dateTime);
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public String getActivityName() {
        return activityName;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "userId=" + userId +
                ", activityName='" + activityName + '\'' +
                ", dateTime=" + dateTime +
                ", id=" + id +
                "} ";
    }
}
