package datatypes;

import anotations.Column;
import anotations.Entity;

@Entity(table = "announcements")
public class Announcement extends Domain<Integer>{
    @Column("announcement_text")
    private String announcementText;
    @Column("hyperlink")
    private String hyperLink;
    @Column("active")
    private boolean active;

    public Announcement() {
    }

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
