package dao;

import dao.helpers.EntityPersister;
import database.CreateConnection;
import datatypes.answer.Answer;
import enums.DaoType;

import java.sql.*;
import java.util.Collection;

import static dao.helpers.QueryGenerator.getInsertQuery;
import static database.mapper.AnswerMapper.*;

public class AnswerDao implements Dao<Integer, Answer> {
    private Cao<Integer, Answer> cao = new Cao<>();
    @Override
    public Answer findById(Integer id) {
        return cao.findById(id);
    }

    @Override
    public void insert(Answer entity) {
        if (EntityPersister.executeInsert(entity)) {
            System.out.println("answer inserted successfully: "+entity);
            cao.add(entity);
        }else {
            System.out.println("error inserting answer: "+entity);
        }
    }

    @Override
    public Collection<Answer> findAll() {
        // TODO: 6/16/19
        return cao.findAll();
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

    @Override
    public void cache() {

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
                if (rs.next()){
                    System.out.println(rs.getInt(1));
                    answer.setId(rs.getInt(1));
                    cao.add(answer);
                }else {
                    System.out.println("error in some insertions");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
