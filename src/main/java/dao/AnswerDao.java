package dao;

import dao.entity.EntityManager;
import datatypes.answer.Answer;
import enums.DaoType;

import java.util.Collection;
import java.util.List;


public class AnswerDao implements Dao<Integer, Answer> {
    private Cao<Integer, Answer> cao = new Cao<>();
    private EntityManager entityManager = EntityManager.getInstance();
    @Override
    public Answer findById(Integer id) {
        return cao.findById(id);
    }

    @Override
    public void insert(Answer entity) {
        if (entityManager.getPersister().executeInsert(entity)) {
            System.out.println("answer inserted successfully: "+entity);
            cao.add(entity);
        }else {
            System.out.println("error inserting answer: "+entity);
        }
    }

    @Override
    public Collection<Answer> findAll() {
        // TODO: 6/16/19
        return cao.findAll();
    }

    @Override
    public void deleteById(Integer integer) {
        // TODO: 6/16/19

    }

    @Override
    public void update(Answer entity) {
        // TODO: 6/16/19

    }

    @Override
    public DaoType getDaoType() {
        return DaoType.Answer;
    }

    @Override
    public void cache() {
        List<Answer> answerList = entityManager.getFinder().executeFindAll(Answer.class);
        answerList.forEach(s->cao.add(s));
    }

    public void insertAll(Collection<Answer> values) {
        values.forEach(this::insert);
    }
}
