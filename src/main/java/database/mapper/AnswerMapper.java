package database.mapper;

import datatypes.answer.Answer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AnswerMapper implements DBRowMapper<Answer> {
    private static final String ANSWER_ID = "id";
    public static final String QUESTION_ID = "question_id";
    public static final String ANSWER_TEXT = "answer_string";
    public static final String TABLE_NAME = "answers";

    @Override
    public Answer mapRow(ResultSet rs) {
        try {
            int answerId = rs.getInt(ANSWER_ID);
            int questionId = rs.getInt(QUESTION_ID);
            String answerText = rs.getString(ANSWER_TEXT);
            Answer answer = new Answer(answerText);
            answer.setId(answerId);
            answer.setQuestionId(questionId);
            return answer;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
