package datatypes.question;

import datatypes.Domain;
import enums.QuestionType;

public abstract class Question extends Domain<Integer> {
    private final String question;
    private int quizId;

    public Question(int questionId, int quizId, String question) {
        this.question = question;
        this.id = questionId;
        this.quizId = quizId;
    }

    public Question(String question, int quizId) {
        this.question = question;
        this.quizId = quizId;
    }

    public String getQuestion() {
        return question;
    }

    public abstract QuestionType getQuestionType();

    public int getQuizId() {
        return quizId;
    }


    @Override
    public String toString() {
        return "Question{" +
                "question='" + question + '\'' +
                '}';
    }
}
