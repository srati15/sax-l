package datatypes.quiz;

import datatypes.Domain;

import java.time.LocalDateTime;

public class Comment extends Domain<Integer> {
    private int quizId;
    private int userId;
    private String commentText;
    private LocalDateTime commentDate;

    public Comment(int quizId, int userId, String commentText, LocalDateTime commentDate) {
        this.quizId = quizId;
        this.userId = userId;
        this.commentText = commentText;
        this.commentDate = commentDate;
    }

    public int getQuizId() {
        return quizId;
    }

    public int getUserId() {
        return userId;
    }

    public String getCommentText() {
        return commentText;
    }

    public LocalDateTime getCommentDate() {
        return commentDate;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "quizId=" + quizId +
                ", userId=" + userId +
                ", commentText='" + commentText + '\'' +
                ", commentDate=" + commentDate +
                ", id=" + id +
                "} ";
    }
}
