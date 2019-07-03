package database.mapper;

import datatypes.question.*;
import enums.QuestionType;

import java.sql.ResultSet;
import java.sql.SQLException;

public class QuestionMapper implements DBRowMapper<Question> {
    public static final String QUIZ_ID = "quiz_id";
    public static final String QUESTION_ID = "id";
    public static final String QUESTION_TYPE = "question_type_id";
    public static final String QUESTION_TEXT = "question_text";
    public static final String TABLE_NAME = "question";


    @Override
    public Question mapRow(ResultSet rs) {
        try {
            int quizId = rs.getInt(QUIZ_ID);
            int questionId = rs.getInt(QUESTION_ID);
            int questionTypeId = rs.getInt(QUESTION_TYPE);
            String questionText = rs.getString(QUESTION_TEXT);
            QuestionType questionType = QuestionType.getById(questionTypeId);
            Question question;
            if (questionType == QuestionType.PictureResponse) {
                question = new PictureResponseQuestion(questionText);
            }else if (questionType == QuestionType.FillInTheBlank) {
                question = new FillBlankQuestion(questionText);
            }else if (questionType == QuestionType.QuestionResponse) {
                question = new QuestionResponse(questionText);
            }else question = new MultipleChoiceQuestion(questionText);
            question.setQuizId(quizId);
            question.setId(questionId);
            return question;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
