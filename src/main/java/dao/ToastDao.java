package dao;

import database.CreateConnection;
import datatypes.announcement.Announcement;
import enums.DaoType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;

import static dao.helpers.FinalBlockExecutor.executeFinalBlock;
import static dao.helpers.FinalBlockExecutor.rollback;
import static dao.helpers.QueryGenerator.*;

public class ToastDao implements Dao<Integer, Announcement> {
    private static final Logger logger = LogManager.getLogger(ToastDao.class);

    private static final String ANNOUNCEMENT_ID = "id";
    private static final String USER_ID = "user_id";
    private static final String ANNOUNCEMENT_TEXT = "announcement_text";
    private static final String HYPERLINK = "hyperlink";
    private static final String STATUS = "active";
    private static final String TABLE_NAME = "announcements";

    private final DBRowMapper<Announcement> mapper = new AnnouncementMapper();
    private final Cao<Integer, Announcement> cao = new Cao<>();
    private final AtomicBoolean isCached = new AtomicBoolean(false);

    public ToastDao() {
    }

    @Override
    public Announcement findById(Integer id) {
        if (!isCached.get()) cache();
        return cao.findById(id);
    }


    @Override
    public boolean insert(Announcement entity) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            String query = getInsertQuery(TABLE_NAME, ANNOUNCEMENT_TEXT, HYPERLINK, STATUS, USER_ID);
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, entity.getAnnouncementText());
            statement.setString(2, entity.getHyperLink());
            statement.setBoolean(3, entity.isActive());
            statement.setInt(4, entity.getUserId());
            logger.debug("Executing statement: {}", statement);
            int result = statement.executeUpdate();
            connection.commit();
            if (result == 1) {
                rs = statement.getGeneratedKeys();
                rs.next();
                entity.setId(rs.getInt(1));
                cao.add(entity);
                logger.info("Announcement inserted successfully {}", entity);
                return true;
            } else
                logger.error("Error inserting announcement {}", entity);
        } catch (SQLException e) {
            rollback(connection);
            logger.error(e);
        } finally {
            executeFinalBlock(connection, statement, rs);
        }
        return false;
    }


    @Override
    public Collection<Announcement> findAll() {
        if (!isCached.get()) cache();
        return cao.findAll();
    }

    @Override
    public boolean deleteById(Integer id) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        try {
            String query = getDeleteQuery(TABLE_NAME, ANNOUNCEMENT_ID);
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            logger.debug("Executing statement: {}", statement);
            int result = statement.executeUpdate();
            connection.commit();
            if (result == 1) {
                logger.info("Announcement Deleted Successfully, {}", findById(id));
                cao.delete(id);
                return true;
            } else
                logger.error("Error Deleting Announcement");
        } catch (SQLException e) {
            rollback(connection);
            logger.error(e);
        } finally {
            executeFinalBlock(connection, statement);
        }
        return false;
    }

    @Override
    public boolean update(Announcement entity) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        try {
            String query = getUpdateQuery(TABLE_NAME, ANNOUNCEMENT_ID, ANNOUNCEMENT_TEXT, HYPERLINK, STATUS, USER_ID);
            statement = connection.prepareStatement(query);
            statement.setString(1, entity.getAnnouncementText());
            statement.setString(2, entity.getHyperLink());
            statement.setBoolean(3, entity.isActive());
            statement.setInt(4, entity.getUserId());
            statement.setInt(5, entity.getId());

            logger.debug("Executing statement: {}", statement);
            int result = statement.executeUpdate();
            connection.commit();
            if (result == 1) {
                cao.add(entity);
                logger.info("Announcement updated sucessfully, {}", entity);
                return true;
            } else logger.error("Error updating announcement {}", entity);
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
        return DaoType.Announcement;
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
                Announcement announcement = mapper.mapRow(rs);
                cao.add(announcement);
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

    private class AnnouncementMapper implements DBRowMapper<Announcement> {

        @Override
        public Announcement mapRow(ResultSet rs) {
            try {
                int id = rs.getInt(ANNOUNCEMENT_ID);
                String txt = rs.getString(ANNOUNCEMENT_TEXT);
                String hyperLink = rs.getString(HYPERLINK);
                boolean isActive = rs.getBoolean(STATUS);
                int userId = rs.getInt(USER_ID);
                return new Announcement(id, userId, txt, hyperLink, isActive);
            } catch (SQLException e) {
                logger.error(e);
            }
            return null;
        }
    }
}
