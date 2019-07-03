package servlets.quiz;

import dao.QuizDao;
import dao.QuizResultDao;
import datatypes.Quiz;
import datatypes.QuizResult;
import datatypes.User;
import datatypes.question.Question;
import enums.DaoType;
import manager.DaoManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/CompleteQuizServlet")
public class CompleteQuizServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        DaoManager manager = (DaoManager) request.getServletContext().getAttribute("manager");
        QuizDao quizDao = manager.getDao(DaoType.Quiz);
        QuizResultDao quizResultDao = manager.getDao(DaoType.QuizResult);
        Map<String, String[]> params = request.getParameterMap();
        Map<Integer, String> questionAnswers = new HashMap<>();
        Map<Question, Boolean> quizResults = new HashMap<>();
        User user = (User) request.getSession().getAttribute("user");
        params.keySet().stream().filter(question->question.startsWith("questionN")).forEach(question->questionAnswers.put(Integer.valueOf(question.substring(question.lastIndexOf("N")+1)), request.getParameter(question)));
        Quiz quiz = quizDao.findById(Integer.valueOf(request.getParameter("quizId")));

        for (Question question : quiz.getQuestionAnswerMap().keySet()) {
            String answer = quiz.getQuestionAnswerMap().get(question).getAnswer().split(",")[0];
            if (questionAnswers.get(question.getId())!= null && questionAnswers.get(question.getId()).equalsIgnoreCase(answer)) {
                quizResults.put(question, true);
            }else {
                quizResults.put(question, false);
            }
        }
        String time = request.getParameter("completeTime");
        int seconds = getSeconds(time);
        int result = (int) quizResults.values().stream().filter(s -> s).count();
        QuizResult quizResult = new QuizResult(quiz.getId(), user.getId(), result, seconds );
        quizResultDao.insert(quizResult);

        request.setAttribute("results", quizResults);
        request.setAttribute("userAnswers", questionAnswers);
        request.setAttribute("score", result);
        request.setAttribute("timespent", time);

        request.getRequestDispatcher("quiz-result?quizId="+quiz.getId()).forward(request, response);
    }

    private int getSeconds(String time) {
        int hours = Integer.parseInt(time.substring(0,2));
        int minutes = Integer.parseInt(time.substring(3,5));
        int seconds = Integer.parseInt(time.substring(6));
        return 3600*hours + 60 * minutes + seconds;
    }
}
