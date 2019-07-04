package database.mapper;

import datatypes.QuizResult;

import java.sql.ResultSet;
import java.sql.SQLException;

public class QuizResultMapper implements DBRowMapper<QuizResult> {
    public static final String RESULT_ID = "result_id";
    public static final String USER_ID = "user_id";
    public static final String QUIZ_ID = "quiz_id";
    public static final String SCORE = "score";
    public static final String TIME_SPENT = "time_spent";
    public static final String TABLE_NAME = "results";

    @Override
    public QuizResult mapRow(ResultSet rs) {
        try {
            int resultId = rs.getInt(RESULT_ID);
            int userId = rs.getInt(USER_ID);
            int quizId = rs.getInt(QUIZ_ID);
            int score = rs.getInt(SCORE);
            int timeSpent = rs.getInt(TIME_SPENT);

            QuizResult quizResult = new QuizResult(quizId,userId,score,timeSpent);
            quizResult.setId(resultId);
            return quizResult;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
