package datatypes.quiz.question;

import enums.QuestionType;

public class QuestionResponse extends Question {
    private final QuestionType questionType = QuestionType.QuestionResponse;
    public QuestionResponse(String question) {
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
