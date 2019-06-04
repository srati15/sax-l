package enums;

public enum InputType {
    text("text"),
    password("password"),
    email("email"),
    select("select");
    private String value;
    InputType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
    public InputType getByValue(String value) {
        for (InputType inputType: InputType.values()){
            if (inputType.value == value) return inputType;
        }
        return null;
    }
}
