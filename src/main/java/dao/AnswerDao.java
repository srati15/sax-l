package dao;

import database.CreateConnection;
import datatypes.quiz.answer.Answer;
import enums.DaoType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Collection;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static dao.helpers.FinalBlockExecutor.executeFinalBlock;
import static dao.helpers.FinalBlockExecutor.rollback;
import static dao.helpers.QueryGenerator.*;

public class AnswerDao implements Dao<Integer, Answer> {
    private static final Logger logger = LogManager.getLogger(AnswerDao.class);
    private ThreadPoolExecutor executor;

    private final Cao<Integer, Answer> cao = new Cao<>();
    private final AnswerMapper answerMapper = new AnswerMapper();
    private final AtomicBoolean isCached = new AtomicBoolean(false);
    private static final String ANSWER_ID = "id";
    private static final String QUESTION_ID = "question_id";
    private static final String ANSWER_TEXT = "answer_string";
    private static final String TABLE_NAME = "answers";

    public AnswerDao(ThreadPoolExecutor newFixedThreadPool) {
        this.executor = newFixedThreadPool;
    }


    @Override
    public Answer findById(Integer id) {
        if (!isCached.get()) cache();
        return cao.findById(id);
    }

    @Deprecated
    @Override
    public boolean insert(Answer entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<Answer> findAll() {
        if (!isCached.get()) cache();
        return cao.findAll();
    }

    @Deprecated
    @Override
    public boolean deleteById(Integer integer) {
        //use deleteAll instead
        return false;
    }

    @Deprecated
    @Override
    public boolean update(Answer entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public DaoType getDaoType() {
        return DaoType.Answer;
    }

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
                Answer answer = answerMapper.mapRow(rs);
                cao.add(answer);
            }
            isCached.set(true);
            logger.info("{} is Cached", this.getClass().getSimpleName());
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            executeFinalBlock(connection, statement, rs);
        }
    }

    public boolean insertAll(Collection<Answer> values) {
        CountDownLatch latch = new CountDownLatch(values.size());
        values.forEach(answer -> executor.execute(new InsertTask(answer, latch)));
        try {
            latch.await();
            logger.info("All answers inserted successfully!");
            return true;
        } catch (InterruptedException e) {
            logger.error("Error inserting answers");
            logger.error(e);
        }
        return false;
    }

    public Answer findAnswerForQuestion(Integer questionId) {
        return cao.findAll().stream().filter(answer -> answer.getQuestionId() == questionId).findFirst().get();
    }

    public void deleteAll(Collection<Answer> values) {
        CountDownLatch latch = new CountDownLatch(values.size());
        values.forEach(answer -> executor.execute(new InsertTask(answer, latch)));
        try {
            latch.await();
            logger.info("All answers deleted successfully!");
        } catch (InterruptedException e) {
            logger.error("Error deleting answers");
            logger.error(e);
        }
    }

    public void shutDown() {
        logger.info("AnswerDao is shutting down..");
        awaitTerminationAfterShutdown(executor);
        logger.info("AnswerDao has shut down !!");
    }

    private void awaitTerminationAfterShutdown(ExecutorService threadPool) {
        threadPool.shutdown();
        try {
            if (!threadPool.awaitTermination(60, TimeUnit.SECONDS)) {
                threadPool.shutdownNow();
            }
        } catch (InterruptedException ex) {
            threadPool.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    private class AnswerMapper implements DBRowMapper<Answer> {
        @Override
        public Answer mapRow(ResultSet rs) {
            try {
                int answerId = rs.getInt(ANSWER_ID);
                int questionId = rs.getInt(QUESTION_ID);
                String answerText = rs.getString(ANSWER_TEXT);
                Answer answer = new Answer(answerText);
                answer.setId(answerId);
                answer.setQuestionId(questionId);
                return answer;
            } catch (SQLException e) {
                logger.error(e);
            }
            return null;
        }
    }

    private class DeleteTask implements Runnable {
        private final Answer answer;
        private final CountDownLatch latch;

        public DeleteTask(Answer answer, CountDownLatch latch) {
            this.answer = answer;
            this.latch = latch;
        }

        @Override
        public void run() {
            Connection connection = CreateConnection.getConnection();
            PreparedStatement statement = null;
            ResultSet rs = null;
            String query = getDeleteQuery(TABLE_NAME, ANSWER_ID);
            try {
                statement = connection.prepareStatement(query);
                statement.setInt(1, answer.getId());
                logger.debug("Executing statement: {}", statement);
                int result = statement.executeUpdate();
                if (result == 1) {
                    logger.info("Answer deleted successfully");
                    latch.countDown();
                } else {
                    logger.error("Error deleting answer");
                }
                connection.commit();
            } catch (SQLException e) {
                rollback(connection);
                logger.error(e);
            } finally {
                executeFinalBlock(connection, statement, rs);
            }
        }
    }

    private class InsertTask implements Runnable {
        private final Answer answer;
        private final CountDownLatch latch;

        InsertTask(Answer answer, CountDownLatch latch) {
            this.answer = answer;
            this.latch = latch;
        }

        @Override
        public void run() {
            Connection connection = CreateConnection.getConnection();
            PreparedStatement statement = null;
            ResultSet rs = null;
            String query = getInsertQuery(TABLE_NAME, QUESTION_ID, ANSWER_TEXT);
            try {
                statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                statement.setInt(1, answer.getQuestionId());
                statement.setString(2, answer.getAnswer());
                logger.debug("Executing statement: {}", statement);
                statement.executeUpdate();
                connection.commit();
                rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    answer.setId(rs.getInt(1));
                    cao.add(answer);
                    logger.info("Answer inserted successfully, {}", answer);
                    latch.countDown();
                } else {
                    logger.error("Error inserting Answer, {}", answer);
                }
            } catch (SQLException e) {
                rollback(connection);
                logger.error(e);
            } finally {
                executeFinalBlock(connection, statement, rs);
            }
        }
    }

}
