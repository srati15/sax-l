package datatypes;

public class Announcement extends Domain<Integer>{
    private final int userId;
    private final String announcementText;
    private final String hyperLink;
    private final boolean active;

    public Announcement(int userId, String announcementText, String hyperLink, Boolean active) {
        this.userId = userId;
        this.announcementText = announcementText;
        this.active = active;
        this.hyperLink = hyperLink;
    }

    public Announcement(int id, int userId, String announcementText, String hyperLink, boolean active) {
        this.id = id;
        this.userId = userId;
        this.announcementText = announcementText;
        this.hyperLink = hyperLink;
        this.active = active;
    }

    public int getUserId() {
        return userId;
    }

    public String getAnnouncementText() {
        return announcementText;
    }

    public String getHyperLink() {
        return hyperLink;
    }

    @Override
    public String toString() {
        return "Announcement{" +
                "id=" + id +
                ", announcementText='" + announcementText + '\'' +
                ", active=" + active +
                ", hyperLink='" + hyperLink + '\'' +
                '}';
    }

    public boolean isActive() {
        return active;
    }
}
