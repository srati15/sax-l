package datatypes;


public class Announcement {
    private int id;
    private String announcementText;
    private boolean active = true;
    private String hyperLink;

    public Announcement(String announcementText, String hyperLink, Boolean active) {
        this.announcementText = announcementText;
        this.active = active;
        this.hyperLink = hyperLink;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getAnnouncementText() {
        return announcementText;
    }

    public boolean isActive() {
        return active;
    }

    public String getHyperLink() {
        return hyperLink;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
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
}
