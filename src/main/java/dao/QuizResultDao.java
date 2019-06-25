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
    private static final QuizResultDao quizResultDao = new QuizResultDao();
    public static QuizResultDao getInstance() {
        return quizResultDao;
    }
    private QuizResultDao(){
    }

    @Override
    public QuizResult findById(Integer integer) {
        return null;
    }

    @Override
    public void insert(QuizResult entity) {

    }

    @Override
    public Collection<QuizResult> findAll() {
        return null;
    }

    @Override
    public void deleteById(Integer integer) {

    }

    @Override
    public void update(QuizResult entity) {

    }

    @Override
    public DaoType getDaoType() {
        return null;
    }

    @Override
    public void cache() {

    }
}
