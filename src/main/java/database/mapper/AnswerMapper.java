package database.mapper;

import datatypes.answer.Answer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AnswerMapper implements DBRowMapper<Answer> {
    public static final String ANSWER_ID = "answer_id";
    public static final String QUESTION_ID = "question_id";
    public static final String ANSWER_TEXT = "answer_string";
    public static final String IS_CORRECT = "correctness";
    public static final String TABLE_NAME = "answers";

    @Override
    public Answer mapRow(ResultSet rs) {
        try {
            int answerId = rs.getInt(ANSWER_ID);
            int questionId = rs.getInt(QUESTION_ID);
            String answerText = rs.getString(ANSWER_TEXT);
            boolean isCorrect = rs.getBoolean(IS_CORRECT);

            return new Answer(answerText, answerId, questionId, isCorrect);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return null;
    }
}
