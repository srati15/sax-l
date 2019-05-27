package enums;

public enum QuestionType {
    QuestionResponse(1),
    FillInTheBlank(2),
    MultipleChoise(3),
    PictureResponse(4);

    private final int value;

    QuestionType(int value) {
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
