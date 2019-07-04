package dao.helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FinalBlockExecutor {
    public static void executeFinalBlock(Connection connection, PreparedStatement statement, ResultSet resultSet) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
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
