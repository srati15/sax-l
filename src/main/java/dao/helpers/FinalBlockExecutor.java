package dao.helpers;

import dao.QuestionDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FinalBlockExecutor {
    private static final Logger logger = LogManager.getLogger(FinalBlockExecutor.class);

    public static void executeFinalBlock(Connection connection, PreparedStatement statement, ResultSet resultSet) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException ex) {
                logger.error(ex);
            }
        }
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException ex) {
                logger.error(ex);
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
                logger.error(ex);
            }
        }
    }
    public static void rollback(Connection connection) {
        if (connection != null) {
            try {
                System.out.print("Transaction is being rolled back");
                connection.rollback();
            } catch(SQLException excep) {
                excep.printStackTrace();
            }
        }
    }

    public static void executeFinalBlock(Connection connection, PreparedStatement statement) {
        executeFinalBlock(connection, statement, null);
    }
}
