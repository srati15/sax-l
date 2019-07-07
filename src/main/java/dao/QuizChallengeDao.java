package dao;

import database.CreateConnection;
import datatypes.messages.QuizChallenge;
import enums.DaoType;
import enums.RequestStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;

import static dao.helpers.FinalBlockExecutor.executeFinalBlock;
import static dao.helpers.FinalBlockExecutor.rollback;
import static dao.helpers.QueryGenerator.*;

public class QuizChallengeDao implements Dao<Integer, QuizChallenge> {
    private static final Logger logger = LogManager.getLogger(QuizChallengeDao.class);

    private static final String ID = "id";
    private static final String SENDER_ID = "sender_id";
    private static final String RECEIVER_ID = "receiver_id";
    private static final String QUIZ_ID = "quiz_id";
    private static final String STATUS = "status";
    private static final String TIME_SENT = "time-sent";
    private static final String TABLE_NAME = "quiz_challenges";


    private final Cao<Integer,QuizChallenge>cao = new Cao<>();
    private final DBRowMapper<QuizChallenge> mapper = new QuizChallengeMapper();
    private final AtomicBoolean isCached = new AtomicBoolean(false);

    public QuizChallengeDao(){

    }

    @Override
    public QuizChallenge findById(Integer integer) {
        if (!isCached.get()) cache();
            return cao.findById(integer);
    }

    @Override
    public void insert(QuizChallenge entity) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement;
        ResultSet rs;
        try {
            String query = getInsertQuery(TABLE_NAME, SENDER_ID, RECEIVER_ID, QUIZ_ID, STATUS, TIME_SENT);
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, entity.getSenderId());
            statement.setInt(2, entity.getReceiverId());
            statement.setInt(3,entity.getQuizId());
            statement.setInt(4, entity.getRequestStatus().getValue());
            statement.setTimestamp(5, entity.getTimestamp());
            int result = statement.executeUpdate();
            connection.commit();
            if (result == 1){
                System.out.println("quizChallenge inserted successfully");
                rs = statement.getGeneratedKeys();
                rs.next();
                entity.setId(rs.getInt(1));
                cao.add(entity);
            }
            else
                System.out.println("Error inserting quizChallenge");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Collection<QuizChallenge> findAll() {
        if (!isCached.get()) cache();
        return cao.findAll();
    }

    @Override
    public void deleteById(Integer integer) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        try {
            String query = getDeleteQuery(TABLE_NAME, ID);
            statement = connection.prepareStatement(query);
            statement.setInt(1, integer);
            int result = statement.executeUpdate();
            connection.commit();
            if (result == 1) {
                System.out.println("quizChallenge Deleted Successfully");
                cao.delete(integer);
            } else
                System.out.println("Error Deleting quizChallenge");
        } catch (SQLException e) {
            rollback(connection);
            e.printStackTrace();
        } finally {
            executeFinalBlock(connection, statement);
        }
    }

    @Override
    public void update(QuizChallenge entity) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        try {
            String query = getUpdateQuery(TABLE_NAME, ID, STATUS);
            statement = connection.prepareStatement(query);
            statement.setInt(1, entity.getRequestStatus().getValue());
            statement.setInt(2, entity.getId());
            int result = statement.executeUpdate();
            connection.commit();
            if (result == 1) {
                System.out.println("quizChallenge accepted Successfully");
                cao.add(entity);
            } else
                System.out.println("Error accepting quizChallenge");
        } catch (SQLException e) {
            e.printStackTrace();
            rollback(connection);
        } finally {
            executeFinalBlock(connection, statement);
        }
    }

    @Override
    public DaoType getDaoType() {
        return DaoType.QuizChallenge;
    }

    public QuizChallenge findBySenderReceiverId(int senderId, int receiverId) {
        return cao.findAll().stream().filter(s -> s.getSenderId() == senderId && s.getReceiverId() == receiverId).findFirst().orElse(null);
    }

    @Override
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
                QuizChallenge request = mapper.mapRow(rs);
                cao.add(request);
            }
            isCached.set(true);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            executeFinalBlock(connection, statement, rs);
        }
    }

    private class QuizChallengeMapper implements DBRowMapper<QuizChallenge> {
        @Override
        public QuizChallenge mapRow(ResultSet rs) {
            try {
                int quizChallengeId = rs.getInt(ID);
                int senderId = rs.getInt(SENDER_ID);
                int receiverId = rs.getInt(RECEIVER_ID);
                int quizId = rs.getInt(QUIZ_ID);
                int status = rs.getInt(STATUS);
                RequestStatus currStatus = RequestStatus.getByValue(status);
                Timestamp dateSent = rs.getTimestamp(TIME_SENT);


                return new QuizChallenge(quizChallengeId, senderId, receiverId, quizId, currStatus, dateSent );
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}

