package datatypes.question;

import enums.QuestionType;

public class PictureResponseQuestion extends Question {

    public PictureResponseQuestion(String imageSource){
        super(imageSource);
    }

    @Override
    QuestionType getQuestionType() {
        return QuestionType.PictureResponse;
    }
}
