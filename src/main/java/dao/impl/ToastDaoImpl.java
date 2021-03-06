package dao.impl;

import dao.DBRowMapper;
import dao.ToastDao;
import database.CreateConnection;
import datatypes.promise.DaoResult;
import datatypes.promise.Promise;
import datatypes.toast.Toast;
import enums.DaoType;
import enums.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;

import static dao.helpers.FinalBlockExecutor.executeFinalBlock;
import static dao.helpers.FinalBlockExecutor.rollback;
import static dao.helpers.QueryGenerator.*;

@Repository
public class ToastDaoImpl implements ToastDao  {
    private static final Logger logger = LogManager.getLogger(ToastDaoImpl.class);

    private static final String TOAST_ID = "id";
    private static final String USER_ID = "user_id";
    private static final String TITLE = "title";
    private static final String TOAST_TEXT = "toast_text";
    private static final String DATE_CREATED = "date_created";
    private static final String TABLE_NAME = "toast";

    private final DBRowMapper<Toast> mapper = new ToastMapper();
    private final Cao<Integer, Toast> cao;
    private final AtomicBoolean isCached = new AtomicBoolean(false);

    public ToastDaoImpl(Cao<Integer, Toast> cao) {
        this.cao = cao;
    }

    @Override
    public Toast findById(Integer id) {
        if (!isCached.get()) cache();
        return cao.findById(id);
    }


    @Override
    public Promise insert(Toast entity) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            String query = getInsertQuery(TABLE_NAME, TOAST_TEXT, USER_ID, DATE_CREATED, TITLE);
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, entity.getToastText());
            statement.setInt(2, entity.getAuthorId());
            statement.setTimestamp(3, Timestamp.valueOf(entity.getDateCreated()));
            statement.setString(4, entity.getTitle());
            logger.debug("Executing statement: {}", statement);
            int result = statement.executeUpdate();
            connection.commit();
            if (result == 1) {
                rs = statement.getGeneratedKeys();
                rs.next();
                entity.setId(rs.getInt(1));
                cao.add(entity);
                logger.info("Toast inserted successfully {}", entity);
                return new DaoResult(Level.INFO, "Toast created successfully");
            } else
                logger.error("Error inserting Toast {}", entity);
        } catch (SQLException e) {
            rollback(connection);
            logger.error(e);
        } finally {
            executeFinalBlock(connection, statement, rs);
        }
        return new DaoResult(Level.ERROR, "Error creating Toast, please try again..");
    }


    @Override
    public Collection<Toast> findAll() {
        if (!isCached.get()) cache();
        return cao.findAll();
    }

    @Override
    public Promise deleteById(Integer id) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        try {
            String query = getDeleteQuery(TABLE_NAME, TOAST_ID);
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            logger.debug("Executing statement: {}", statement);
            int result = statement.executeUpdate();
            connection.commit();
            if (result == 1) {
                logger.info("Toast Deleted Successfully, {}", findById(id));
                cao.delete(id);
                return new DaoResult(Level.INFO, "Toast deleted successfully");
            } else
                logger.error("Error Deleting Toast");
        } catch (SQLException e) {
            rollback(connection);
            logger.error(e);
        } finally {
            executeFinalBlock(connection, statement);
        }
        return new DaoResult(Level.ERROR, "Error deleting Toast, please try again");
    }

    @Override
    public Promise update(Toast entity) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        try {
            String query = getUpdateQuery(TABLE_NAME, TOAST_ID, TOAST_TEXT, DATE_CREATED, USER_ID, TITLE);
            statement = connection.prepareStatement(query);
            statement.setString(1, entity.getToastText());
            statement.setTimestamp(2, Timestamp.valueOf(entity.getDateCreated()));
            statement.setInt(3, entity.getAuthorId());
            statement.setString(4, entity.getTitle());

            statement.setInt(5, entity.getId());

            logger.debug("Executing statement: {}", statement);
            int result = statement.executeUpdate();
            connection.commit();
            if (result == 1) {
                cao.add(entity);
                logger.info("Toast updated sucessfully, {}", entity);
                return new DaoResult(Level.INFO, "Toast updated successfully");
            } else logger.error("Error updating Toast {}", entity);
        } catch (SQLException e) {
            rollback(connection);
            logger.error(e);
        } finally {
            executeFinalBlock(connection, statement);
        }
        return new DaoResult(Level.ERROR, "Error updating Toast");
    }

    @Override
    public DaoType getDaoType() {
        return DaoType.Toast;
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
                Toast Toast = mapper.mapRow(rs);
                cao.add(Toast);
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

    private class ToastMapper implements DBRowMapper<Toast> {

        @Override
        public Toast mapRow(ResultSet rs) {
            try {
                int id = rs.getInt(TOAST_ID);
                String txt = rs.getString(TOAST_TEXT);
                String title = rs.getString(TITLE);
                int userId = rs.getInt(USER_ID);
                LocalDateTime dateCreated = rs.getTimestamp(DATE_CREATED).toLocalDateTime();
                return new Toast(id, userId, title, txt, dateCreated);
            } catch (SQLException e) {
                logger.error(e);
            }
            return null;
        }
    }
}
