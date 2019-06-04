package enums;

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

    private String value;
    FormFields(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
    public FormFields getByValue(String value) {
        for (FormFields inputType: FormFields.values()){
            if (inputType.value == value) return inputType;
        }
        return null;
    }
}
