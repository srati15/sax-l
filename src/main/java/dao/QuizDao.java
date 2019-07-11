package dao;

import database.CreateConnection;
import datatypes.quiz.Quiz;
import enums.DaoType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import static dao.helpers.FinalBlockExecutor.executeFinalBlock;
import static dao.helpers.FinalBlockExecutor.rollback;
import static dao.helpers.QueryGenerator.*;

public class QuizDao implements Dao<Integer, Quiz>{
    private static final Logger logger = LogManager.getLogger(QuizDao.class);

    private final DBRowMapper<Quiz> mapper = new QuizMapper();
    private final Cao<Integer, Quiz> cao = new Cao<>();
    private final AtomicBoolean isCached = new AtomicBoolean(false);
    private static final String QUIZ_ID = "id";
    private static final String QUIZ_NAME = "quiz_name";
    private static final String QUIZ_AUTHOR = "quiz_author_id";
    private static final String DATE_CREATED = "date_created";
    private static final String QUIZ_IMAGE = "quiz_image_url";
    private static final String IS_RANDOMIZED = "randomized";
    private static final String IS_PRACTICE = "is_allowed_practice_mode";
    private static final String IS_CORRECTION = "is_allowed_correction";
    private static final String IS_SINGLEPAGE = "is_single_page";
    private static final String QUIZ_DESCRIPTION = "quiz_description";

    private static final String TABLE_NAME = "quiz";

    public QuizDao() {

    }

    @Override
    public Quiz findById(Integer id) {
        if (!isCached.get()) cache();
        return cao.findById(id);
    }

    @Override
    public boolean insert(Quiz entity) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            String query = getInsertQuery(TABLE_NAME, QUIZ_AUTHOR, QUIZ_NAME, DATE_CREATED, IS_RANDOMIZED, IS_CORRECTION, IS_PRACTICE, IS_SINGLEPAGE,  QUIZ_IMAGE, QUIZ_DESCRIPTION);
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            setParameters(entity, statement);
            logger.debug("Executing statement: {}", statement);

            int result = statement.executeUpdate();
            connection.commit();
            if (result == 1) {
                logger.info("Quiz inserted sucsessfully, {}", entity);
                rs = statement.getGeneratedKeys();
                if (rs.next()){
                    entity.setId(rs.getInt(1));
                    cao.add(entity);
                    return true;
                }
            }
            else logger.error("Error inserting Quiz, {}", entity);
        } catch (SQLException e) {
            logger.error(e);
            rollback(connection);
        }finally {
            executeFinalBlock(connection, statement, rs);
        }
        return false;
    }

    @Override
    public Collection<Quiz> findAll() {
        if (!isCached.get()) cache();
        return cao.findAll();
    }

    @Override
    public boolean deleteById(Integer id) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            Quiz quiz = findById(id);
            String query = getDeleteQuery(TABLE_NAME, QUIZ_ID);
            statement = connection.prepareStatement(query);
            statement.setInt(1,id);
            logger.debug("Executing statement: {}", statement);
            int result = statement.executeUpdate();
            connection.commit();
            if (result == 1) {
                logger.info("Quiz deleted sucessfully, {}", quiz);
                cao.delete(id);
                return true;
            }
            else logger.info("Error deleting Quiz");
        } catch (SQLException e) {
            logger.error(e);
        }finally {
            executeFinalBlock(connection, statement, rs);
        }
        return false;
    }

    @Override
    public boolean update(Quiz entity) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            String query = getUpdateQuery(TABLE_NAME, QUIZ_ID, QUIZ_AUTHOR, QUIZ_NAME, DATE_CREATED, IS_RANDOMIZED, IS_CORRECTION, IS_PRACTICE, IS_SINGLEPAGE, QUIZ_IMAGE, QUIZ_DESCRIPTION);
            statement = connection.prepareStatement(query);
            setParameters(entity, statement);
            statement.setInt(10, entity.getId());
            logger.debug("Executing statement: {}", statement);

            int result = statement.executeUpdate();
            connection.commit();
            if (result == 1) {
                logger.info("Quiz updated sucessfully, {}",entity);
                cao.add(entity);
                return true;
            }
            else logger.error("Error inserting Quiz, {}", entity);
        } catch (SQLException e) {
            logger.error(e);
            rollback(connection);
        }finally {
            executeFinalBlock(connection, statement, rs);
        }
        return false;
    }

    private void setParameters(Quiz entity, PreparedStatement statement) throws SQLException {
        statement.setInt(1, entity.getAuthorId());
        statement.setString(2, entity.getQuizName());
        statement.setTimestamp(3, Timestamp.valueOf(entity.getDateCreated()));
        statement.setBoolean(4, entity.isRandomized());
        statement.setBoolean(5, entity.isAllowedImmediateCorrection());
        statement.setBoolean(6, entity.isAllowedPracticemode());
        statement.setBoolean(7, entity.isOnePage());
        statement.setString(8, entity.getQuizImageURL());
        statement.setString(9, entity.getDescription());
    }

    @Override
    public DaoType getDaoType() {
        return DaoType.Quiz;
    }
    public void cache() {
        if (isCached.get()) return;
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            String query = getSelectQuery(TABLE_NAME);
            statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                cao.add(mapper.mapRow(resultSet));
            }
            isCached.set(true);
            logger.info("{} is Cached", this.getClass().getSimpleName());
        } catch (SQLException e) {
            logger.error(e);
        }finally {
            executeFinalBlock(connection, statement, rs);
        }
    }

    public List<Quiz> findAllForUser(int userId) {
        if (!isCached.get()) cache();
        return findAll().stream().filter(quiz -> quiz.getAuthorId() == userId).collect(Collectors.toList());
    }

    private class QuizMapper implements DBRowMapper<Quiz> {
        @Override
        public Quiz mapRow(ResultSet rs) {
            try {
                int quizId = rs.getInt(QUIZ_ID);
                String quizName = rs.getString(QUIZ_NAME);
                int authorId = rs.getInt(QUIZ_AUTHOR);
                Timestamp dateCreated = rs.getTimestamp(DATE_CREATED);
                String quizImage = rs.getString(QUIZ_IMAGE);
                boolean isRandomized = rs.getBoolean(IS_RANDOMIZED);
                boolean isPractice = rs.getBoolean(IS_PRACTICE);
                boolean isCorrection = rs.getBoolean(IS_CORRECTION);
                boolean isSinglePage = rs.getBoolean(IS_SINGLEPAGE);
                String description = rs.getString(QUIZ_DESCRIPTION);
                return new Quiz(quizId, quizName, authorId, dateCreated.toLocalDateTime(), isRandomized, isSinglePage, isCorrection, isPractice, quizImage, description);
            } catch (SQLException e) {
                logger.error(e);
            }

            return null;
        }
    }

}
