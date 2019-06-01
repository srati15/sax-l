package database;

import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class CreateConnection {
    private static MysqlDataSource dataSource;
    private static MyDBInfo myDBInfo = new MyDBInfo();

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
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
