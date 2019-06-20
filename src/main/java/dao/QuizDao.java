package dao;

import dao.helpers.EntityPersister;
import database.CreateConnection;
import database.mapper.DBRowMapper;
import database.mapper.QuizMapper;
import datatypes.Quiz;
import enums.DaoType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import static dao.helpers.FinalBlockExecutor.executeFinalBlock;
import static dao.helpers.QueryGenerator.getSelectQuery;
import static database.mapper.QuizMapper.TABLE_NAME;

public class QuizDao implements Dao<Integer, Quiz> {

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
        if (EntityPersister.executeInsert(entity)) {
            System.out.println("Quiz inserted sucessfully");
            cao.add(entity);
        }else {
            System.out.println("Error inserting quiz");
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
        } finally {
            executeFinalBlock(connection, statement, rs);
        }
    }

}
