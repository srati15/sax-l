package datatypes.server;

import enums.Level;

public class ServerLog {
    private Level level;
    private String date;
    private String message;

    public ServerLog(Level level, String date, String message) {
        this.level = level;
        this.date = date;
        this.message = message;
    }

    public Level getLevel() {
        return level;
    }

    public String getDate() {
        return date;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ServerLog{" +
                "level='" + level + '\'' +
                ", date='" + date + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
