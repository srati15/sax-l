package datatypes.question;

import enums.QuestionType;

public class FillBlankQuestion extends Question {
    private final QuestionType questionType = QuestionType.FillInTheBlank;
    public FillBlankQuestion(String question) {
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
