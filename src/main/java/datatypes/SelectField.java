package datatypes;

import java.util.List;
import java.util.Map;

public class SelectField {
    private String name;
    private List<String> selectStrings;

    public SelectField(String name, List<String> selectStrings) {
        this.name = name;
        this.selectStrings = selectStrings;
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
                "name='" + name + '\'' +
                ", selectStrings=" + selectStrings +
                '}';
    }
}
