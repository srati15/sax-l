package datatypes;

import datatypes.answer.Answer;
import datatypes.question.Question;

import java.sql.Timestamp;
import java.util.Map;

public class Quiz {
    private int id;
    private int authorId;
    private Map<Question, Answer> questionAnswerMap;
    private int timesDone;
    private Timestamp dateCreated;
    private boolean randomized;
    private boolean onePage;
    private boolean allowedImmediateCorrection;
    private boolean allowedPracticemode;

    public Quiz(int id, int authorId, Map<Question, Answer> questionAnswerMap, int timesDone, Timestamp dateCreated, boolean randomized, boolean onePage, boolean allowedImmediateCorrection, boolean allowedPracticemode) {
        this.id = id;
        this.authorId = authorId;
        this.questionAnswerMap = questionAnswerMap;
        this.timesDone = timesDone;
        this.dateCreated = dateCreated;
        this.randomized = randomized;
        this.onePage = onePage;
        this.allowedImmediateCorrection = allowedImmediateCorrection;
        this.allowedPracticemode = allowedPracticemode;
    }

    public Quiz(int authorId, Map<Question, Answer> questionAnswerMap, int timesDone, Timestamp dateCreated, boolean randomized, boolean onePage, boolean allowedImmediateCorrection, boolean allowedPracticemode) {
        this.authorId = authorId;
        this.questionAnswerMap = questionAnswerMap;
        this.timesDone = timesDone;
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

    public int getId() {
        return id;
    }

    public int getAuthorId() {
        return authorId;
    }

    public Map<Question, Answer> getQuestionAnswerMap() {
        return questionAnswerMap;
    }

    public int getTimesDone() {
        return timesDone;
    }
}
