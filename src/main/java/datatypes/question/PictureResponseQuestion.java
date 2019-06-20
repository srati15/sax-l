package datatypes.question;

import anotations.Column;
import enums.QuestionType;

public class PictureResponseQuestion extends Question {
    @Column("question_type_id")
    private final QuestionType questionType = QuestionType.PictureResponse;

    public PictureResponseQuestion(int questionId, int quizId, String question) {
        super(questionId, quizId, question);
    }

    public PictureResponseQuestion(String questionText, int quizId) {
        super(questionText, quizId);
    }

    @Override
    public QuestionType getQuestionType() {
        return questionType;
    }
}
