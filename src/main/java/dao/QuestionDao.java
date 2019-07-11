package dao;

import database.CreateConnection;
import datatypes.quiz.question.*;
import enums.DaoType;
import enums.QuestionType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import static dao.helpers.FinalBlockExecutor.executeFinalBlock;
import static dao.helpers.FinalBlockExecutor.rollback;
import static dao.helpers.QueryGenerator.*;

public class QuestionDao implements Dao<Integer, Question> {
    private static final Logger logger = LogManager.getLogger(QuestionDao.class);
    private ThreadPoolExecutor executor;

    private final Cao<Integer, Question> cao = new Cao<>();
    private final QuestionMapper questionMapper = new QuestionMapper();
    private final AtomicBoolean isCached = new AtomicBoolean(false);
    private static final String QUIZ_ID = "quiz_id";
    private static final String QUESTION_ID = "id";
    private static final String QUESTION_TYPE = "question_type_id";
    private static final String QUESTION_TEXT = "question_text";
    private static final String TABLE_NAME = "question";

    public QuestionDao(ThreadPoolExecutor newFixedThreadPool) {
        this.executor = newFixedThreadPool;
    }

    @Override
    public Question findById(Integer id) {
        if (!isCached.get()) cache();
        return cao.findById(id);
    }
    @Deprecated
    @Override
    public boolean insert(Question entity) {
        throw new UnsupportedOperationException();
    }

    public boolean insertAll(Set<Question> questions) {
        CountDownLatch latch = new CountDownLatch(questions.size());
        questions.forEach(question -> executor.execute(new InsertTask(question, latch)));
        try {
            latch.await();
            logger.info("All questions inserted successfully!");
            return true;
        } catch (InterruptedException e) {
            logger.error("Error inserting questions");
            logger.error(e);
        }
        return false;
    }

    @Override
    public Collection<Question> findAll() {
        if (!isCached.get()) cache();
        return cao.findAll();
    }

    @Deprecated
    @Override
    public boolean deleteById(Integer integer) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public boolean update(Question entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public DaoType getDaoType() {
        return DaoType.Question;
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
                Question question = questionMapper.mapRow(rs);
                cao.add(question);
            }
            isCached.set(true);
            logger.info("{} is Cached", this.getClass().getSimpleName());
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            executeFinalBlock(connection, statement, rs);
        }
    }

    public List<Question> getQuestionForQuiz(int quizId) {
        if (!isCached.get()) cache();

        return cao.findAll().stream().filter(question -> question.getQuizId() == quizId).collect(Collectors.toList());
    }

    public void deleteAll(Set<Question> keySet) {
        CountDownLatch latch = new CountDownLatch(keySet.size());
        keySet.forEach(question -> executor.execute(new DeleteTask(question, latch)));
        try {
            latch.await();
        } catch (InterruptedException e) {
            logger.error("Error deleting questions");
            logger.error(e);
        }
    }

    public void shutDown() {
        logger.info("QuestionDao is shutting down..");
        awaitTerminationAfterShutdown(executor);
        logger.info("QuestionDao has shut down !!");
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
    private class QuestionMapper implements DBRowMapper<Question> {
        @Override
        public Question mapRow(ResultSet rs) {
            try {
                int quizId = rs.getInt(QUIZ_ID);
                int questionId = rs.getInt(QUESTION_ID);
                int questionTypeId = rs.getInt(QUESTION_TYPE);
                String questionText = rs.getString(QUESTION_TEXT);
                QuestionType questionType = QuestionType.getById(questionTypeId);
                Question question;
                if (questionType == QuestionType.PictureResponse) {
                    question = new PictureResponseQuestion(questionText);
                } else if (questionType == QuestionType.FillInTheBlank) {
                    question = new FillBlankQuestion(questionText);
                } else if (questionType == QuestionType.QuestionResponse) {
                    question = new QuestionResponse(questionText);
                } else question = new MultipleChoiceQuestion(questionText);
                question.setQuizId(quizId);
                question.setId(questionId);
                return question;
            } catch (SQLException e) {
                logger.error(e);
            }
            return null;
        }
    }

    private class DeleteTask implements Runnable {
        private final CountDownLatch latch;
        private final Question question;

        DeleteTask(Question entity, CountDownLatch latch) {
            this.question = entity;
            this.latch = latch;
        }

        @Override
        public void run() {
            Connection connection = CreateConnection.getConnection();
            PreparedStatement statement = null;
            String query = getDeleteQuery(TABLE_NAME, QUESTION_ID);
            try {
                statement = connection.prepareStatement(query);
                statement.setInt(1, question.getId());
                logger.debug("Executing statement: {}", statement);
                int result = statement.executeUpdate();
                if (result == 1) {
                    logger.info("Question deleted successfully, {}", question);
                    latch.countDown();
                } else {
                    logger.error("Error deleting question, {}", question);
                }
                connection.commit();
            } catch (SQLException e) {
                rollback(connection);
                logger.error(e);
            } finally {
                executeFinalBlock(connection, statement, null);
            }
        }
    }

    private class InsertTask implements Runnable {
        private final Question question;
        private final CountDownLatch latch;

        InsertTask(Question question, CountDownLatch latch) {
            this.question = question;
            this.latch = latch;
        }

        @Override
        public void run() {
            Connection connection = CreateConnection.getConnection();
            PreparedStatement statement = null;
            ResultSet rs = null;
            String query = getInsertQuery(TABLE_NAME, QUESTION_TEXT, QUESTION_TYPE, QUIZ_ID);
            try {
                statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, question.getQuestion());
                statement.setInt(2, question.getQuestionType().getValue());
                statement.setInt(3, question.getQuizId());
                logger.debug("Executing statement: {}", statement);
                int result = statement.executeUpdate();
                connection.commit();
                if (result == 1) {
                    rs = statement.getGeneratedKeys();
                    rs.next();
                    question.setId(rs.getInt(1));
                    latch.countDown();
                }else {
                    logger.error("Error inserting question, {}", question);
                }
            } catch (SQLException e) {
                logger.error(e);
                rollback(connection);
            } finally {
                executeFinalBlock(connection, statement, rs);
            }
        }
    }
}
