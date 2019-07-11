package servlets.quiz;

import dao.QuizDao;
import datatypes.quiz.Quiz;
import datatypes.quiz.QuizResult;
import datatypes.quiz.question.Question;
import datatypes.user.User;
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

@WebServlet("/ClearQuizHistoryServlet")
public class ClearQuizHistoryServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        DaoManager manager = (DaoManager) request.getServletContext().getAttribute("manager");
        int quizId = Integer.parseInt(request.getParameter("clearedQuizId"));
        Integer userId = ((User) request.getSession().getAttribute("user")).getId();
        manager.deleteHistoryForQuiz(userId,quizId);
        request.setAttribute("warn","Quiz history is being cleared...");
        request.getRequestDispatcher("quiz").forward(request, response);
    }

}
