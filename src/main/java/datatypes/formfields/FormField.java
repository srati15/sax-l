package datatypes.formfields;

import enums.InputType;


public class FormField {
    private final String name;
    private final InputType inputType;
    private final boolean required;
    private final Integer minLength;
    private final String displayName;
    public FormField(String displayName, String name, InputType inputType, boolean required, Integer minLength) {
        this.displayName = displayName;
        this.name = name;
        this.inputType = inputType;
        this.required = required;
        this.minLength = minLength;
    }

    public String getName() {
        return name;
    }

    public InputType getInputType() {
        return inputType;
    }

    public String getRequired() {
        return required? "required" : "";
    }

    public Integer getMinLength() {
        return minLength;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return "FormField{" +
                "name='" + name + '\'' +
                ", inputType=" + inputType +
                ", required=" + required +
                ", minLength=" + minLength +
                ", displayName='" + displayName + '\'' +
                '}';
    }


}
