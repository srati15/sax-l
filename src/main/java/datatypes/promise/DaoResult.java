package datatypes.promise;

import enums.Level;

public class DaoResult implements Promise {
    private Level level;
    private String text;

    public DaoResult(Level level, String text) {
        this.level = level;
        this.text = text;
    }

    @Override
    public Level getLevel() {
        return level;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "DaoResult{" +
                "level=" + level +
                ", text='" + text + '\'' +
                '}';
    }
}
