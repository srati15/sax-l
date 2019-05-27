package datatypes.answer;

import datatypes.question.Question;
import enums.QuestionType;

public class MultipleChoiceAnswer extends Answer {
    public MultipleChoiceAnswer(String answer) {
        super(answer);
    }

    @Override
    QuestionType getAnswerType() {
        return QuestionType.MultipleChoise;
    }

}
