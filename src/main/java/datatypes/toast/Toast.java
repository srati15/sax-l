package datatypes.toast;

import datatypes.Domain;
import datatypes.user.Person;

import java.time.LocalDateTime;
import java.util.Objects;

public class Toast extends Domain<Integer> {
    private Person author;
    private String toastText;
    private LocalDateTime dateCreated;

    public Toast(Person author, String toastText, LocalDateTime dateCreated) {
        this.author = author;
        this.toastText = toastText;
        this.dateCreated = dateCreated;
    }
    public Toast(int id, Person author, String toastText, LocalDateTime dateCreated) {
        this.id = id;
        this.author = author;
        this.toastText = toastText;
        this.dateCreated = dateCreated;
    }

    public Person getAuthor() {
        return author;
    }

    public String getToastText() {
        return toastText;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Toast toast = (Toast) o;
        return Objects.equals(author, toast.author) &&
                Objects.equals(toastText, toast.toastText) &&
                Objects.equals(dateCreated, toast.dateCreated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(author, toastText, dateCreated);
    }

    @Override
    public String toString() {
        return "Toast{" +
                "author=" + author.getUserName() +
                ", toastText='" + toastText + '\'' +
                ", dateCreated=" + dateCreated +
                ", id=" + id +
                '}';
    }
}
