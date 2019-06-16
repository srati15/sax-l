package dao;

import database.CreateConnection;
import database.mapper.DBRowMapper;
import database.mapper.QuizMapper;
import datatypes.Quiz;
import enums.DaoType;

import java.sql.*;
import java.util.List;

import static dao.helpers.FinalBlockExecutor.executeFinalBlock;
import static dao.helpers.QueryGenerator.getInsertQuery;
import static dao.helpers.QueryGenerator.getSelectQuery;
import static database.mapper.QuizMapper.*;

public class QuizDao implements Dao<Integer, Quiz>{

    private final QuestionDao questionDao;
    private final AnswerDao answerDao;
    private DBRowMapper<Quiz> mapper = new QuizMapper();

    public QuizDao(QuestionDao questionDao, AnswerDao answerDao) {
        this.questionDao = questionDao;
        this.answerDao = answerDao;
    }

    public QuestionDao getQuestionDao() {
        return questionDao;
    }

    public AnswerDao getAnswerDao() {
        return answerDao;
    }

    @Override
    public Quiz findById(Integer id) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            String query = getSelectQuery(TABLE_NAME, QUIZ_ID);
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapper.mapRow(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            executeFinalBlock(connection, statement, rs);
        }
        return null;
    }

    @Override
    public void insert(Quiz entity) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            String query = getInsertQuery(TABLE_NAME, QUIZ_AUTHOR, QUIZ_NAME, DATE_CREATED, IS_RANDOMIZED, IS_CORRECTION, IS_PRACTICE, IS_SINGLEPAGE, TIMES_DONE);
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, entity.getAuthorId());
            statement.setString(2, entity.getQuizName());
            statement.setTimestamp(3, entity.getDateCreated());
            statement.setBoolean(4, entity.isRandomized());
            statement.setBoolean(5, entity.isAllowedImmediateCorrection());
            statement.setBoolean(6, entity.isAllowedPracticemode());
            statement.setBoolean(7, entity.isOnePage());
            statement.setInt(8, entity.getTimesDone());

            int result = statement.executeUpdate();
            if (result == 1) {
                System.out.println("Record inserted sucessfully");
                rs = statement.getGeneratedKeys();
                rs.next();
                entity.setId(rs.getInt(1));
            }
            else System.out.println("Error inserting record");
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            executeFinalBlock(connection, statement, rs);
        }
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
