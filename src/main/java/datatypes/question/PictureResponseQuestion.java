package datatypes.question;

import enums.QuestionType;

public class PictureResponseQuestion extends Question {


    public PictureResponseQuestion(int questionId, int quizId, String question) {
        super(questionId, quizId, question);
    }

    public PictureResponseQuestion(String questionText, int quizId) {
        super(questionText, quizId);
    }

    @Override
    public QuestionType getQuestionType() {
        return QuestionType.PictureResponse;
    }
}
