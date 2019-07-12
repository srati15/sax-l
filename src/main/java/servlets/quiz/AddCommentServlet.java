package servlets.quiz;

import dao.QuizDao;
import datatypes.quiz.Comment;
import datatypes.quiz.Quiz;
import datatypes.user.User;
import enums.DaoType;
import manager.DaoManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/AddCommentServlet")
public class AddCommentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        DaoManager manager = (DaoManager) request.getServletContext().getAttribute("manager");
        User user = (User) request.getSession().getAttribute("user");
        int quizId= Integer.parseInt(request.getParameter("quizId"));
        String commentText = request.getParameter("commentText");
        manager.insert(new Comment(quizId, user.getId(), commentText, LocalDateTime.now()));
        request.setAttribute("info", "Comment has been added");
        request.getRequestDispatcher("quiz-details?quizId="+quizId).forward(request, response);
    }
}
