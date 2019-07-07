package enums;

import java.util.Objects;

public enum InputType {
    text("text"),
    password("password"),
    email("email"),
    select("select");
    private final String value;
    InputType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
    public InputType getByValue(String value) {
        for (InputType inputType: InputType.values()){
            if (Objects.equals(inputType.value, value)) return inputType;
        }
        return null;
    }
}
