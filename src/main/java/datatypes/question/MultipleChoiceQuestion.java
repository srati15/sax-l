package datatypes.question;

import enums.QuestionType;

public class MultipleChoiceQuestion extends Question {
    private final QuestionType questionType = QuestionType.MultipleChoise;

    public MultipleChoiceQuestion(String question) {
        super(question);
    }

    @Override
    public QuestionType getQuestionType() {
        return questionType;
    }
    @Override
    public String toString() {
        return "QuestionResponse{" +
                "questionType=" + questionType +
                ", id=" + id +
                "} " + super.toString();
    }
}
