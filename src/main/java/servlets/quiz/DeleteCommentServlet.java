package servlets.quiz;

import dao.CommentDao;
import datatypes.quiz.Comment;
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

@WebServlet("/DeleteCommentServlet")
public class DeleteCommentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        DaoManager manager = (DaoManager) request.getServletContext().getAttribute("manager");
        CommentDao commentDao = manager.getDao(DaoType.Comment);
        int commentId= Integer.parseInt(request.getParameter("deleteCommentId"));
        int quizId= Integer.parseInt(request.getParameter("quizId"));
        manager.delete(commentDao.findById(commentId));
        request.setAttribute("info", "Comment has been deleted");
        request.getRequestDispatcher("quiz-details?quizId="+quizId).forward(request, response);
    }
}
