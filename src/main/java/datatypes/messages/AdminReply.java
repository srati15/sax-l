package datatypes.messages;

import datatypes.Domain;

import java.time.LocalDateTime;

public class AdminReply extends Domain<Integer> {
    private int messageId;
    private String replyText;
    private LocalDateTime dateSent;

    public AdminReply(int messageId, String replyText, LocalDateTime dateSent) {
        this.messageId = messageId;
        this.replyText = replyText;
        this.dateSent = dateSent;
    }

    public int getMessageId() {
        return messageId;
    }

    public String getReplyText() {
        return replyText;
    }

    public LocalDateTime getDateSent() {
        return dateSent;
    }

    @Override
    public String toString() {
        return "AdminReply{" +
                "messageId=" + messageId +
                ", replyText='" + replyText + '\'' +
                ", dateSent=" + dateSent +
                ", id=" + id +
                "} " + super.toString();
    }
}
