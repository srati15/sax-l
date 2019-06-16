package dao;

import database.CreateConnection;
import datatypes.answer.Answer;
import enums.DaoType;

import java.sql.*;
import java.util.Collection;
import java.util.List;

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
        //use insertAll instead
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
            rs = statement.getGeneratedKeys();
            for (Answer answer : values) {
                rs.next();
                System.out.println(rs.getInt(1));
                answer.setQuestionId(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
