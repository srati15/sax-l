package enums;

public enum Achievement {
    AmateurAuthor(1),
    ProlificAuthor(2),
    ProdigiousAuthor(3),
    QuizMachine(4),
    IAmTheGreatest(5),
    PracticeMakesPerfect(6);
    int value;
    Achievement(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    public static Achievement getByValue(int value){
        for (Achievement achievement : Achievement.values()) {
            if (achievement.value == value) return achievement;
        }
        return null;
    }
}
