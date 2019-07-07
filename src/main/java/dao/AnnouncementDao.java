package dao;

import database.CreateConnection;
import datatypes.Announcement;
import enums.DaoType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;

import static dao.helpers.FinalBlockExecutor.executeFinalBlock;
import static dao.helpers.FinalBlockExecutor.rollback;
import static dao.helpers.QueryGenerator.*;

public class AnnouncementDao implements Dao<Integer, Announcement> {
    private static final Logger logger = LogManager.getLogger(AnnouncementDao.class);

    public static final String ANNOUNCEMENT_ID = "id";
    public static final String ANNOUNCEMENT_TEXT = "announcement_text";
    public static final String HYPERLINK = "hyperlink";
    public static final String STATUS = "active";
    public static final String TABLE_NAME = "announcements";

    private DBRowMapper<Announcement> mapper = new AnnouncementMapper();
    private Cao<Integer, Announcement> cao = new Cao<>();
    private AtomicBoolean isCached = new AtomicBoolean(false);

    public AnnouncementDao() {
    }

    @Override
    public Announcement findById(Integer id) {
        if (!isCached.get()) cache();
        return cao.findById(id);
    }


    @Override
    public void insert(Announcement entity) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            String query = getInsertQuery(TABLE_NAME, ANNOUNCEMENT_TEXT, HYPERLINK, STATUS);
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, entity.getAnnouncementText());
            statement.setString(2, entity.getHyperLink());
            statement.setBoolean(3, entity.isActive());
            logger.info("Executing statement: {}", statement);
            int result = statement.executeUpdate();
            connection.commit();
            if (result == 1) {
                rs = statement.getGeneratedKeys();
                rs.next();
                entity.setId(rs.getInt(1));
                cao.add(entity);
                logger.info("Announcement inserted successfully {}", entity);
            } else
                logger.error("Error inserting announcement {}", entity);
        } catch (SQLException e) {
            rollback(connection);
            logger.error(e);
        } finally {
            executeFinalBlock(connection, statement, rs);
        }
    }


    @Override
    public Collection<Announcement> findAll() {
        if (!isCached.get()) cache();
        return cao.findAll();
    }

    @Override
    public void deleteById(Integer id) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        try {
            String query = getDeleteQuery(TABLE_NAME, ANNOUNCEMENT_ID);
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            logger.info("Executing statement: {}", statement);
            int result = statement.executeUpdate();
            connection.commit();
            if (result == 1) {
                cao.delete(id);
                System.out.println("Announcement Deleted Successfully");
            } else
                System.out.println("Error Deleting Announcement");
        } catch (SQLException e) {
            rollback(connection);
            logger.error(e);
        } finally {
            executeFinalBlock(connection, statement);
        }
    }

    @Override
    public void update(Announcement entity) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        try {
            String query = getUpdateQuery(TABLE_NAME, ANNOUNCEMENT_ID, ANNOUNCEMENT_TEXT, HYPERLINK, STATUS);
            statement = connection.prepareStatement(query);
            statement.setString(1, entity.getAnnouncementText());
            statement.setString(2, entity.getHyperLink());
            statement.setBoolean(3, entity.isActive());
            statement.setInt(4, entity.getId());
            int result = statement.executeUpdate();
            connection.commit();
            if (result == 1) {
                cao.add(entity);
                System.out.println("Announcement updated sucessfully");
            } else System.out.println("Error updating announcement");
        } catch (SQLException e) {
            rollback(connection);
            logger.error(e);
        } finally {
            executeFinalBlock(connection, statement);
        }
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
                return new Announcement(id, txt, hyperLink, isActive);
            } catch (SQLException e) {
                logger.error(e);
            }
            return null;
        }
    }
}
