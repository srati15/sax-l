package servlets.user;

import dao.UserDao;
import datatypes.User;
import enums.DaoType;
import manager.DaoManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/DeleteUserServlet")
public class DeleteUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DaoManager manager = (DaoManager) request.getServletContext().getAttribute("manager");
        UserDao userDao = manager.getDao(DaoType.User);
        User user = (User) request.getSession().getAttribute("user");
        int deleteUserId = Integer.parseInt(request.getParameter("deleteUserId"));
        User deleteUser = userDao.findById(deleteUserId);

        if (user.getId() == deleteUserId) {
            request.setAttribute("error", "You can't delete yourself");
            request.getRequestDispatcher("users-list").forward(request, response);
            return;
        }
        if (user.getId() != deleteUserId)  manager.delete(deleteUser);
        request.getRequestDispatcher("users-list").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

    }
}
