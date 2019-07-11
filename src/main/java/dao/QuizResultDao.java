package dao;

import database.CreateConnection;
import datatypes.quiz.QuizResult;
import enums.DaoType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;

import static dao.helpers.FinalBlockExecutor.executeFinalBlock;
import static dao.helpers.FinalBlockExecutor.rollback;
import static dao.helpers.QueryGenerator.*;

public class QuizResultDao implements Dao<Integer, QuizResult> {
    private static final Logger logger = LogManager.getLogger(QuizResultDao.class);

    private final DBRowMapper<QuizResult> mapper = new QuizResultMapper();
    private final Cao<Integer, QuizResult> cao = new Cao<>();
    private final AtomicBoolean isCached = new AtomicBoolean(false);
    private static final String RESULT_ID = "result_id";
    private static final String USER_ID = "user_id";
    private static final String QUIZ_ID = "quiz_id";
    private static final String SCORE = "score";
    private static final String TIME_SPENT = "time_spent";
    private static final String TIMESTAMP = "date_happened";

    private static final String TABLE_NAME = "results";

    public QuizResultDao(){
    }

    @Override
    public QuizResult findById(Integer integer) {
        if (!isCached.get()) cache();
        return cao.findById(integer);
    }

    @Override
    public boolean insert(QuizResult entity) {
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
            logger.debug("Executing statement: {}", statement);
            int result = statement.executeUpdate();
            connection.commit();
            if (result == 1){
                rs = statement.getGeneratedKeys();
                rs.next();
                entity.setId(rs.getInt(1));
                logger.info("Result inserted successfully, {}",entity);
                cao.add(entity);
                return true;
            }
            else
                logger.error("Error inserting result, {}", entity);
        } catch (SQLException e) {
            rollback(connection);
            logger.error(e);
        }finally {
            executeFinalBlock(connection, statement, rs);
        }
        return false;
    }

    @Override
    public Collection<QuizResult> findAll() {
        if (!isCached.get()) cache();
        return cao.findAll();
    }

    @Override
    public boolean deleteById(Integer integer) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        try {
            String query = getDeleteQuery(TABLE_NAME, RESULT_ID);
            statement = connection.prepareStatement(query);
            statement.setInt(1, integer);
            logger.debug("Executing statement: {}", statement);
            int result = statement.executeUpdate();
            connection.commit();
            if(result == 1) {
                logger.info("Result Deleted Successfully, {}", findById(integer));
                cao.delete(integer);
                return true;
            }
            else
                logger.error("Error Deleting Result, {}", findById(integer));
        } catch (SQLException e) {
            rollback(connection);
            logger.error(e);
        }finally {
            executeFinalBlock(connection,statement);
        }
        return false;
    }

    @Override
    public boolean update(QuizResult entity) {
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
            logger.debug("Executing statement: {}", statement);

            int result = statement.executeUpdate();
            connection.commit();
            if (result == 1) {
                cao.add(entity);
                logger.info("Result updated sucessfully, {}", entity);
                return true;
            }
            else logger.error("Error updating result, {}", entity);
        } catch (SQLException e) {
            rollback(connection);
            logger.error(e);
        }finally {
            executeFinalBlock(connection, statement);
        }
        return false;
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
            logger.info("{} is Cached", this.getClass().getSimpleName());
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            executeFinalBlock(connection, statement, rs);
        }
    }
    private class QuizResultMapper implements DBRowMapper<QuizResult> {
        @Override
        public QuizResult mapRow(ResultSet rs) {
            try {
                int resultId = rs.getInt(RESULT_ID);
                int userId = rs.getInt(USER_ID);
                int quizId = rs.getInt(QUIZ_ID);
                int score = rs.getInt(SCORE);
                int timeSpent = rs.getInt(TIME_SPENT);
                LocalDateTime timestamp = rs.getTimestamp(TIMESTAMP).toLocalDateTime();
                QuizResult quizResult = new QuizResult(quizId,userId,score,timeSpent, timestamp);
                quizResult.setId(resultId);
                return quizResult;
            } catch (SQLException e) {
                logger.error(e);
            }

            return null;
        }
    }



}
