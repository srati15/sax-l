package database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DBConnector {
    private Connection connection;
    public DBConnector() {
        createConnection();
    }

    private void createConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.load(getClass().getClassLoader().getResourceAsStream("database.properties"));
            String dbServer = dbProperties.getProperty("MYSQL_DATABASE_SERVER");
            int port = Integer.parseInt(dbProperties.getProperty("DATABASE_PORT"));
            String userName = dbProperties.getProperty("MYSQL_USERNAME");
            String password = dbProperties.getProperty("MYSQL_PASSWORD");
            String databaseUrl = "jdbc:mysql://" + dbServer + ":" + port;
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(databaseUrl, userName, password);
            Statement stmt = connection.createStatement();
            stmt.execute("USE " + dbProperties.getProperty("MYSQL_DATABASE_NAME"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Connection getConnection() {
        try {
            if (connection.isClosed()) createConnection();
            connection.setAutoCommit(false);
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
