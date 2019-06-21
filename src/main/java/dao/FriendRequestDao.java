package dao;

import dao.entity.EntityManager;
import datatypes.messages.FriendRequest;
import enums.DaoType;
import enums.RequestStatus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class FriendRequestDao implements Dao<Integer, FriendRequest> {
    private Cao<Integer, FriendRequest> cao = new Cao<>();
    private EntityManager entityManager = EntityManager.getInstance();


    @Override
    public FriendRequest findById(Integer id) {
        return cao.findById(id);
    }

    @Override
    public void insert(FriendRequest entity) {

        if (entityManager.getPersister().executeInsert(entity)) {
            cao.add(entity);
            System.out.println("Request Added Successfully");
        } else{
            System.out.println("Error Adding Request");
        }
    }

    @Override
    public Collection<FriendRequest> findAll() {
        return cao.findAll();
    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public void update(FriendRequest entity) {


    }

    @Override
    public DaoType getDaoType() {
        return DaoType.FriendRequest;
    }


    public FriendRequest findBySenderReceiverId(int senderId, int receiverId) {
        return cao.findAll().stream().filter(s -> s.getSenderId() == senderId && s.getReceiverId() == receiverId).findFirst().orElse(null);
    }

    public List<FriendRequest> getPendingRequestsFor(int receiverId) {
        return cao.findAll().stream().filter(s -> s.getReceiverId() == receiverId).collect(Collectors.toList());
    }

    public List<Integer> getFriendsIdsFor(int id) {
        List<Integer> friendsIds = new ArrayList<>();
        cao.findAll().stream().filter(s -> s.getReceiverId() == id && s.getStatus() == RequestStatus.Accepted).forEach(s -> friendsIds.add(s.getSenderId()));
        cao.findAll().stream().filter(s -> s.getSenderId() == id && s.getStatus() == RequestStatus.Accepted).forEach(s -> friendsIds.add(s.getReceiverId()));
        return friendsIds;
    }

    @Override
    public void cache() {
//        List<FriendRequest> friendRequests = entityManager.getFinder().executeFindAll(FriendRequest.class);
//        friendRequests.forEach(s->cao.add(s));
    }
}
