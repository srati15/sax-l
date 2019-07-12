package dao;

import database.CreateConnection;
import datatypes.quiz.Comment;
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

public class CommentDao implements Dao<Integer, Comment> {
    private static final Logger logger = LogManager.getLogger(CommentDao.class);

    private static final String COMMENT_ID = "id";
    private static final String USER_ID = "user_id";
    private static final String COMMENT_TEXT = "comment_text";
    private static final String COMMENT_DATE = "comment_date";
    private static final String QUIZ_ID = "quiz_id";
    private static final String TABLE_NAME = "comment";

    private final DBRowMapper<Comment> mapper = new CommentMapper();
    private final Cao<Integer, Comment> cao = new Cao<>();
    private final AtomicBoolean isCached = new AtomicBoolean(false);

    @Override
    public Comment findById(Integer id) {
        if (!isCached.get()) cache();
        return cao.findById(id);
    }


    @Override
    public boolean insert(Comment entity) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            String query = getInsertQuery(TABLE_NAME, COMMENT_TEXT, QUIZ_ID, USER_ID, COMMENT_DATE);
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, entity.getCommentText());
            statement.setInt(2, entity.getQuizId());
            statement.setInt(3, entity.getUserId());
            statement.setTimestamp(4, Timestamp.valueOf(entity.getCommentDate()));
            logger.debug("Executing statement: {}", statement);
            int result = statement.executeUpdate();
            connection.commit();
            if (result == 1) {
                rs = statement.getGeneratedKeys();
                rs.next();
                entity.setId(rs.getInt(1));
                cao.add(entity);
                logger.info("comment inserted successfully {}", entity);
                return true;
            } else
                logger.error("Error inserting comment {}", entity);
        } catch (SQLException e) {
            rollback(connection);
            logger.error(e);
        } finally {
            executeFinalBlock(connection, statement, rs);
        }
        return false;
    }


    @Override
    public Collection<Comment> findAll() {
        if (!isCached.get()) cache();
        return cao.findAll();
    }

    @Override
    public boolean deleteById(Integer id) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        try {
            String query = getDeleteQuery(TABLE_NAME, COMMENT_ID);
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            logger.debug("Executing statement: {}", statement);
            int result = statement.executeUpdate();
            connection.commit();
            if (result == 1) {
                logger.info("Comment Deleted Successfully, {}", findById(id));
                cao.delete(id);
                return true;
            } else
                logger.error("Error Deleting Comment, {}", findById(id));
        } catch (SQLException e) {
            rollback(connection);
            logger.error(e);
        } finally {
            executeFinalBlock(connection, statement);
        }
        return false;
    }

    @Override
    public boolean update(Comment entity) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        try {
            String query = getUpdateQuery(TABLE_NAME, COMMENT_ID, COMMENT_TEXT, USER_ID, QUIZ_ID, COMMENT_DATE);
            statement = connection.prepareStatement(query);
            statement.setString(1, entity.getCommentText());
            statement.setInt(2, entity.getUserId());
            statement.setInt(3, entity.getQuizId());
            statement.setTimestamp(4, Timestamp.valueOf(entity.getCommentDate()));
            statement.setInt(5, entity.getId());

            logger.debug("Executing statement: {}", statement);
            int result = statement.executeUpdate();
            connection.commit();
            if (result == 1) {
                cao.add(entity);
                logger.info("comment updated sucessfully, {}", entity);
                return true;
            } else logger.error("Error updating comment {}", entity);
        } catch (SQLException e) {
            rollback(connection);
            logger.error(e);
        } finally {
            executeFinalBlock(connection, statement);
        }
        return false;
    }

    @Override
    public DaoType getDaoType() {
        return DaoType.Comment;
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
                Comment comment = mapper.mapRow(rs);
                cao.add(comment);
            }
            isCached.set(true);
            logger.info("{} is Cached", this.getClass().getSimpleName());
        } catch (SQLException e) {
            rollback(connection);
            logger.error(e);
        } finally {
            executeFinalBlock(connection, statement, rs);
        }
    }

    private class CommentMapper implements DBRowMapper<Comment> {

        @Override
        public Comment mapRow(ResultSet rs) {
            try {
                int id = rs.getInt(COMMENT_ID);
                String txt = rs.getString(COMMENT_TEXT);
                int quizId = rs.getInt(QUIZ_ID);
                LocalDateTime commentDate = rs.getTimestamp(COMMENT_DATE).toLocalDateTime();
                int userId = rs.getInt(USER_ID);
                Comment comment = new Comment(quizId, userId, txt, commentDate);
                comment.setId(id);
                return comment;
            } catch (SQLException e) {
                logger.error(e);
            }
            return null;
        }
    }
}
