package servlets.quiz;

import dao.QuizDao;
import dao.QuizResultDao;
import dao.UserDao;
import enums.DaoType;
import manager.DaoManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/quiz")
public class QuizServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(QuizServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        DaoManager manager = (DaoManager) request.getServletContext().getAttribute("manager");
        UserDao userDao = manager.getDao(DaoType.User);
        QuizDao quizDao = manager.getDao(DaoType.Quiz);
        request.setAttribute("userDao", userDao);
        request.setAttribute("quizDao", quizDao);
        request.getRequestDispatcher("/quiz.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
