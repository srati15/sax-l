package datatypes;

public class QuizResult extends Domain<Integer>{
    private int quizId;
    private int userId;
    private int score;
    private int timeSpent;

    public QuizResult(int quizId, int userId, int score, int timeSpent) {
        this.quizId = quizId;
        this.userId = userId;
        this.score = score;
        this.timeSpent = timeSpent;
    }

    public int getTimeSpent() {
        return timeSpent;
    }

    public int getQuizId() {
        return quizId;
    }

    public int getUserId() {
        return userId;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "QuizResult{" +
                "quizId=" + quizId +
                ", userId=" + userId +
                ", score=" + score +
                ", timeSpent=" + timeSpent +
                ", id=" + id +
                '}';
    }
}
