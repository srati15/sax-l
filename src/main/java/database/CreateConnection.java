package database;

import com.mysql.cj.jdbc.MysqlDataSource;
import dao.helpers.FinalBlockExecutor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class CreateConnection {
    private static final Logger logger = LogManager.getLogger(CreateConnection.class);

    private static MysqlDataSource dataSource;
    private static MyDBInfo myDBInfo = new MyDBInfo();

    public static void setTestMyDBInfo(MyDBInfo myDBInfo1) {
        myDBInfo = myDBInfo1;
    }

    private static void initDataSource() {
        dataSource = new MysqlDataSource();
        dataSource.setURL(myDBInfo.getDatabaseUrl());
        dataSource.setUser(myDBInfo.getUserName());
        dataSource.setPassword(myDBInfo.getPassword());
        dataSource.setDatabaseName(myDBInfo.getDatabaseName());
    }


    public static Connection getConnection() {
        if (dataSource == null) initDataSource();
        try {
            Connection connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            return connection;
        } catch (SQLException e) {
            logger.error(e);
        }
        return null;
    }
}
