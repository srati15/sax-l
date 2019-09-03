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
import java.util.Map;

@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        User user = (User) request.getSession().getAttribute("user");
        request.getSession().removeAttribute("user");
        Map<Integer, User> userMap = (Map<Integer, User>) request.getServletContext().getAttribute("onlineUsers");
        userMap.remove(user.getId());
        request.getSession().invalidate();
        request.getSession().setAttribute("info", "Bye, "+user.getUserName());
        response.sendRedirect("/");
    }
}

