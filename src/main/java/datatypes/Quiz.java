package datatypes;

import anotations.Column;
import anotations.Entity;

import java.sql.Timestamp;

@Entity(table = "quiz")
public class Quiz extends Domain<Integer> {
    @Column("quiz_name")
    private String quizName;

    @Column("quiz_author_id")
    private int authorId;

    @Column("times_done")
    private int timesDone;

    @Column("date_created")
    private Timestamp dateCreated;

    @Column("randomized")
    private boolean randomized;

    @Column("is_single_page")
    private boolean onePage;

    @Column("is_allowed_correction")
    private boolean allowedImmediateCorrection;

    @Column("is_allowed_practice_mode")
    private boolean allowedPracticemode;

    public String getQuizName() {
        return quizName;
    }

    public Quiz(int id, String quizName, int authorId, int timesDone, Timestamp dateCreated, boolean randomized, boolean onePage, boolean allowedImmediateCorrection, boolean allowedPracticemode) {
        this.id = id;
        this.quizName = quizName;
        this.authorId = authorId;
        this.timesDone = timesDone;
        this.dateCreated = dateCreated;
        this.randomized = randomized;
        this.onePage = onePage;
        this.allowedImmediateCorrection = allowedImmediateCorrection;
        this.allowedPracticemode = allowedPracticemode;
    }

    public Quiz(String quizName, int authorId, Timestamp dateCreated, boolean randomized, boolean onePage, boolean allowedImmediateCorrection, boolean allowedPracticemode) {
        this.quizName = quizName;
        this.authorId = authorId;
        this.dateCreated = dateCreated;
        this.randomized = randomized;
        this.onePage = onePage;
        this.allowedImmediateCorrection = allowedImmediateCorrection;
        this.allowedPracticemode = allowedPracticemode;
    }

    public boolean isRandomized() {
        return randomized;
    }

    public boolean isOnePage() {
        return onePage;
    }

    public boolean isAllowedImmediateCorrection() {
        return allowedImmediateCorrection;
    }

    public boolean isAllowedPracticemode() {
        return allowedPracticemode;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public int getAuthorId() {
        return authorId;
    }

    public int getTimesDone() {
        return timesDone;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "id=" + id +
                ", quizName='" + quizName + '\'' +
                ", authorId=" + authorId +
                ", timesDone=" + timesDone +
                ", dateCreated=" + dateCreated +
                ", randomized=" + randomized +
                ", onePage=" + onePage +
                ", allowedImmediateCorrection=" + allowedImmediateCorrection +
                ", allowedPracticemode=" + allowedPracticemode +
                '}';
    }
}
