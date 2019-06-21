package datatypes.answer;

import anotations.Column;
import anotations.Entity;
import datatypes.Domain;
@Entity(table = "answers")
public class Answer extends Domain<Integer> {
    @Column("answer_string")
    private String answer;

    @Column("question_id")
    private int questionId;
    public Answer(){}
    public Answer(String answer, int answerId, int questionId) {
        this.answer = answer;
        this.id = answerId;
        this.questionId = questionId;
    }

    public Answer(String answer, int questionId) {
        this.answer = answer;
        this.questionId = questionId;
    }

    public Answer(String answer) {
        this.answer = answer;
    }

    public int getQuestionId() {
        return questionId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "answer='" + answer + '\'' +
                ", questionId=" + questionId +
                ", id=" + id +
                "} " + super.toString();
    }
}
