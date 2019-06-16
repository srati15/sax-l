package servlets.quiz;

import dao.QuizDao;
import datatypes.answer.Answer;
import datatypes.answer.QuestionResponseAnswer;
import datatypes.question.Question;
import enums.DaoType;
import manager.DaoManager;
import org.json.JSONArray;
import org.json.JSONObject;
import serializers.QuestionAnswerJsonDispatcher;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/QuizCreationServlet")
public class QuizCreationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        JSONArray questionsArray = new JSONArray(request.getParameter("questions"));
        System.out.println(questionsArray.length());
        Map<Question, Answer> questionAnswerMap = new HashMap<>();
        for (int i = 0; i < questionsArray.length(); i++) {
            JSONObject questionJson = questionsArray.getJSONObject(i);
            Question question = QuestionAnswerJsonDispatcher.dispatchQuestion(questionJson);
            Answer answer = QuestionAnswerJsonDispatcher.dispatchAnswer(questionJson);
            questionAnswerMap.put(question, answer);
        }
        DaoManager manager = (DaoManager) request.getServletContext().getAttribute("manager");
        QuizDao quizDao= manager.getDao(DaoType.Quiz);
        //todo
        request.getRequestDispatcher("quiz").forward(request, response);
    }
    private Answer dispatchAnswer(JSONObject questionJson) {

        return new QuestionResponseAnswer(questionJson.getString("answer"));
    }
}
