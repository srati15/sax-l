package datatypes.answer;

import enums.QuestionType;

public class QuestionResponseAnswer extends Answer {
    public QuestionResponseAnswer(String answer) {
        super(answer);
    }

    @Override
    QuestionType getAnswerType() {
        return QuestionType.QuestionResponse;
    }
}
