package datatypes.quiz;

import datatypes.Domain;

import java.time.LocalDateTime;

public class QuizResult extends Domain<Integer> {
    private final int quizId;
    private final int userId;
    private final int score;
    private final int timeSpent;
    private final LocalDateTime timestamp;
    public QuizResult(int quizId, int userId, int score, int timeSpent, LocalDateTime timestamp) {
        this.quizId = quizId;
        this.userId = userId;
        this.score = score;
        this.timeSpent = timeSpent;
        this.timestamp = timestamp;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
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
                ", timestamp=" + timestamp +
                ", id=" + id +
                "} ";
    }
}
