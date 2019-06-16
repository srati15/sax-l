package datatypes.answer;

public class Answer {
    private final String answer;
    private int answerId;
    private int questionId;

    public Answer(String answer, int answerId, int questionId) {
        this.answer = answer;
        this.answerId = answerId;
        this.questionId = questionId;
    }

    public Answer(String answer, int questionId) {
        this.answer = answer;
        this.questionId = questionId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    public int getAnswerId() {
        return answerId;
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
}
