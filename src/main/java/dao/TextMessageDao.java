package dao;

import dao.entity.EntityManager;
import datatypes.messages.TextMessage;
import enums.DaoType;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class TextMessageDao implements Dao<Integer, TextMessage> {
    private Cao<Integer, TextMessage> cao = new Cao<>();
    private EntityManager entityManager = EntityManager.getInstance();


    @Override
    public TextMessage findById(Integer id) {
        return cao.findById(id);
    }

    @Override
    public void insert(TextMessage entity) {
        if (entityManager.getPersister().executeInsert(entity)) {
            System.out.println("message inserted successfully");
            cao.add(entity);
        } else {
            System.out.println("Error inserting message");
        }
    }

    @Override
    public Collection<TextMessage> findAll() {
        return cao.findAll();
    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public void update(TextMessage entity) {

    }

    @Override
    public DaoType getDaoType() {
        return DaoType.TextMessage;
    }

    @Override
    public void cache() {
//        List<TextMessage> textMessages = entityManager.getFinder().executeFindAll(TextMessage.class);
//        textMessages.forEach(s->cao.add(s));
    }

    public List<TextMessage> getTextMessagesOfGivenUsers(int senderId, int receiverId) {
        //not both sides
        return cao.findAll().stream().filter(s -> s.getSenderId() == senderId && s.getReceiverId() == receiverId).collect(Collectors.toList());
    }

}
