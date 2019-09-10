package dao.impl;

import dao.DBRowMapper;
import dao.Dao;
import dao.UserAchievementDao;
import database.CreateConnection;
import datatypes.promise.DaoResult;
import datatypes.promise.Promise;
import datatypes.user.Achievement;
import datatypes.user.UserAchievement;
import enums.DaoType;
import enums.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;

import static dao.helpers.FinalBlockExecutor.executeFinalBlock;
import static dao.helpers.FinalBlockExecutor.rollback;
import static dao.helpers.QueryGenerator.*;

@Repository
public class UserAchievementDaoImpl implements UserAchievementDao {
    private static final Logger logger = LogManager.getLogger(UserAchievementDaoImpl.class);

    private static final String USER_ACHIEVEMENT_ID = "id";
    private static final String USER_ID = "user_id";
    private static final String ACHIEVEMENT_ID = "achievement_id";
    private static final String TABLE_NAME = "user_achievements";

    private final AtomicBoolean isCached = new AtomicBoolean(false);

    private final Cao<Integer, UserAchievement> cao;
    private final AchievementDao achievementDao = new AchievementDao();

    public UserAchievementDaoImpl(Cao<Integer, UserAchievement> cao) {
        this.cao = cao;
    }

    @Override
    public UserAchievement findById(Integer id) {
        if (!isCached.get()) cache();
        return cao.findById(id);
    }

    @Override
    public Promise insert(UserAchievement entity) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            Achievement achievement = achievementDao.findByName(entity.getAchievement().getAchievementName());
            String query = getInsertQuery(TABLE_NAME, USER_ID, ACHIEVEMENT_ID);
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, entity.getUserId());
            statement.setInt(2, achievement.getId());
            logger.debug("Executing statement: {}", statement);
            int result = statement.executeUpdate();
            connection.commit();
            if (result == 1) {
                logger.info("User Achievement inserted sucessfully, {}", entity);
                rs = statement.getGeneratedKeys();
                rs.next();
                entity.setId(rs.getInt(1));
                cao.add(entity);
                return new DaoResult(Level.INFO, "Congratulations, you gained Achievement "+entity.getAchievement().getAchievementName());
            }
            else logger.error("Error inserting User Achievement, {}", entity);

        } catch (SQLException e) {
            logger.error(e);
            rollback(connection);
        } finally {
            executeFinalBlock(connection, statement, rs);
        }
        return new DaoResult(Level.ERROR, "Error inserting achievement");
    }

    @Override
    public Collection<UserAchievement> findAll() {
        if (!isCached.get()) cache();
        return cao.findAll();
    }
    @Override
    public Promise deleteById(Integer id) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        try {
            String query = getDeleteQuery(TABLE_NAME, USER_ACHIEVEMENT_ID);
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            logger.debug("Executing statement: {}", statement);
            int result = statement.executeUpdate();
            connection.commit();
            if (result == 1) {
                logger.info("User Achievement Deleted Successfully, {}", findById(id));
                cao.delete(id);
                return new DaoResult(Level.INFO, "You lost Achievement :(");
            } else
                logger.error("Error Deleting User Achievement");
        } catch (SQLException e) {
            rollback(connection);
            logger.error(e);
        } finally {
            executeFinalBlock(connection, statement);
        }
        return new DaoResult(Level.ERROR, "Error deleting achievement");
    }
    @Deprecated
    @Override
    public Promise update(UserAchievement entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public DaoType getDaoType() {
        return DaoType.UserAchievement;
    }

    @Override
    public void cache() {
        if (!achievementDao.isCached.get()) achievementDao.cache();
        if (isCached.get()) return;
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            String query = getSelectQuery(TABLE_NAME);
            statement = connection.prepareStatement(query);
            rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(USER_ACHIEVEMENT_ID);
                int userId = rs.getInt(USER_ID);
                int achievemenetId = rs.getInt(ACHIEVEMENT_ID);
                Achievement achievement = achievementDao.findById(achievemenetId);
                UserAchievement userAchievement = new UserAchievement(userId, achievement);
                userAchievement.setId(id);
                cao.add(userAchievement);
            }
            isCached.set(true);
            logger.info("{} is Cached", this.getClass().getSimpleName());
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            executeFinalBlock(connection, statement, rs);
        }
    }

    private class AchievementDao implements Dao<Integer, Achievement> {
        private final AtomicBoolean isCached = new AtomicBoolean(false);
        private final DBRowMapper<Achievement> mapper = new AchievementMapper();
        private final Cao<Integer, Achievement> cao = new Cao<>();
        @Override
        public Achievement findById(Integer id) {
            if (!isCached.get()) cache();
            return cao.findById(id);
        }

        @Override
        public Promise insert(Achievement entity) {
            throw new UnsupportedOperationException("You can't add achievement manually");
        }

        @Override
        public Collection<Achievement> findAll() {
            if (!isCached.get()) cache();
            return cao.findAll();
        }

        @Override
        public Promise deleteById(Integer integer) {
            throw new UnsupportedOperationException("You can't delete achievement manually");
        }

        @Override
        public Promise update(Achievement entity) {
            throw new UnsupportedOperationException("You can't update achievement manually");
        }

        @Override
        public DaoType getDaoType() {
            return null;
        }

        @Override
        public void cache() {
            if (isCached.get()) return;
            Connection connection = CreateConnection.getConnection();
            PreparedStatement statement = null;
            ResultSet rs = null;
            try {
                String query = getSelectQuery(AchievementMapper.TABLE_NAME);
                statement = connection.prepareStatement(query);
                rs = statement.executeQuery();
                while (rs.next()) {
                    Achievement achievement = mapper.mapRow(rs);
                    cao.add(achievement);
                }
                isCached.set(true);
                logger.info("{} is Cached", this.getClass().getSimpleName());
            } catch (SQLException e) {
                logger.error(e);
            } finally {
                executeFinalBlock(connection, statement, rs);
            }
        }

        public Achievement findByName(String achievementName) {
            if (!isCached.get()) cache();
            return findAll().stream().filter(achievement->achievement.getAchievementName().equals(achievementName)).findFirst().get();
        }
    }
    private static class AchievementMapper implements DBRowMapper<Achievement> {
        static final String ACHIEVEMENT_ID = "id";
        static final String ACHIEVEMENT_NAME = "achievement_name";
        static final String ACHIEVEMENT_CRITERIA = "achievement_criteria";

        static final String TABLE_NAME = "achievements";
        @Override
        public Achievement mapRow(ResultSet rs) {
            try {
                int id = rs.getInt(ACHIEVEMENT_ID);
                String achievementName = rs.getString(ACHIEVEMENT_NAME);
                String achievementCriteria = rs.getString(ACHIEVEMENT_CRITERIA);
                Achievement achievement = new Achievement(achievementName, achievementCriteria );
                achievement.setId(id);
                return achievement;
            } catch (SQLException e) {
                logger.error(e);
            }
            return null;
        }
    }
}
