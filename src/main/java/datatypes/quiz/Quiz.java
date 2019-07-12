package datatypes.quiz;

import datatypes.Domain;
import datatypes.quiz.answer.Answer;
import datatypes.quiz.question.Question;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Quiz extends Domain<Integer> {
    private String quizName;
    private int authorId;
    private LocalDateTime dateCreated;
    private boolean randomized;
    private boolean onePage;
    private boolean allowedImmediateCorrection;
    private boolean allowedPracticemode;
    private String quizImageURL;
    private String description;
    private AtomicInteger timesDone = new AtomicInteger(0);
    private Map<Question, Answer> questionAnswerMap = new HashMap<>();
    private List<Comment> comments = new ArrayList<>();
    public String getQuizName() {
        return quizName;
    }

    public Quiz(int id, String quizName, int authorId, LocalDateTime dateCreated, boolean randomized, boolean onePage, boolean allowedImmediateCorrection, boolean allowedPracticemode, String quizImageURL, String description) {
        this.id = id;
        this.quizName = quizName;
        this.authorId = authorId;
        this.dateCreated = dateCreated;
        this.randomized = randomized;
        this.onePage = onePage;
        this.allowedImmediateCorrection = allowedImmediateCorrection;
        this.allowedPracticemode = allowedPracticemode;
        this.quizImageURL = quizImageURL;
        this.description = description;
    }

    public Quiz(String quizName, int authorId, LocalDateTime dateCreated, boolean randomized, boolean onePage, boolean allowedImmediateCorrection, boolean allowedPracticemode, String quizImageURL, String description) {
        this.quizName = quizName;
        this.authorId = authorId;
        this.dateCreated = dateCreated;
        this.randomized = randomized;
        this.onePage = onePage;
        this.allowedImmediateCorrection = allowedImmediateCorrection;
        this.allowedPracticemode = allowedPracticemode;
        this.quizImageURL = quizImageURL;
        this.description = description;
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
        return timesDone.get();
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
    public void setQuestionAnswerMap(Map<Question, Answer> questionAnswerMap) {
        this.questionAnswerMap = questionAnswerMap;
    }

    public String getDescription() {
        return description;
    }

    public void setTimesDone(int timesDone) {
        this.timesDone.set(timesDone);
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
                timesDone.equals(quiz.timesDone) &&
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
