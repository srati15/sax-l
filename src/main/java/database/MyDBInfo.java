package database;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.io.IOException;
import java.util.Properties;

public class MyDBInfo {
    private String dbServer;
    private int port;
    private String userName;
    private String databaseUrl;
    private String password;

    public MyDBInfo(){
        initProperties();
    }

    private void initProperties() {
        Properties dbProperties = new Properties();
        try {
            dbProperties.load(getClass().getClassLoader().getResourceAsStream("database.properties"));
            this.dbServer = dbProperties.getProperty("MYSQL_DATABASE_SERVER");
            this.port = Integer.parseInt(dbProperties.getProperty("DATABASE_PORT"));
            this.userName = dbProperties.getProperty("MYSQL_USERNAME");
            this.password = dbProperties.getProperty("MYSQL_PASSWORD");
            this.databaseUrl = "jdbc:mysql://" + dbServer + ":" + port;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getUserName() {
        return userName;
    }

    public String getDatabaseUrl() {
        return databaseUrl;
    }

    public String getPassword() {
        return password;
    }
}
