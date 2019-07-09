package servlets.quiz;

import datatypes.quiz.Quiz;
import datatypes.quiz.answer.Answer;
import datatypes.quiz.question.Question;
import datatypes.user.User;
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
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/QuizCreationServlet")
public class QuizCreationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        DaoManager manager = (DaoManager) request.getServletContext().getAttribute("manager");
        User user = (User) request.getSession().getAttribute("user");
        //parameter values
        boolean autoCorrection = request.getParameter("correction").equals("yes");
        boolean practiceMode = request.getParameter("practice").equals("yes");
        boolean singlePage = request.getParameter("singlepage").equals("yes");
        boolean randomized = request.getParameter("randomized").equals("yes");
        String quizName = request.getParameter("quizname");
        String quizImageURL = request.getParameter("quizImageUrl");
        String description = request.getParameter("quizDescription");
        Quiz quiz = new Quiz(quizName, user.getId(), (LocalDateTime.now()), randomized, singlePage, autoCorrection, practiceMode, quizImageURL, description);
        JSONArray questionsArray = new JSONArray(request.getParameter("questions"));
        Map<Question, Answer> questionAnswerMap = new HashMap<>();
        QuestionAnswerJsonDispatcher dispatcher = new QuestionAnswerJsonDispatcher();
        for (int i = 0; i < questionsArray.length(); i++) {
            JSONObject questionJson = questionsArray.getJSONObject(i);
            Question question = dispatcher.dispatchQuestion(questionJson);
            questionAnswerMap.put(question, new Answer(questionJson.getString("answer")));
        }
        quiz.setQuestionAnswerMap(questionAnswerMap);
        manager.insert(quiz);


        request.getRequestDispatcher("quiz").forward(request, response);
    }

}
