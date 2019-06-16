package datatypes.question;

import enums.QuestionType;

public class FillBlankQuestion extends Question {
    public FillBlankQuestion(int questionId, int quizId, String question) {
        super(questionId, quizId, question);
    }

    public FillBlankQuestion(String question, int quizId) {
        super(question, quizId);
    }

    @Override
    public QuestionType getQuestionType() {
        return QuestionType.FillInTheBlank;
    }
}
