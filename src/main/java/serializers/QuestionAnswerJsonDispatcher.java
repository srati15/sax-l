package serializers;

import datatypes.answer.*;
import datatypes.question.*;
import org.json.JSONObject;

public class QuestionAnswerJsonDispatcher {
    public static Question dispatchQuestion(JSONObject questionJson) {
        Question question = null;
        String type = questionJson.getString("type");
        String questionText = questionJson.getString("question");
        if (type.equals("questionResponse")) {
            question = new QuestionResponse(questionText);
        }else if (type.equals("pictureResponse")){
            question = new PictureResponseQuestion(questionText);
        }else if (type.equals("fillInTheBlanks")) {
            question = new FillBlankQuestion(questionText);
        }else if (type.equals("multipleChoice")){
            question = new MultipleChoiceQuestion(questionText);
        }
        return question;
    }
    public static Answer dispatchAnswer(JSONObject questionJson) {
        Answer answer = null;
        String type = questionJson.getString("type");
        String answerText = questionJson.getString("answer");
        if (type.equals("questionResponse")) {
            answer = new QuestionResponseAnswer(answerText);
        }else if (type.equals("pictureResponse")){
            answer = new PictureResponseAnswer(answerText);
        }else if (type.equals("fillInTheBlanks")) {
            answer = new FillBlankAnswer(answerText);
        }else if (type.equals("multipleChoice")){
            answer = new MultipleChoiceAnswer(answerText);
        }
        return answer;
    }
}
