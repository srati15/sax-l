package datatypes;

import enums.InputType;

public class EditFormField extends FormField {
    private String defaultValue;
    private boolean disabled;
    public EditFormField(String displayName, String name, InputType inputType, boolean required, Integer minLength, String defaultValue, boolean disabled) {
        super(displayName, name, inputType, required, minLength);
        this.defaultValue = defaultValue;
        this.disabled = disabled;
    }

    public String getDefaultValue() {
        return defaultValue;
    }
    public String getDisabled() {
        return disabled? "disabled" : "";
    }

}
