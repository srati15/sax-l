package datatypes.question;

import anotations.Column;
import enums.QuestionType;

public class MultipleChoiceQuestion extends Question {
    @Column("question_type_id")
    private final QuestionType questionType = QuestionType.MultipleChoise;
    public MultipleChoiceQuestion(int questionId, int quizId, String question) {
        super(questionId, quizId, question);
    }

    public MultipleChoiceQuestion(String questionText, int quizId) {
        super(questionText, quizId);
    }

    @Override
    public QuestionType getQuestionType() {
        return questionType;
    }
}
