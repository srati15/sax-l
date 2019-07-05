package servlets.quiz;

import dao.QuizDao;
import datatypes.Quiz;
import enums.DaoType;
import manager.DaoManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/DeleteQuizServlet")
public class DeleteQuizServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        DaoManager manager = (DaoManager) request.getServletContext().getAttribute("manager");
        int quizId= Integer.parseInt(request.getParameter("deleteQuizId"));
        QuizDao quizDao = manager.getDao(DaoType.Quiz);
        Quiz quiz = quizDao.findById(quizId);
        manager.delete(quiz);
        request.getRequestDispatcher("quiz").forward(request, response);
    }

    private int getSeconds(String time) {
        int hours = Integer.parseInt(time.substring(0,2));
        int minutes = Integer.parseInt(time.substring(3,5));
        int seconds = Integer.parseInt(time.substring(6));
        return 3600*hours + 60 * minutes + seconds;
    }
}
