package datatypes;


public class Announcement {
    private int id;
    private String announcementText;
    private String hyperLink;
    private boolean active;
    public Announcement(String announcementText, String hyperLink, Boolean active) {
        this.announcementText = announcementText;
        this.active = active;
        this.hyperLink = hyperLink;
    }

    public Announcement(int id, String announcementText, String hyperLink, boolean active) {
        this.id = id;
        this.announcementText = announcementText;
        this.hyperLink = hyperLink;
        this.active = active;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAnnouncementText() {
        return announcementText;
    }

    public String getHyperLink() {
        return hyperLink;
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

    public boolean isActive() {
        return active;
    }
}
