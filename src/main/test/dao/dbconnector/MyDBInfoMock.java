package dao.dbconnector;

import database.MyDBInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Properties;

public class MyDBInfoMock extends MyDBInfo {
    private static final Logger logger = LogManager.getLogger(MyDBInfoMock.class);

    private String dbServer;
    private int port;
    private String userName;
    private String databaseUrl;
    private String password;
    private String databaseName;

    public MyDBInfoMock(){
        initProperties();
    }

    private void initProperties() {
        Properties dbProperties = new Properties();
        try {
            dbProperties.load(getClass().getClassLoader().getResourceAsStream("database.properties"));
            this.dbServer = dbProperties.getProperty("MYSQL_DATABASE_SERVER");
            this.port = Integer.parseInt(dbProperties.getProperty("DATABASE_PORT"));
            this.databaseName = dbProperties.getProperty("MYSQL_DATABASE_NAME");
            this.userName = dbProperties.getProperty("MYSQL_USERNAME");
            this.password = dbProperties.getProperty("MYSQL_PASSWORD");
            this.databaseUrl = "jdbc:mysql://" + dbServer + ":" + port+"/"+databaseName;
        } catch (IOException e) {
            logger.error(e);
        }
    }

    @Override
    public String toString() {
        return "MyDBInfoMock{" +
                "dbServer='" + dbServer + '\'' +
                ", port=" + port +
                ", userName='" + userName + '\'' +
                ", databaseUrl='" + databaseUrl + '\'' +
                ", password='" + password + '\'' +
                ", databaseName='" + databaseName + '\'' +
                '}';
    }
}
