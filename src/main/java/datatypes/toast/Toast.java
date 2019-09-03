package datatypes.toast;

import datatypes.Domain;
import datatypes.user.Person;

import java.time.LocalDateTime;
import java.util.Objects;

public class Toast extends Domain<Integer> {
    private int authorId;
    private String title;
    private String toastText;
    private LocalDateTime dateCreated;

    public Toast(int authorId, String title, String toastText, LocalDateTime dateCreated) {
        this.authorId = authorId;
        this.title = title;
        this.toastText = toastText;
        this.dateCreated = dateCreated;
    }
    public Toast(int id, int authorId, String title, String toastText, LocalDateTime dateCreated) {
        this.id = id;
        this.authorId = authorId;
        this.title = title;
        this.toastText = toastText;
        this.dateCreated = dateCreated;
    }

    public int getAuthorId() {
        return authorId;
    }

    public String getToastText() {
        return toastText;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public String getTitle() {
        return title;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Toast toast = (Toast) o;
        return Objects.equals(authorId, toast.authorId) &&
                Objects.equals(toastText, toast.toastText) &&
                Objects.equals(dateCreated, toast.dateCreated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authorId, toastText, dateCreated);
    }

    @Override
    public String toString() {
        return "Toast{" +
                "authorId=" + authorId +
                ", title='" + title + '\'' +
                ", toastText='" + toastText + '\'' +
                ", dateCreated=" + dateCreated +
                ", id=" + id +
                '}';
    }
}
