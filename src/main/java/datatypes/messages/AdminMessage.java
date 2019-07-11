package datatypes.messages;

import datatypes.Domain;

import java.time.LocalDateTime;

public class AdminMessage extends Domain<Integer> {
    private String name;
    private String mail;
    private String subject;
    private String messageText;
    private LocalDateTime time;
    private boolean seen;
    public AdminMessage(String name, String mail, String subject, String messageText, LocalDateTime time, boolean seen) {
        this.name = name;
        this.mail = mail;
        this.subject = subject;
        this.messageText = messageText;
        this.time = time;
        this.seen = seen;
    }

    public boolean isSeen() {
        return seen;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    public String getMail() {
        return mail;
    }

    public String getSubject() {
        return subject;
    }

    public String getMessageText() {
        return messageText;
    }

    @Override
    public String toString() {
        return "AdminMessage{" +
                "name='" + name + '\'' +
                ", mail='" + mail + '\'' +
                ", subject='" + subject + '\'' +
                ", messageText='" + messageText + '\'' +
                ", time=" + time +
                '}';
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }
}
