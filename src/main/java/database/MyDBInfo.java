package database;

import java.io.IOException;
import java.util.Properties;

public class MyDBInfo {
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

    public String getDatabaseName() {
        return databaseName;
    }
}
