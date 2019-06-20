package datatypes.question;

import anotations.Column;
import anotations.Entity;
import datatypes.Domain;
import enums.QuestionType;
@Entity(table = "question")
public abstract class Question extends Domain<Integer> {
    @Column("question_text")
    private final String question;
    @Column("quiz_id")
    private final int quizId;

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
