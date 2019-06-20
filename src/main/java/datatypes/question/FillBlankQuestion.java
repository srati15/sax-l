package datatypes.question;

import anotations.Column;
import enums.QuestionType;

public class FillBlankQuestion extends Question {
    @Column("question_type_id")
    private final QuestionType questionType = QuestionType.FillInTheBlank;
    public FillBlankQuestion(int questionId, int quizId, String question) {
        super(questionId, quizId, question);
    }

    public FillBlankQuestion(String question, int quizId) {
        super(question, quizId);
    }

    @Override
    public QuestionType getQuestionType() {
        return questionType;
    }
}
