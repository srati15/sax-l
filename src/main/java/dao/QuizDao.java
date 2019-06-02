package dao;

import datatypes.Quiz;
import enums.DaoType;

import java.util.List;

public class QuizDao implements Dao<Integer, Quiz>{
    @Override
    public Quiz findById(Integer id) {
        // TODO: 5/27/19
        return null;
    }

    @Override
    public void insert(Quiz entity) {
        // TODO: 5/27/19
    }

    @Override
    public List<Quiz> findAll() {
        // TODO: 5/27/19

        return null;
    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public void update(Quiz entity) {
        // TODO: 6/2/19  
    }

    @Override
    public DaoType getDaoType() {
        return DaoType.Quiz;
    }
}
