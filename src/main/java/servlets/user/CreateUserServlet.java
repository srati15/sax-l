package servlets.user;

import dao.UserDao;
import datatypes.promise.Promise;
import datatypes.user.User;
import enums.DaoType;
import enums.FormFields;
import enums.UserType;
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

@WebServlet("/CreateUserServlet")
public class CreateUserServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(CreateUserServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        DaoManager manager = (DaoManager) getServletContext().getAttribute("manager");
        UserDao userRepository = ((DaoManager) getServletContext().getAttribute("manager")).getDao(DaoType.User);
        String userName = request.getParameter(FormFields.username.getValue());
        String password = request.getParameter(FormFields.password.getValue());
        String confirmPassword = request.getParameter(FormFields.confirmpassword.getValue());
        String firstName = request.getParameter(FormFields.firstname.getValue());
        String lastName = request.getParameter(FormFields.lastname.getValue());
        String mail = request.getParameter(FormFields.mail.getValue());
        UserType userType = request.getParameter("usertype").equals("admin")? UserType.Admin:UserType.User;
        if (!password.equals(confirmPassword)) {
            request.getSession().setAttribute("error", "Passwords don't match");
            logger.error("Passwords don't match");
            response.sendRedirect("users-list");
            return;
        }
        if (userRepository.findByUserName(userName) != null) {
            request.getSession().setAttribute("error", "Username is already taken");
            logger.error("Username is already taken, {}", userName);
            response.sendRedirect("users-list");
            return;
        }

        User user = new User(userName, Cracker.code(password), firstName, lastName, mail);
        user.setUserType(userType);
        Promise promise = manager.insert(user);
        request.getSession().setAttribute(promise.getLevel().getValue(), promise.getText());
        response.sendRedirect("users-list");
    }
}
