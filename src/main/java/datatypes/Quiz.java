package datatypes;

import datatypes.answer.Answer;
import datatypes.question.Question;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class Quiz extends Domain<Integer> {
    private String quizName;
    private int authorId;
    private int timesDone;
    private Timestamp dateCreated;
    private boolean randomized;
    private boolean onePage;
    private boolean allowedImmediateCorrection;
    private boolean allowedPracticemode;

    private Map<Question, Answer> questionAnswerMap = new HashMap<>();
    public String getQuizName() {
        return quizName;
    }
    public Quiz(){

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

    public void setQuestionAnswerMap(Map<Question, Answer> questionAnswerMap) {
        this.questionAnswerMap = questionAnswerMap;
    }

    public Map<Question, Answer> getQuestionAnswerMap() {
        return questionAnswerMap;
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
