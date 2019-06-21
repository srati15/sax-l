package dao;

import dao.entity.EntityManager;
import datatypes.question.Question;
import enums.DaoType;

import java.util.Collection;
import java.util.Set;

public class QuestionDao implements Dao<Integer, Question> {
    private Cao<Integer, Question> cao = new Cao<>();
    private EntityManager entityManager = EntityManager.getInstance();

    @Override
    public Question findById(Integer id) {
        return cao.findById(id);
    }

    @Override
    public void insert(Question entity) {
        if (entityManager.getPersister().executeInsert(entity)) {
            System.out.println("Question inserted successfully");
            cao.add(entity);
        }else {
            System.out.println("Error inserting question");
        }
    }

    public void insertAll(Set<Question> questions) {
        questions.forEach(this::insert);
    }

    @Override
    public Collection<Question> findAll() {
        return cao.findAll();
    }

    @Override
    public void deleteById(Integer integer) {
        // TODO: 6/16/19

    }

    @Override
    public void update(Question entity) {
        // TODO: 6/16/19

    }

    @Override
    public DaoType getDaoType() {
        return DaoType.Question;
    }

    @Override
    public void cache() {
        // FIXME: 21/06/19
    }
}
