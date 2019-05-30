package datatypes.question;

import enums.QuestionType;

public class QuestionResponse extends Question {
    public QuestionResponse(String question) {
        super(question);
    }
    @Override
    QuestionType getQuestionType() {
        return QuestionType.QuestionResponse;
    }
}
