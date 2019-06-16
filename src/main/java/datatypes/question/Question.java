package datatypes.question;

import enums.QuestionType;

public abstract class Question {
    private final String question;
    private int quizId;
    private int authorId;
    private QuestionType questionType;
    private int questionId;

    public Question(String question) {
        this.question = question;
    }

    abstract QuestionType getQuestionType();

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getQuizId() {
        return quizId;
    }

    public int getAuthorId() {
        return authorId;
    }

    @Override
    public String toString() {
        return "Question{" +
                "question='" + question + '\'' +
                '}';
    }
}
