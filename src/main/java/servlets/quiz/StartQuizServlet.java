package servlets.quiz;

import dao.QuizDao;
import datatypes.Quiz;
import datatypes.question.Question;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet("/start-quiz")
public class StartQuizServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Map<String, String[]> params = request.getParameterMap();
        Map<Integer, String> questionAnswers = new HashMap<>();
        params.keySet().stream().filter(question->question.startsWith("questionN")).forEach(question->questionAnswers.put(Integer.valueOf(question.substring(question.lastIndexOf("N")+1)), request.getParameter(question)));

        Quiz quiz = QuizDao.getInstance().findById(Integer.valueOf(request.getParameter("quizId")));
        List<Question> questionList = quiz.getQuestionAnswerMap().keySet().stream().collect(Collectors.toList());
        if (quiz.isRandomized()) {
            Collections.shuffle(questionList);
        }
        request.setAttribute("currentQuizQuestions", questionList);
        request.setAttribute("questionAnswerMap", quiz.getQuestionAnswerMap());
        request.getRequestDispatcher("/start-quiz.jsp").forward(request, response);
    }

}
