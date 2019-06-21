package dao;

import dao.entity.EntityManager;
import datatypes.Announcement;
import enums.DaoType;

import java.util.Collection;
import java.util.List;

public class AnnouncementDao implements Dao<Integer, Announcement> {
    private Cao<Integer, Announcement> cao = new Cao<>();
    private EntityManager entityManager = EntityManager.getInstance();
    @Override
    public Announcement findById(Integer id) {
        return cao.findById(id);
    }



    @Override
    public void insert(Announcement entity) {
        if (entityManager.getPersister().executeInsert(entity)){
            System.out.println("Announcement inserted successfully");
            cao.add(entity);
        }else {
            System.out.println("Error inserting announcement");
        }
    }

    @Override
    public Collection<Announcement> findAll() {
        return cao.findAll();
    }

    @Override
    public void deleteById(Integer id) {
    }

    @Override
    public void update(Announcement entity) {

    }

    @Override
    public DaoType getDaoType() {
        return DaoType.Announcement;
    }

    @Override
    public void cache() {
        List<Announcement> answerList = entityManager.getFinder().executeFindAll(Announcement.class);
        answerList.forEach(s->cao.add(s));
    }
}
