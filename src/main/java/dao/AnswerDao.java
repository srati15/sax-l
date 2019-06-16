package dao;

import datatypes.answer.Answer;
import enums.DaoType;

import java.util.List;

public class AnswerDao implements Dao<Integer, Answer> {
    @Override
    public Answer findById(Integer integer) {
        // TODO: 6/16/19
        return null;
    }

    @Override
    public void insert(Answer entity) {
        // TODO: 6/16/19

    }

    @Override
    public List<Answer> findAll() {
        // TODO: 6/16/19
        return null;
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
}
