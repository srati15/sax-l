package servlets.user;

import dao.UserDao;
import datatypes.User;
import manager.DaoManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        UserDao userRepository = ((DaoManager) request.getServletContext().getAttribute("manager")).getUserDao();
        String userName = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmpassword");
        String firstName = request.getParameter("firstname");
        String lastName = request.getParameter("lastname");
        String mail = request.getParameter("mail");
        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "Passwords don't match");
            System.out.println("Passwords don't match");
            response.sendRedirect("/register");
            return;
        }
        if (userRepository.findByUserName(userName) != null) {
            request.setAttribute("error", "Username is already taken");
            System.out.println("Username is already taken");
            request.getRequestDispatcher("register").forward(request, response);
            return;
        }

        if (password.length() < 4) {
            System.out.println("Password must be of minimum 6 character");
            request.setAttribute("error", "Password must be of minimum 6 character");
            request.getRequestDispatcher("register").forward(request, response);
            return;
        }
        User user = new User(userName, password, firstName, lastName, mail);
        userRepository.insert(user);
        request.getSession().setAttribute("user", userRepository.findByUserName(userName));
        request.getRequestDispatcher("/").forward(request, response);
    }
}
