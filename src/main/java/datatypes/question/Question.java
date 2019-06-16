package datatypes.question;

import enums.QuestionType;

public abstract class Question {
    private final String question;
    private int quizId;
    private int questionId;

    public Question(int questionId, int quizId, String question) {
        this.question = question;
        this.questionId = questionId;
        this.quizId = quizId;
    }

    public Question(String question, int quizId) {
        this.question = question;
        this.quizId = quizId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getQuestionId() {
        return questionId;
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
