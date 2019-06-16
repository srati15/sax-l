package dao;

import database.CreateConnection;
import datatypes.answer.Answer;
import enums.DaoType;

import java.sql.*;
import java.util.List;

import static dao.helpers.FinalBlockExecutor.executeFinalBlock;
import static dao.helpers.QueryGenerator.getInsertQuery;
import static database.mapper.AnswerMapper.*;

public class AnswerDao implements Dao<Integer, Answer> {
    @Override
    public Answer findById(Integer integer) {
        // TODO: 6/16/19
        return null;
    }

    @Override
    public void insert(Answer entity) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            String query = getInsertQuery(TABLE_NAME, QUESTION_ID, ANSWER_TEXT, IS_CORRECT );
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, entity.getQuestionId());
            statement.setString(2, entity.getAnswer());
            statement.setBoolean(3, entity.isCorrect());
            int result = statement.executeUpdate();
            if(result == 1){
                rs = statement.getGeneratedKeys();
                rs.next();
                int id = rs.getInt(1);
                entity.setAnswerId(id);
                System.out.println("Answer Added Successfully");
            }else
                System.out.println("Error Adding Answer");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            executeFinalBlock(connection, statement, rs);
        }
    }

    @Override
    public List<Answer> findAll() {
        // TODO: 6/16/19
        return null;
    }

    @Override
    public void deleteById(Integer integer) {
        // TODO: 6/16/19

    }

    @Override
    public void update(Answer entity) {
        // TODO: 6/16/19

    }

    @Override
    public DaoType getDaoType() {
        return DaoType.Answer;
    }
}
