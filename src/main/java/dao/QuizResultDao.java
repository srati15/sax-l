package dao;

import database.mapper.DBRowMapper;
import database.mapper.FriendRequestMapper;
import datatypes.QuizResult;
import datatypes.messages.FriendRequest;
import enums.DaoType;

import java.util.Collection;

public class QuizResultDao implements Dao<Integer, QuizResult> {
    private DBRowMapper<FriendRequest> mapper = new FriendRequestMapper();
    private Cao<Integer, QuizResult> cao = new Cao<>();
    public QuizResultDao(){
    }

    @Override
    public QuizResult findById(Integer integer) {
        // TODO: 7/3/19
        return null;
    }

    @Override
    public void insert(QuizResult entity) {
        // TODO: 7/3/19

    }

    @Override
    public Collection<QuizResult> findAll() {
        // TODO: 7/3/19
        return null;
    }

    @Override
    public void deleteById(Integer integer) {
        // TODO: 7/3/19

    }

    @Override
    public void update(QuizResult entity) {
        // TODO: 7/3/19

    }

    @Override
    public DaoType getDaoType() {
        return null;
    }

    public void getQuizResultsForUser(int userId) {
        // TODO: 7/3/19
    }

    @Override
    public void cache() {

    }
}
