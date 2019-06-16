package datatypes.answer;

import enums.QuestionType;

public abstract class Answer {
    private final String answer;

    public Answer(String answer) {
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    abstract QuestionType getAnswerType();

    @Override
    public String toString() {
        return "Answer{" +
                "answer='" + answer + '\'' +
                '}';
    }
}
