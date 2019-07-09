package datatypes.quiz;

import datatypes.Domain;
import datatypes.quiz.answer.Answer;
import datatypes.quiz.question.Question;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Quiz extends Domain<Integer> {
    private String quizName;
    private int authorId;
    private int timesDone;
    private LocalDateTime dateCreated;
    private boolean randomized;
    private boolean onePage;
    private boolean allowedImmediateCorrection;
    private boolean allowedPracticemode;
    private String quizImageURL;
    private Map<Question, Answer> questionAnswerMap = new HashMap<>();
    public String getQuizName() {
        return quizName;
    }
    public Quiz(){

    }
    public Quiz(int id, String quizName, int authorId, int timesDone, LocalDateTime dateCreated, boolean randomized, boolean onePage, boolean allowedImmediateCorrection, boolean allowedPracticemode, String quizImageURL) {
        this.id = id;
        this.quizName = quizName;
        this.authorId = authorId;
        this.timesDone = timesDone;
        this.dateCreated = dateCreated;
        this.randomized = randomized;
        this.onePage = onePage;
        this.allowedImmediateCorrection = allowedImmediateCorrection;
        this.allowedPracticemode = allowedPracticemode;
        this.quizImageURL = quizImageURL;
    }

    public Quiz(String quizName, int authorId, LocalDateTime dateCreated, boolean randomized, boolean onePage, boolean allowedImmediateCorrection, boolean allowedPracticemode, String quizImageURL) {
        this.quizName = quizName;
        this.authorId = authorId;
        this.dateCreated = dateCreated;
        this.randomized = randomized;
        this.onePage = onePage;
        this.allowedImmediateCorrection = allowedImmediateCorrection;
        this.allowedPracticemode = allowedPracticemode;
        this.quizImageURL = quizImageURL;
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

    public LocalDateTime getDateCreated() {
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

    public void setTimesDone(int timesDone) {
        this.timesDone = timesDone;
    }

    public Map<Question, Answer> getQuestionAnswerMap() {
        return questionAnswerMap;
    }

    public String getQuizImageURL() {
        return quizImageURL;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quiz quiz = (Quiz) o;
        return authorId == quiz.authorId &&
                timesDone == quiz.timesDone &&
                randomized == quiz.randomized &&
                onePage == quiz.onePage &&
                allowedImmediateCorrection == quiz.allowedImmediateCorrection &&
                allowedPracticemode == quiz.allowedPracticemode &&
                Objects.equals(quizName, quiz.quizName) &&
                Objects.equals(dateCreated, quiz.dateCreated) &&
                Objects.equals(questionAnswerMap, quiz.questionAnswerMap);
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "quizName='" + quizName + '\'' +
                ", authorId=" + authorId +
                ", timesDone=" + timesDone +
                ", dateCreated=" + dateCreated +
                ", randomized=" + randomized +
                ", onePage=" + onePage +
                ", allowedImmediateCorrection=" + allowedImmediateCorrection +
                ", allowedPracticemode=" + allowedPracticemode +
                ", quizImageURL='" + quizImageURL + '\'' +
                ", questionAnswerMap=" + questionAnswerMap +
                ", id=" + id +
                '}';
    }
}
