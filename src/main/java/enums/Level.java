package enums;

public enum Level {
    INFO("info"),
    ERROR("error"),
    FATAL("fatal"),
    DEBUG("debug"),
    WARN("warn");
    private String value;
    Level(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Level getByValue(String s) {
        for (Level level: Level.values()){
            if (s.contains(level.name())) return level;
        }
        return null;
    }
}
