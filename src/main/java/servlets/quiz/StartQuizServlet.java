package servlets.quiz;

import dao.QuizDao;
import datatypes.quiz.Quiz;
import enums.DaoType;
import manager.DaoManager;
import servlets.challenge.ChallengeSenderServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/start-quiz")
public class StartQuizServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        DaoManager manager = (DaoManager) request.getServletContext().getAttribute("manager");
        Map<String, String[]> params = request.getParameterMap();
        Map<Integer, String> questionAnswers = new HashMap<>();
        params.keySet().stream().filter(question->question.startsWith("questionN")).forEach(question->questionAnswers.put(Integer.valueOf(question.substring(question.lastIndexOf("N")+1)), request.getParameter(question)));
        QuizDao quizDao = manager.getDao(DaoType.Quiz);
        Quiz quiz = quizDao.findById(Integer.valueOf(request.getParameter("quizId")));
        ChallengeSenderServlet.startQuiz(request, response, quiz);
    }

}
