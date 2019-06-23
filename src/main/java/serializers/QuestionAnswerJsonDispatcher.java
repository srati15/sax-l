package serializers;

import datatypes.question.*;
import org.json.JSONObject;

public class QuestionAnswerJsonDispatcher {
    public static Question dispatchQuestion(JSONObject questionJson, int quizId) {
        Question question = null;
        String type = questionJson.getString("type");
        String questionText = questionJson.getString("question");
        if (type.equals("questionResponse")) {
            question = new QuestionResponse(questionText);
        }else if (type.equals("pictureResponse")){
            question = new PictureResponseQuestion(questionText);
        }else if (type.equals("fillBlank")) {
            question = new FillBlankQuestion(questionText);
        }else if (type.equals("multipleChoice")){
            question = new MultipleChoiceQuestion(questionText);
        }
        assert question != null;
        question.setQuizId(quizId);
        return question;
    }
}
