package datatypes.messages;

import anotations.Column;
import anotations.Entity;
import enums.MessageType;

import java.sql.Timestamp;
@Entity(table = "text_message")
public class TextMessage extends Message {
    @Column("message_sent")
    private final String textMesage;
    private final MessageType messageType = MessageType.TextMessage;
    public TextMessage(int senderId, int receiverId, Timestamp timestamp, String textMessage) {
        super(senderId, receiverId, timestamp);
        this.textMesage = textMessage;
    }
    public TextMessage(int id, int senderId, int receiverId, Timestamp timestamp, String textMessage) {
        super(senderId, receiverId, timestamp);
        this.textMesage = textMessage;
        this.id = id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTextMessage(){return textMesage;}

    @Override
    public MessageType getMessageType() {
        return messageType;
    }

    @Override
    public String toString() {
        return "TextMessage{" +
                "textMesage='" + textMesage + '\'' +
                ", id=" + id +
                "} " + super.toString();
    }
}
