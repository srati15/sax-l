package datatypes.question;

import enums.QuestionType;

public class MultipleChoiceQuestion extends Question {
    public MultipleChoiceQuestion(String question) {
        super(question);
    }
    @Override
    QuestionType getQuestionType() {
        return QuestionType.QuestionResponse;
    }
}
