package enums;

public enum UserType {
    Admin(1),
    User(2);
    private int value;
    UserType(int value) {
        this.value = value;
    }

    public static UserType getById(int value) {
        for (UserType type: UserType.values()) {
            if (type.getValue() == value) return type;
        }
        return null;
    }

    public int getValue() {
        return value;
    }
}
