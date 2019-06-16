package dao;

import database.CreateConnection;
import datatypes.question.Question;
import enums.DaoType;

import java.sql.*;
import java.util.List;
import java.util.Set;

import static dao.helpers.QueryGenerator.getInsertQuery;
import static database.mapper.QuestionMapper.*;

public class QuestionDao implements Dao<Integer, Question> {
    @Override
    public Question findById(Integer integer) {
        // TODO: 6/16/19
        return null;
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
            rs = statement.getGeneratedKeys();
            for (Question question : questions) {
                rs.next();
                System.out.println(rs.getInt(1));
                question.setQuestionId(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Question> findAll() {
        // TODO: 6/16/19
        return null;
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
}
