package dao;

import database.CreateConnection;
import database.mapper.DBRowMapper;
import database.mapper.QuizMapper;
import datatypes.Quiz;
import enums.DaoType;

import java.sql.*;
import java.util.Collection;

import static dao.helpers.FinalBlockExecutor.executeFinalBlock;
import static dao.helpers.QueryGenerator.getInsertQuery;
import static dao.helpers.QueryGenerator.getSelectQuery;
import static database.mapper.QuizMapper.*;

public class QuizDao implements Dao<Integer, Quiz>{

    private final QuestionDao questionDao;
    private final AnswerDao answerDao;
    private DBRowMapper<Quiz> mapper = new QuizMapper();
    private Cao<Integer, Quiz> cao = new Cao<>();
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
        return cao.findById(id);
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
                if (rs.next()){
                    entity.setId(rs.getInt(1));
                    cao.add(entity);
                }
            }
            else System.out.println("Error inserting record");
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            executeFinalBlock(connection, statement, rs);
        }
    }

    @Override
    public Collection<Quiz> findAll() {
        return cao.findAll();
    }

    @Override
    public void deleteById(Integer id) {
        // TODO: 6/17/19
    }

    @Override
    public void update(Quiz entity) {

        // TODO: 6/2/19  
    }

    @Override
    public DaoType getDaoType() {
        return DaoType.Quiz;
    }
    @Override
    public void cache() {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            String query = getSelectQuery(TABLE_NAME);
            statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                cao.add(mapper.mapRow(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            executeFinalBlock(connection, statement, rs);
        }
    }

}
