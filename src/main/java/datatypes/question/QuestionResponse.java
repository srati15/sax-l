package datatypes.question;

import anotations.Column;
import enums.QuestionType;

public class QuestionResponse extends Question {
    @Column("question_type_id")
    private final QuestionType questionType = QuestionType.QuestionResponse;

    public QuestionResponse(int questionId, int quizId, String question) {
        super(questionId, quizId, question);
    }

    public QuestionResponse(String question, int quizId ) {
        super(question, quizId);
    }

    @Override
    public QuestionType getQuestionType() {
        return questionType;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
