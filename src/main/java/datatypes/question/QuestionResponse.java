package datatypes.question;

import enums.QuestionType;

public class QuestionResponse extends Question {
    public QuestionResponse(int questionId, int quizId, String question) {
        super(questionId, quizId, question);
    }

    public QuestionResponse(String question, int quizId ) {
        super(question, quizId);
    }

    @Override
    public QuestionType getQuestionType() {
        return QuestionType.QuestionResponse;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
