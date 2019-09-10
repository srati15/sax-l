package dao;

import datatypes.messages.TextMessage;

import java.util.List;

public interface TextMessageDao extends Dao<Integer, TextMessage> {
    List<TextMessage> getTextMessagesOfGivenUsers(int senderId, int receiverId);
}
