package datatypes.answer;

public class Answer {
    private final String answer;
    private int answerId;
    private int questionId;
    private boolean isCorrect;

    public Answer(String answer, int answerId, int questionId, boolean isCorrect) {
        this.answer = answer;
        this.answerId = answerId;
        this.questionId = questionId;
        this.isCorrect = isCorrect;
    }

    public Answer(String answer, int questionId, boolean isCorrect) {
        this.answer = answer;
        this.questionId = questionId;
        this.isCorrect = isCorrect;
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

    public boolean isCorrect() {
        return isCorrect;
    }

    public String getAnswer() {
        return answer;
    }


}
