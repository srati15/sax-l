package enums;

public enum Level {
    INFO,
    ERROR,
    FATAL,
    DEBUG,
    WARN;

    public static Level getByValue(String s) {
        for (Level level: Level.values()){
            if (s.contains(level.name())) return level;
        }
        return null;
    }
}
