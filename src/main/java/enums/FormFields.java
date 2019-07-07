package enums;

import java.util.Objects;

public enum FormFields {
    username("username"),
    password("password"),
    mail("mail"),
    confirmpassword("confirmpassword"),
    firstname("firstname"),
    lastname("lastname"),
    usertype("usertype"),
    announcementText("announcementText"),
    hyperlink("hyperlink"),
    activeOrNot("activeOrNot");

    private final String value;
    FormFields(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
    public FormFields getByValue(String value) {
        for (FormFields inputType: FormFields.values()){
            if (Objects.equals(inputType.value, value)) return inputType;
        }
        return null;
    }
}
