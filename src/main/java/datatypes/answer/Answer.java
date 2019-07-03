package datatypes.answer;

import datatypes.Domain;

public class Answer extends Domain<Integer> {
    private final String answer;
    private int questionId;

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
                '}';
    }
}
