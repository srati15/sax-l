package servlets.user;

import dao.UserDao;
import datatypes.User;
import manager.DaoManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        UserDao userRepository = ((DaoManager) request.getServletContext().getAttribute("manager")).getUserDao();
        String userName = request.getParameter("username");
        String password = request.getParameter("password");
        User user = userRepository.findByUserName(userName);
        if (user == null) {
            request.setAttribute("error", "Wrong login credentials");
            System.out.println("Wrong username");
            request.getRequestDispatcher("/register").forward(request, response);
            return;
        }
        if (!user.getPassword().equals(password)) {
            request.setAttribute("error", "Wrong login credentials");
            System.out.println("Wrong login credentials");
            request.getRequestDispatcher("/register").forward(request, response);
            return;
        }
        request.getSession().setAttribute("user", user);
        RequestDispatcher dispatcher = request.getRequestDispatcher("");
        dispatcher.forward(request, response);
    }
}
