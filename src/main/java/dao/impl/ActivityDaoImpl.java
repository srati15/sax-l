package dao.impl;

import dao.ActivityDao;
import dao.DBRowMapper;
import database.CreateConnection;
import datatypes.promise.DaoResult;
import datatypes.promise.Promise;
import datatypes.server.Activity;
import enums.DaoType;
import enums.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import static dao.helpers.FinalBlockExecutor.executeFinalBlock;
import static dao.helpers.FinalBlockExecutor.rollback;
import static dao.helpers.QueryGenerator.*;

@Repository
public class ActivityDaoImpl implements ActivityDao {
    private static final Logger logger = LogManager.getLogger(ActivityDaoImpl.class);
    private ThreadPoolExecutor executor;
    private static final String ACTIVITY_ID = "id";
    private static final String USER_ID = "user_id";
    private static final String ACTIVITY_NAME = "activity_name";
    private static final String DATE_TIME = "date_happened";
    private static final String TABLE_NAME = "activity";

    private static final String DELETE_QUERY = "delete from "+TABLE_NAME +" where "+USER_ID+"=?";

    private final DBRowMapper<Activity> mapper = new ActivityMapper();
    private Cao<Integer, Activity> cao;
    private final AtomicBoolean isCached = new AtomicBoolean(false);
    public ActivityDaoImpl(ThreadPoolExecutor threadPoolExecutor, Cao<Integer, Activity> cao) {
        this.executor = threadPoolExecutor ;
        this.cao = cao;
    }


    @Override
    public Activity findById(Integer id) {
        if (!isCached.get()) cache();
        return cao.findById(id);
    }


    @Override
    public Promise insert(Activity entity) {
        executor.execute(new InsertTask(entity));
        return new DaoResult(Level.INFO, "Activity inserted successfully");
    }

    @Override
    public Collection<Activity> findAll() {
        if (!isCached.get()) cache();
        return cao.findAll();
    }

    @Override
    public Promise deleteById(Integer id) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(DELETE_QUERY);
            statement.setInt(1, id);
            logger.debug("Executing statement: {}", statement);
            int result = statement.executeUpdate();
            connection.commit();
            if (result == 1) {
                logger.info("Activity Deleted Successfully, {}",findById(id));
                cao.delete(id);
                return new DaoResult(Level.INFO, "Activity deleted successfully");
            } else
                logger.error("Error Deleting Activity, {}", findById(id));
        } catch (SQLException e) {
            rollback(connection);
            logger.error(e);
        } finally {
            executeFinalBlock(connection, statement);
        }
        return new DaoResult(Level.ERROR, "Error deleting activity");
    }

    @Override
    public Promise update(Activity entity) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        try {
            String query = getUpdateQuery(TABLE_NAME, ACTIVITY_ID, USER_ID, ACTIVITY_NAME, DATE_TIME);
            statement = connection.prepareStatement(query);
            statement.setInt(1, entity.getUserId());
            statement.setString(2, entity.getActivityName());
            statement.setTimestamp(3, Timestamp.valueOf(entity.getDateTime()));
            statement.setInt(4, entity.getId());
            logger.debug("Executing statement: {}", statement);
            int result = statement.executeUpdate();
            connection.commit();
            if (result == 1) {
                cao.add(entity);
                logger.info("Activity updated sucessfully, {}", entity);
                return new DaoResult(Level.INFO, "Activity updated sucessfully");
            } else logger.error("Error updating Activity, {}", entity);
        } catch (SQLException e) {
            rollback(connection);
            logger.error(e);
        } finally {
            executeFinalBlock(connection, statement);
        }
        return new DaoResult(Level.ERROR, "Error updating Activity");
    }

    @Override
    public DaoType getDaoType() {
        return DaoType.Activity;
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
                Activity activity = mapper.mapRow(rs);
                cao.add(activity);
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

    public void shutDown() {
        logger.info("ActivityDao is shutting down..");
        awaitTerminationAfterShutdown(executor);
        logger.info("ActivityDao has shut down !!");
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

    public Collection<Activity> findAllForUser(Integer id) {

        if (!isCached.get()) cache();
        return cao.findAll().stream().filter(s->s.getUserId() == id).collect(Collectors.toList());
    }

    private class ActivityMapper implements DBRowMapper<Activity> {

        @Override
        public Activity mapRow(ResultSet rs) {
            try {
                int id = rs.getInt(ACTIVITY_ID);
                String activityName = rs.getString(ACTIVITY_NAME);
                int userId = rs.getInt(USER_ID);
                LocalDateTime dateTime = rs.getTimestamp(DATE_TIME).toLocalDateTime();
                return new Activity(id, userId, activityName, dateTime);
            } catch (SQLException e) {
                logger.error(e);
            }
            return null;
        }
    }

    private class InsertTask implements Runnable {
        private Activity entity;

        InsertTask(Activity entity) {
            this.entity = entity;
        }

        @Override
        public void run() {
            Connection connection = CreateConnection.getConnection();
            PreparedStatement statement = null;
            ResultSet rs = null;
            try {
                String query = getInsertQuery(TABLE_NAME, USER_ID, ACTIVITY_NAME, DATE_TIME);
                statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                statement.setInt(1, entity.getUserId());
                statement.setString(2, entity.getActivityName());
                statement.setTimestamp(3, Timestamp.valueOf(entity.getDateTime()));
                logger.debug("Executing statement: {}", statement);
                int result = statement.executeUpdate();
                connection.commit();
                if (result == 1) {
                    rs = statement.getGeneratedKeys();
                    rs.next();
                    entity.setId(rs.getInt(1));
                    cao.add(entity);
                    logger.info("Activity inserted successfully {}", entity);
                } else
                    logger.error("Error inserting activity {}", entity);
            } catch (SQLException e) {
                rollback(connection);
                logger.error(e);
            } finally {
                executeFinalBlock(connection, statement, rs);
            }

        }
    }
}
