package servlets.quiz;

import dao.QuizDao;
import datatypes.Quiz;
import datatypes.User;
import datatypes.answer.Answer;
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
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/QuizCreationServlet")
public class QuizCreationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        DaoManager manager = (DaoManager) request.getServletContext().getAttribute("manager");
        QuizDao quizDao= manager.getDao(DaoType.Quiz);
        User user = (User) request.getSession().getAttribute("user");
        //parameter values
        boolean autoCorrection = request.getParameter("correction").equals("yes");
        boolean practiceMode = request.getParameter("practice").equals("yes");
        boolean singlePage = request.getParameter("singlepage").equals("yes");
        boolean randomized = request.getParameter("randomized").equals("yes");
        String quizName = request.getParameter("quizname");
        Quiz quiz = new Quiz(quizName, user.getId(), Timestamp.valueOf(LocalDateTime.now()), randomized, singlePage, autoCorrection, practiceMode);
        quizDao.insert(quiz);
        JSONArray questionsArray = new JSONArray(request.getParameter("questions"));

        Map<Question, Answer> questionAnswerMap = new HashMap<>();
        for (int i = 0; i < questionsArray.length(); i++) {
            JSONObject questionJson = questionsArray.getJSONObject(i);
            Question question = QuestionAnswerJsonDispatcher.dispatchQuestion(questionJson, quiz.getId());
            questionAnswerMap.put(question, new Answer(questionJson.getString("answer"), 0));
        }
        quizDao.getQuestionDao().insertAll(questionAnswerMap.keySet());
        for (Question question: questionAnswerMap.keySet()) {
            questionAnswerMap.get(question).setQuestionId(question.getId());
        }
        quizDao.getAnswerDao().insertAll(questionAnswerMap.values());
        request.getRequestDispatcher("quiz").forward(request, response);
    }

}
