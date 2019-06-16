package dao;

import datatypes.question.Question;
import enums.DaoType;

import java.util.List;

public class QuestionDao implements Dao<Integer, Question> {
    @Override
    public Question findById(Integer integer) {
        // TODO: 6/16/19
        return null;
    }

    @Override
    public void insert(Question entity) {
        // TODO: 6/16/19

    }

    @Override
    public List<Question> findAll() {
        // TODO: 6/16/19
        return null;
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
}
