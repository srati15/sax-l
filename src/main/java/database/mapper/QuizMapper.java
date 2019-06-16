package database.mapper;

import datatypes.Quiz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class QuizMapper implements DBRowMapper<Quiz> {
    public static final String QUIZ_ID = "quiz_id";
    public static final String QUIZ_NAME = "quiz_name";
    public static final String QUIZ_AUTHOR = "quiz_author";
    public static final String DATE_CREATED = "date_created";
    public static final String IS_RANDOMIZED = "randomized";
    public static final String IS_PRACTICE = "is_allowed_practice_mode";
    public static final String IS_CORRECTION = "is_allowed_correction";
    public static final String IS_SINGLEPAGE = "is_single_page";
    public static final String TIMES_DONE = "times_done";
    public static final String TABLE_NAME = "quiz";

    @Override
    public Quiz mapRow(ResultSet rs) {
        String txt = null;
        try {
            int quizId = rs.getInt(QUIZ_ID);
            String quizName = rs.getString(QUIZ_NAME);
            int authorId = rs.getInt(QUIZ_AUTHOR);
            Timestamp dateCreated = rs.getTimestamp(DATE_CREATED);
            boolean isRandomized = rs.getBoolean(IS_RANDOMIZED);
            boolean isPractice = rs.getBoolean(IS_PRACTICE);
            boolean isCorrection = rs.getBoolean(IS_CORRECTION);
            boolean isSinglePage = rs.getBoolean(IS_SINGLEPAGE);
            int timesDone = rs.getInt(TIMES_DONE);
            return new Quiz(quizId, quizName, authorId, timesDone, dateCreated, isRandomized, isSinglePage, isCorrection, isPractice);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return null;
    }
}