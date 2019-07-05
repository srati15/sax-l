package dao;

import database.CreateConnection;
import database.mapper.AchievementMapper;
import database.mapper.DBRowMapper;
import datatypes.Achievement;
import datatypes.UserAchievement;
import enums.DaoType;

import java.sql.*;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;

import static dao.helpers.FinalBlockExecutor.executeFinalBlock;
import static dao.helpers.FinalBlockExecutor.rollback;
import static dao.helpers.QueryGenerator.getInsertQuery;
import static dao.helpers.QueryGenerator.getSelectQuery;

public class UserAchievementDao implements Dao<Integer, UserAchievement> {
    private static final String USER_ACHIEVEMENT_ID = "id";
    private static final String USER_ID = "user_id";
    private static final String ACHIEVEMENT_ID = "achievement_id";
    private static final String TABLE_NAME = "user_achievements";

    private AtomicBoolean isCached = new AtomicBoolean(false);

    private Cao<Integer, UserAchievement> cao = new Cao<>();
    private AchievementDao achievementDao = new AchievementDao();
    @Override
    public UserAchievement findById(Integer id) {
        if (!isCached.get()) cache();
        return cao.findById(id);
    }

    @Override
    public void insert(UserAchievement entity) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            Achievement achievement = achievementDao.findByName(entity.getAchievement().getAchievementName());
            String query = getInsertQuery(TABLE_NAME, USER_ID, ACHIEVEMENT_ID);
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, entity.getUserId());
            statement.setInt(2, achievement.getId());
            int result = statement.executeUpdate();
            connection.commit();
            if (result == 1) System.out.println("User Achievement inserted sucessfully");
            else System.out.println("Error inserting User Achievement");
            rs = statement.getGeneratedKeys();
            rs.next();
            entity.setId(rs.getInt(1));
            cao.add(entity);
        } catch (SQLException e) {
            e.printStackTrace();
            rollback(connection);
        } finally {
            executeFinalBlock(connection, statement, rs);
        }
    }

    @Override
    public Collection<UserAchievement> findAll() {
        if (!isCached.get()) cache();
        return cao.findAll();
    }

    @Override
    public void deleteById(Integer id) {
        // TODO: 7/5/19

    }

    @Override
    public void update(UserAchievement entity) {
        // TODO: 7/5/19

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
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            executeFinalBlock(connection, statement, rs);
        }
    }

    private class AchievementDao implements Dao<Integer, Achievement> {
        private AtomicBoolean isCached = new AtomicBoolean(false);
        private DBRowMapper<Achievement> mapper = new AchievementMapper();
        private Cao<Integer, Achievement> cao = new Cao<>();
        @Override
        public Achievement findById(Integer id) {
            if (!isCached.get()) cache();
            return cao.findById(id);
        }

        @Override
        public void insert(Achievement entity) {
            throw new UnsupportedOperationException("You can't add achievement manually");
        }

        @Override
        public Collection<Achievement> findAll() {
            if (!isCached.get()) cache();
            return cao.findAll();
        }

        @Override
        public void deleteById(Integer integer) {
            throw new UnsupportedOperationException("You can't delete achievement manually");
        }

        @Override
        public void update(Achievement entity) {
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
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                executeFinalBlock(connection, statement, rs);
            }
        }

        public Achievement findByName(String achievementName) {
            if (!isCached.get()) cache();
            return findAll().stream().filter(achievement->achievement.getAchievementName().equals(achievementName)).findFirst().get();
        }
    }
}
