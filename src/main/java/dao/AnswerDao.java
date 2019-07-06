package dao;

import database.CreateConnection;
import datatypes.answer.Answer;
import enums.DaoType;

import java.sql.*;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;

import static dao.helpers.FinalBlockExecutor.executeFinalBlock;
import static dao.helpers.FinalBlockExecutor.rollback;
import static dao.helpers.QueryGenerator.*;

public class AnswerDao implements Dao<Integer, Answer> {
    private Cao<Integer, Answer> cao = new Cao<>();
    private AnswerMapper answerMapper = new AnswerMapper();
    private AtomicBoolean isCached = new AtomicBoolean(false);
    public static final String ANSWER_ID = "id";
    public static final String QUESTION_ID = "question_id";
    public static final String ANSWER_TEXT = "answer_string";
    public static final String TABLE_NAME = "answers";


    public AnswerDao(){

    }

    @Override
    public Answer findById(Integer id) {
        if (!isCached.get()) cache();
        return cao.findById(id);
    }

    @Override
    public void insert(Answer entity) {
        //use insertAll instead
    }

    @Override
    public Collection<Answer> findAll() {
        if (!isCached.get()) cache();
        return cao.findAll();
    }
    @Deprecated
    @Override
    public void deleteById(Integer integer) {
        //use deleteAll instead
    }

    @Override
    public void update(Answer entity) {
        // TODO: 6/16/19

    }

    @Override
    public DaoType getDaoType() {
        return DaoType.Answer;
    }

    public void cache() {
        if (isCached.get()) return;
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            String query = getSelectQuery(TABLE_NAME);
            statement = connection.prepareStatement(query);
            rs = statement.executeQuery();
            while (rs.next()) {
                Answer answer = answerMapper.mapRow(rs);
                cao.add(answer);
            }
            isCached.set(true);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            executeFinalBlock(connection, statement, rs);
        }
    }

    public void insertAll(Collection<Answer> values) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        String query = getInsertQuery(TABLE_NAME, QUESTION_ID, ANSWER_TEXT);
        try {
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            for (Answer answer : values) {
                statement.setInt(1, answer.getQuestionId());
                statement.setString(2, answer.getAnswer());
                statement.addBatch();
            }
            statement.executeBatch();
            connection.commit();
            rs = statement.getGeneratedKeys();
            for (Answer answer : values) {
                if (rs.next()){
                    answer.setId(rs.getInt(1));
                    cao.add(answer);
                }else {
                    System.out.println("error in some insertions");
                }
            }
        } catch (SQLException e) {
            rollback(connection);
            e.printStackTrace();
        }finally {
            executeFinalBlock(connection, statement, rs);
        }
    }

    public Answer findAnswerForQuestion(Integer questionId) {
        return cao.findAll().stream().filter(answer -> answer.getQuestionId() == questionId).findFirst().get();
    }

    public void deleteAll(Collection<Answer> values) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        String query = getDeleteQuery(TABLE_NAME,ANSWER_ID );
        try {
            statement = connection.prepareStatement(query);
            for (Answer answer : values) {
                statement.setInt(1, answer.getId());
                statement.addBatch();
            }
            statement.executeBatch();
            connection.commit();
        } catch (SQLException e) {
            rollback(connection);
            e.printStackTrace();
        }finally {
            executeFinalBlock(connection, statement, rs);
        }
    }

    private class AnswerMapper implements DBRowMapper<Answer> {
        @Override
        public Answer mapRow(ResultSet rs) {
            try {
                int answerId = rs.getInt(ANSWER_ID);
                int questionId = rs.getInt(QUESTION_ID);
                String answerText = rs.getString(ANSWER_TEXT);
                Answer answer = new Answer(answerText);
                answer.setId(answerId);
                answer.setQuestionId(questionId);
                return answer;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
