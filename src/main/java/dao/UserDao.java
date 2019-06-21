package dao;

import dao.entity.EntityManager;
import datatypes.User;
import enums.DaoType;

import java.util.Collection;
import java.util.List;

public class UserDao implements Dao<Integer, User> {
    private Cao<Integer, User> cao = new Cao<>();
    private EntityManager entityManager = EntityManager.getInstance();


    @Override
    public User findById(Integer id) {

        return cao.findById(id);
    }

    public User findByUserName(String usrName) {
        return cao.findAll().stream().filter(s -> s.getUserName().equals(usrName)).findFirst().orElse(null);
    }

    @Override
    public void insert(User entity) {
        if (entityManager.getPersister().executeInsert(entity)) {
            System.out.println("Record inserted sucessfully");
            cao.add(entity);
        } else {
            System.out.println("Error inserting record");
        }
    }

    @Override
    public Collection<User> findAll() {
        return cao.findAll();
    }

    @Override
    public void deleteById(Integer id) {

    }

    public void update(User user) {

    }

    @Override
    public DaoType getDaoType() {
        return DaoType.User;
    }

    @Override
    public void cache() {
        List<User> userList = entityManager.getFinder().executeFindAll(User.class);
        userList.forEach(s->cao.add(s));
    }
}
