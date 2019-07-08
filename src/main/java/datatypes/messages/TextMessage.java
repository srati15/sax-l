package datatypes.messages;

import enums.MessageType;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class TextMessage extends Message {
    private final String textMesage;
    private final MessageType messageType = MessageType.TextMessage;
    public TextMessage(int senderId, int receiverId, LocalDateTime timestamp, String textMessage) {
        super(senderId, receiverId, timestamp);
        this.textMesage = textMessage;
    }
    public TextMessage(int id, int senderId, int receiverId, LocalDateTime timestamp, String textMessage) {
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
