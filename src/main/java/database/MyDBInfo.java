package database;

import dao.helpers.FinalBlockExecutor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Properties;

public class MyDBInfo {
    private static final Logger logger = LogManager.getLogger(MyDBInfo.class);

    private String userName;
    private String databaseUrl;
    private String password;
    private String databaseName;

    public MyDBInfo(){
        initProperties();
    }

    private void initProperties() {
        Properties dbProperties = new Properties();
        try {
            dbProperties.load(getClass().getClassLoader().getResourceAsStream("database.properties"));
            String dbServer = dbProperties.getProperty("MYSQL_DATABASE_SERVER");
            int port = Integer.parseInt(dbProperties.getProperty("DATABASE_PORT"));
            this.databaseName = dbProperties.getProperty("MYSQL_DATABASE_NAME");
            this.userName = dbProperties.getProperty("MYSQL_USERNAME");
            this.password = dbProperties.getProperty("MYSQL_PASSWORD");
            this.databaseUrl = "jdbc:mysql://" + dbServer + ":" + port +"/"+databaseName;
        } catch (IOException e) {
            logger.error(e);
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

    public String getDatabaseName() {
        return databaseName;
    }
}
