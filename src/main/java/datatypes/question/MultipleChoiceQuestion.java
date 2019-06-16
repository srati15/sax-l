package datatypes.question;

import enums.QuestionType;

public class MultipleChoiceQuestion extends Question {
    public MultipleChoiceQuestion(int questionId, int quizId, String question) {
        super(questionId, quizId, question);
    }

    public MultipleChoiceQuestion(String questionText, int quizId) {
        super(questionText, quizId);
    }

    @Override
    public QuestionType getQuestionType() {
        return QuestionType.QuestionResponse;
    }
}
