package serializers;

import datatypes.question.*;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class QuestionAnswerJsonDispatcher {
    private interface Command{
        Question dispatch(JSONObject questionJson);
    }
    private Command questionResponse = questionJson -> new QuestionResponse(questionJson.getString("question"));
    private Command pictureResponse = questionJson -> new PictureResponseQuestion(questionJson.getString("question"));
    private Command fillBlank = questionJson -> new FillBlankQuestion(questionJson.getString("question"));
    private Command multipleChoice = questionJson -> new MultipleChoiceQuestion(questionJson.getString("question"));
    private Map<String, Command> commandMap;
    public QuestionAnswerJsonDispatcher(){
        initCommandMap();
    }

    private void initCommandMap() {
        commandMap = new HashMap<>();
        commandMap.put("questionResponse", questionResponse);
        commandMap.put("pictureResponse", pictureResponse);
        commandMap.put("fillBlank", fillBlank);
        commandMap.put("multipleChoice", multipleChoice);
    }

    public Question dispatchQuestion(JSONObject questionJson, int quizId) {
        String type = questionJson.getString("type");
        Question question = commandMap.get(type).dispatch(questionJson);
        assert question != null;
        question.setQuizId(quizId);
        return question;
    }
}
