package dao;

import database.CreateConnection;
import database.mapper.DBRowMapper;
import database.mapper.QuizResultMapper;
import datatypes.QuizResult;
import enums.DaoType;

import java.sql.*;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;

import static dao.helpers.FinalBlockExecutor.executeFinalBlock;
import static dao.helpers.FinalBlockExecutor.rollback;
import static dao.helpers.QueryGenerator.*;
import static database.mapper.QuizResultMapper.*;

public class QuizResultDao implements Dao<Integer, QuizResult> {
    private DBRowMapper<QuizResult> mapper = new QuizResultMapper();
    private Cao<Integer, QuizResult> cao = new Cao<>();
    private AtomicBoolean isCached = new AtomicBoolean(false);
    public QuizResultDao(){
    }

    @Override
    public QuizResult findById(Integer integer) {
        if (!isCached.get()) cache();
        return cao.findById(integer);
    }

    @Override
    public void insert(QuizResult entity) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            String query = getInsertQuery(TABLE_NAME, USER_ID, QUIZ_ID, SCORE, TIME_SPENT);
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, entity.getUserId());
            statement.setInt(2, entity.getQuizId());
            statement.setInt(3, entity.getScore());
            statement.setInt(4, entity.getTimeSpent());
            int result = statement.executeUpdate();
            connection.commit();
            if (result == 1){
                System.out.println("Result inserted successfully");
                rs = statement.getGeneratedKeys();
                rs.next();
                entity.setId(rs.getInt(1));
                cao.add(entity);
            }
            else
                System.out.println("Error inserting result");
        } catch (SQLException e) {
            rollback(connection);
            e.printStackTrace();
        }finally {
            executeFinalBlock(connection, statement, rs);
        }

    }

    @Override
    public Collection<QuizResult> findAll() {
        if (!isCached.get()) cache();
        return cao.findAll();
    }

    @Override
    public void deleteById(Integer integer) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        try {
            String query = getDeleteQuery(TABLE_NAME, RESULT_ID);
            statement = connection.prepareStatement(query);
            statement.setInt(1, integer);
            int result = statement.executeUpdate();
            connection.commit();
            if(result == 1) {
                cao.delete(integer);
                System.out.println("Result Deleted Successfully");
            }
            else
                System.out.println("Error Deleting Result");
        } catch (SQLException e) {
            rollback(connection);
            e.printStackTrace();
        }finally {
            executeFinalBlock(connection,statement);
        }
    }

    @Override
    public void update(QuizResult entity) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        try {
            String query = getUpdateQuery(TABLE_NAME, RESULT_ID, USER_ID, QUIZ_ID, SCORE, TIME_SPENT);
            statement = connection.prepareStatement(query);
            statement.setInt(1, entity.getId());
            statement.setInt(2, entity.getUserId());
            statement.setInt(3, entity.getQuizId());
            statement.setInt(4, entity.getScore());
            statement.setInt(5, entity.getTimeSpent());

            int result = statement.executeUpdate();
            connection.commit();
            if (result == 1) {
                cao.add(entity);
                System.out.println("Result updated sucessfully");
            }
            else System.out.println("Error updating result");
        } catch (SQLException e) {
            rollback(connection);
            e.printStackTrace();
        }finally {
            executeFinalBlock(connection, statement);
        }
    }

    @Override
    public DaoType getDaoType() {
        return DaoType.QuizResult;
    }

    @Override
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
                QuizResult quizResult = mapper.mapRow(rs);
                cao.add(quizResult);
            }
            isCached.set(true);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            executeFinalBlock(connection, statement, rs);
        }
    }
}
