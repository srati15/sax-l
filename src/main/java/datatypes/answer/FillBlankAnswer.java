package datatypes.answer;

import enums.QuestionType;

public class FillBlankAnswer extends Answer {
    public FillBlankAnswer(String answer){
        super(answer);
    }
    @Override
    QuestionType getAnswerType() {
        return QuestionType.FillInTheBlank;
    }
}
