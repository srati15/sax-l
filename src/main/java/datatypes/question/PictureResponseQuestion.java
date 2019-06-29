package datatypes.question;

import enums.QuestionType;

public class PictureResponseQuestion extends Question {
    private final QuestionType questionType = QuestionType.PictureResponse;

    public PictureResponseQuestion(String question) {
        super(question);
    }

    @Override
    public QuestionType getQuestionType() {
        return questionType;
    }

    @Override
    public String toString() {
        return "PictureResponseQuestion{" +
                "questionType=" + questionType +
                ", id=" + id +
                "} " + super.toString();
    }
}
