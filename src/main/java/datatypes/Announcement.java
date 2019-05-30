package datatypes;

public class Announcement {
    private String announcementText;
    private boolean active = true;
    private String hyperLink;

    public Announcement(String announcementText, String hyperLink) {
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
}
