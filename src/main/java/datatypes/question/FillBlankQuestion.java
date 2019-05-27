package datatypes.question;

import enums.QuestionType;

public class FillBlankQuestion extends Question {
    public FillBlankQuestion(String question){
        super(question);
    }
    @Override
    QuestionType getQuestionType() {
        return QuestionType.FillInTheBlank;
    }
}
