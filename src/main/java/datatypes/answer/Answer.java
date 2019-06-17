package datatypes.answer;

import datatypes.Domain;

public class Answer extends Domain<Integer> {
    private final String answer;
    private int questionId;

    public Answer(String answer, int answerId, int questionId) {
        this.answer = answer;
        this.id = answerId;
        this.questionId = questionId;
    }

    public Answer(String answer, int questionId) {
        this.answer = answer;
        this.questionId = questionId;
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
