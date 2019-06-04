package datatypes;

import enums.InputType;


public class FormField {
    private String name;
    private InputType inputType;
    private boolean required;
    private Integer minLength;
    public FormField(String name, InputType inputType, boolean required, Integer minLength) {
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

    public boolean isRequired() {
        return required;
    }

    public Integer getMinLength() {
        return minLength;
    }

    @Override
    public String toString() {
        return "FormField{" +
                "name='" + name + '\'' +
                ", inputType=" + inputType +
                ", required=" + required +
                ", minLength=" + minLength +
                '}';
    }

}
