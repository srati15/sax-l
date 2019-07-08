package datatypes.formfields;

import java.util.List;

public class SelectField {
    private final String displayName;
    private final String name;
    private final List<String> selectStrings;

    public SelectField(String displayName, String name, List<String> selectStrings) {
        this.displayName = displayName;
        this.name = name;
        this.selectStrings = selectStrings;
    }

    public String getDisplayName() {
        return displayName;
    }

    public List<String> getSelectStrings() {
        return selectStrings;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "SelectField{" +
                "displayName='" + displayName + '\'' +
                ", name='" + name + '\'' +
                ", selectStrings=" + selectStrings +
                '}';
    }

}
