package servlets.user;

import dao.UserDao;
import datatypes.user.User;
import enums.DaoType;
import mail.PasswordRecovery;
import manager.DaoManager;
import security.Cracker;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;

@WebServlet(value = "/ForgotPasswordServlet", asyncSupported = true)
public class ForgotPasswordServlet extends HttpServlet {
    private static final int PASSWORD_LENGTH = 12;
    private final Random random = new Random();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        UserDao userRepository = ((DaoManager) getServletContext().getAttribute("manager")).getDao(DaoType.User);
        String userName = request.getParameter("username");
        User user = userRepository.findByUserName(userName);
        if (user == null) {
            request.getSession().setAttribute("error", "Wrong username");
            request.getRequestDispatcher("forgot").forward(request, response);
            return;
        }
        StringBuilder passwordBuilder = new StringBuilder();
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            passwordBuilder.append((char) ('a' + random.nextInt(26)));
        }
        String passwordHash = Cracker.code(passwordBuilder.toString());
        user.setPassword(passwordHash);
        userRepository.update(user);
        if (PasswordRecovery.send(user, passwordBuilder.toString())) {
            request.getSession().setAttribute("info", "Password recovery mail sent to " + user.getMail());
            request.getRequestDispatcher("").forward(request, response);
        } else {
            request.getSession().setAttribute("error", "Your mail is invalid. you have to create a new Account");
            request.getRequestDispatcher("register").forward(request, response);
        }
    }
}
