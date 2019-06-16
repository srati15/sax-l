package database.mapper;

import datatypes.question.*;
import enums.QuestionType;

import java.sql.ResultSet;
import java.sql.SQLException;

public class QuestionMapper implements DBRowMapper<Question> {
    public static final String QUIZ_ID = "quiz_id";
    public static final String QUESTION_ID = "question_id";
    public static final String QUESTION_TYPE = "question_type_id";
    public static final String QUESTION_TEXT = "question_text";
    public static final String TABLE_NAME = "question";

    @Override
    public Question mapRow(ResultSet rs) {
        String txt = null;
        try {
            int quizId = rs.getInt(QUIZ_ID);
            int questionId = rs.getInt(QUESTION_ID);
            int questionTypeId = rs.getInt(QUESTION_TYPE);
            String questionText = rs.getString(QUESTION_TEXT);
            QuestionType questionType = QuestionType.getById(questionTypeId);
            if (questionType == QuestionType.PictureResponse) {
                return new PictureResponseQuestion(questionId, quizId, questionText);
            }else if (questionType == QuestionType.FillInTheBlank) {
                return new FillBlankQuestion(questionId, quizId, questionText);
            }else if (questionType == QuestionType.QuestionResponse) {
                return new QuestionResponse(questionId, quizId, questionText);
            }else if (questionType == QuestionType.MultipleChoise) {
                return new MultipleChoiceQuestion(questionId, quizId, questionText);
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return null;
    }
}
