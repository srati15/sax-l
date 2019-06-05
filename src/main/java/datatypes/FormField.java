package datatypes;

import enums.InputType;


public class FormField {
    private String name;
    private InputType inputType;
    private boolean required;
    private Integer minLength;
    private String displayName;
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
