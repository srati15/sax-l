package servlets.user;

import dao.ActivityDao;
import dao.UserDao;
import datatypes.server.Activity;
import datatypes.user.User;
import enums.DaoType;
import manager.DaoManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import security.Cracker;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(RegisterServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        UserDao userRepository = ((DaoManager) request.getServletContext().getAttribute("manager")).getDao(DaoType.User);
        String userName = request.getParameter("username");
        String passwordHash =Cracker.code(request.getParameter("password"));
        String confirmPasswordHash = Cracker.code(request.getParameter("confirmpassword"));
        String firstName = request.getParameter("firstname");
        String lastName = request.getParameter("lastname");
        String mail = request.getParameter("mail");
        if (!passwordHash.equals(confirmPasswordHash)) {
            request.setAttribute("error", "Passwords don't match");
            logger.error("Passwords don't match");
            request.getRequestDispatcher("register").forward(request, response);
            return;
        }
        if (userRepository.findByUserName(userName) != null) {
            request.setAttribute("error", "Username is already taken");
            logger.error("Username is already taken, {}", userName);
            request.getRequestDispatcher("register").forward(request, response);
            return;
        }

        User user = new User(userName, passwordHash, firstName, lastName, mail);
        DaoManager manager = (DaoManager) getServletContext().getAttribute("manager");
        manager.insert(user);

        request.getSession().setAttribute("user", user);
        Map<Integer, User> userMap = (Map<Integer, User>) request.getServletContext().getAttribute("onlineUsers");
        userMap.put(user.getId(), user);

        request.getSession().setAttribute("user", userRepository.findByUserName(userName));
        request.getRequestDispatcher("").forward(request, response);
    }
}
