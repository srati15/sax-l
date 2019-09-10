package dao;

import datatypes.messages.FriendRequest;

import java.util.List;

public interface FriendRequestDao extends Dao<Integer, FriendRequest> {
    FriendRequest findBySenderReceiverId(int senderId, int receiverId);
    List<FriendRequest> findAllForUser(Integer id);
}
