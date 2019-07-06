package dao;

import database.CreateConnection;
import database.mapper.QuestionMapper;
import datatypes.question.Question;
import enums.DaoType;

import java.sql.*;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import static dao.helpers.FinalBlockExecutor.executeFinalBlock;
import static dao.helpers.FinalBlockExecutor.rollback;
import static dao.helpers.QueryGenerator.*;
import static database.mapper.QuestionMapper.*;

public class QuestionDao implements Dao<Integer, Question> {
    private Cao<Integer, Question> cao = new Cao<>();
    private QuestionMapper questionMapper = new QuestionMapper();
    private AtomicBoolean isCached = new AtomicBoolean(false);

    public QuestionDao(){
    }
    @Override
    public Question findById(Integer id) {
        if (!isCached.get()) cache();
        return cao.findById(id);
    }

    @Override
    public void insert(Question entity) {
        //use insertAll instead
    }

    public void insertAll(Set<Question> questions) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        String query = getInsertQuery(TABLE_NAME, QUESTION_TEXT, QUESTION_TYPE, QUIZ_ID);
        try {
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            for (Question question : questions) {
                statement.setString(1, question.getQuestion());
                statement.setInt(2, question.getQuestionType().getValue());
                statement.setInt(3, question.getQuizId());
                statement.addBatch();
            }
            statement.executeBatch();
            connection.commit();
            rs = statement.getGeneratedKeys();
            for (Question question : questions) {
                rs.next();
                question.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            rollback(connection);
        }finally {
            executeFinalBlock(connection, statement, rs);
        }
    }

    @Override
    public Collection<Question> findAll() {
        if (!isCached.get()) cache();
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
                Question question = questionMapper.mapRow(rs);
                cao.add(question);
            }
            isCached.set(true);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            executeFinalBlock(connection, statement, rs);
        }
    }

    public List<Question> getQuestionForQuiz(int quizId) {
        if (!isCached.get()) cache();

        return cao.findAll().stream().filter(question -> question.getQuizId() == quizId).collect(Collectors.toList());
    }

    public void deleteAll(Set<Question> keySet) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        String query = getDeleteQuery(TABLE_NAME, QUESTION_ID );
        try {
            statement = connection.prepareStatement(query);
            for (Question question : keySet) {
                statement.setInt(1, question.getId());
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
}
