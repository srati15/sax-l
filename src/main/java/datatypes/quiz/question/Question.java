package datatypes.quiz.question;

import datatypes.Domain;
import enums.QuestionType;

public abstract class Question extends Domain<Integer> {
    private final String question;
    private int quizId;

    public Question(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public abstract QuestionType getQuestionType();

    public int getQuizId() {
        return quizId;
    }

    @Override
    public String toString() {
        return "Question{" +
                "question='" + question + '\'' +
                ", quizId=" + quizId +
                ", id=" + id +
                '}';
    }
}
