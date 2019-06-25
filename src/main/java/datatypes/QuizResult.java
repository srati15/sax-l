package datatypes;

public class QuizResult extends Domain<Integer>{
    private int quizId;
    private int userId;
    private int score;

    public QuizResult(int quizId, int userId, int score) {
        this.quizId = quizId;
        this.userId = userId;
        this.score = score;
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
                ", id=" + id +
                '}';
    }
}
