package dao;

import database.CreateConnection;
import database.mapper.DBRowMapper;
import database.mapper.QuizMapper;
import datatypes.Quiz;
import enums.DaoType;

import java.sql.*;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import static dao.helpers.FinalBlockExecutor.executeFinalBlock;
import static dao.helpers.FinalBlockExecutor.rollback;
import static dao.helpers.QueryGenerator.*;
import static database.mapper.QuizMapper.*;

public class QuizDao implements Dao<Integer, Quiz>{

    private DBRowMapper<Quiz> mapper = new QuizMapper();
    private Cao<Integer, Quiz> cao = new Cao<>();
    private AtomicBoolean isCached = new AtomicBoolean(false);
    public QuizDao() {

    }

    @Override
    public Quiz findById(Integer id) {
        if (!isCached.get()) cache();
        return cao.findById(id);
    }

    @Override
    public void insert(Quiz entity) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            String query = getInsertQuery(TABLE_NAME, QUIZ_AUTHOR, QUIZ_NAME, DATE_CREATED, IS_RANDOMIZED, IS_CORRECTION, IS_PRACTICE, IS_SINGLEPAGE, TIMES_DONE, QUIZ_IMAGE);
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            setParameters(entity, statement);

            int result = statement.executeUpdate();
            connection.commit();
            if (result == 1) {
                System.out.println("Quiz inserted sucessfully");
                rs = statement.getGeneratedKeys();
                if (rs.next()){
                    entity.setId(rs.getInt(1));
                    cao.add(entity);
                }
            }
            else System.out.println("Error inserting Quiz");
        } catch (SQLException e) {
            e.printStackTrace();
            rollback(connection);
        }finally {
            executeFinalBlock(connection, statement, rs);
        }
    }

    @Override
    public Collection<Quiz> findAll() {
        if (!isCached.get()) cache();
        return cao.findAll();
    }

    @Override
    public void deleteById(Integer id) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            Quiz quiz = findById(id);
            String query = getDeleteQuery(TABLE_NAME, QUIZ_ID);
            statement = connection.prepareStatement(query);
            statement.setInt(1,id);
            int result = statement.executeUpdate();
            connection.commit();
            if (result == 1) {
                System.out.println("Quiz deleted sucessfully");
                cao.delete(id);
            }
            else System.out.println("Error inserting Quiz");
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            executeFinalBlock(connection, statement, rs);
        }
    }

    @Override
    public void update(Quiz entity) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            String query = getUpdateQuery(TABLE_NAME, QUIZ_ID, QUIZ_AUTHOR, QUIZ_NAME, DATE_CREATED, IS_RANDOMIZED, IS_CORRECTION, IS_PRACTICE, IS_SINGLEPAGE, TIMES_DONE, QUIZ_IMAGE);
            statement = connection.prepareStatement(query);
            setParameters(entity, statement);
            statement.setInt(10, entity.getId());

            int result = statement.executeUpdate();
            connection.commit();
            if (result == 1) {
                System.out.println("Quiz updated sucessfully");
                cao.add(entity);
            }
            else System.out.println("Error inserting Quiz");
        } catch (SQLException e) {
            e.printStackTrace();
            rollback(connection);
        }finally {
            executeFinalBlock(connection, statement, rs);
        }
    }

    private void setParameters(Quiz entity, PreparedStatement statement) throws SQLException {
        statement.setInt(1, entity.getAuthorId());
        statement.setString(2, entity.getQuizName());
        statement.setTimestamp(3, entity.getDateCreated());
        statement.setBoolean(4, entity.isRandomized());
        statement.setBoolean(5, entity.isAllowedImmediateCorrection());
        statement.setBoolean(6, entity.isAllowedPracticemode());
        statement.setBoolean(7, entity.isOnePage());
        statement.setInt(8, entity.getTimesDone());
        statement.setString(9, entity.getQuizImageURL());
    }

    @Override
    public DaoType getDaoType() {
        return DaoType.Quiz;
    }
    public void cache() {
        if (isCached.get()) return;
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            String query = getSelectQuery(TABLE_NAME);
            statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                cao.add(mapper.mapRow(resultSet));
            }
            isCached.set(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            executeFinalBlock(connection, statement, rs);
        }
    }

    public List<Quiz> findAllForUser(int userId) {
        if (!isCached.get()) cache();
        return findAll().stream().filter(quiz -> quiz.getAuthorId() == userId).collect(Collectors.toList());
    }
}
