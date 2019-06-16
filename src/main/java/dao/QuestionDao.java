package dao;

import database.CreateConnection;
import datatypes.question.Question;
import enums.DaoType;

import java.sql.*;
import java.util.List;

import static dao.helpers.FinalBlockExecutor.executeFinalBlock;
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
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            String query = getInsertQuery(TABLE_NAME, QUESTION_TEXT, QUESTION_TYPE, QUIZ_ID );
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, entity.getQuestion());
            statement.setInt(2, entity.getQuestionType().getValue());
            statement.setInt(3, entity.getQuizId());
            int result = statement.executeUpdate();
            if(result == 1){
                rs = statement.getGeneratedKeys();
                rs.next();
                int id = rs.getInt(1);
                entity.setQuestionId(id);
                System.out.println("Question Added Successfully");
            }else
                System.out.println("Error Adding Question");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            executeFinalBlock(connection, statement, rs);
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
