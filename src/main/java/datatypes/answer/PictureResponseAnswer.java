package datatypes.answer;

import enums.QuestionType;

public class PictureResponseAnswer extends Answer {
    public PictureResponseAnswer(String answer) {
        super(answer);
    }

    @Override
    QuestionType getAnswerType() {
        return QuestionType.PictureResponse;
    }
}
