package dao;

import database.CreateConnection;
import datatypes.Activity;
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

public class ActivityDao implements Dao<Integer, Activity> {
    private static final Logger logger = LogManager.getLogger(ActivityDao.class);

    private static final String ACTIVITY_ID = "id";
    private static final String USER_ID = "user_id";
    private static final String ACTIVITY_NAME = "activity_name";
    private static final String DATE_TIME = "date_happened";
    private static final String TABLE_NAME = "activity";

    private final DBRowMapper<Activity> mapper = new ActivityMapper();
    private final Cao<Integer, Activity> cao = new Cao<>();
    private final AtomicBoolean isCached = new AtomicBoolean(false);


    @Override
    public Activity findById(Integer id) {
        if (!isCached.get()) cache();
        return cao.findById(id);
    }


    @Override
    public void insert(Activity entity) {
        new Thread(() -> {
            Connection connection = CreateConnection.getConnection();
            PreparedStatement statement = null;
            ResultSet rs = null;
            try {
                String query = getInsertQuery(TABLE_NAME, USER_ID, ACTIVITY_NAME, DATE_TIME);
                statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                statement.setInt(1, entity.getUserId());
                statement.setString(2, entity.getActivityName());
                statement.setTimestamp(3, Timestamp.valueOf(entity.getDateTime()));
                logger.info("Executing statement: {}", statement);
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
        }).start();

    }


    @Override
    public Collection<Activity> findAll() {
        if (!isCached.get()) cache();
        return cao.findAll();
    }

    @Override
    public void deleteById(Integer id) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        try {
            String query = getDeleteQuery(TABLE_NAME, ACTIVITY_ID);
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            logger.info("Executing statement: {}", statement);
            int result = statement.executeUpdate();
            connection.commit();
            if (result == 1) {
                cao.delete(id);
                logger.info("Activity Deleted Successfully");
            } else
                logger.error("Error Deleting Activity");
        } catch (SQLException e) {
            rollback(connection);
            logger.error(e);
        } finally {
            executeFinalBlock(connection, statement);
        }
    }

    @Override
    public void update(Activity entity) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        try {
            String query = getUpdateQuery(TABLE_NAME, ACTIVITY_ID, USER_ID, ACTIVITY_NAME, DATE_TIME);
            statement = connection.prepareStatement(query);
            statement.setInt(1, entity.getUserId());
            statement.setString(2, entity.getActivityName());
            statement.setTimestamp(3, Timestamp.valueOf(entity.getDateTime()));
            statement.setInt(4, entity.getId());
            int result = statement.executeUpdate();
            connection.commit();
            if (result == 1) {
                cao.add(entity);
                logger.info("Activity updated sucessfully, {}", entity);
            } else logger.error("Error updating Activity, {}", entity);
        } catch (SQLException e) {
            rollback(connection);
            logger.error(e);
        } finally {
            executeFinalBlock(connection, statement);
        }
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
}
