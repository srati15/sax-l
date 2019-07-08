package servlets.user;

import dao.ActivityDao;
import datatypes.server.Activity;
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

@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        User user = (User) request.getSession().getAttribute("user");
        request.getSession().removeAttribute("user");
        DaoManager manager = (DaoManager) getServletContext().getAttribute("manager");
        ActivityDao activityDao = manager.getDao(DaoType.Activity);
        activityDao.insert(new Activity(user.getId(), "logged out", LocalDateTime.now()));
        request.getRequestDispatcher("").forward(request, response);
    }
}

