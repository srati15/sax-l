package servlets.user;

import datatypes.user.User;
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

@WebServlet("/UpdateUserServlet")
public class UpdateUserServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(UpdateUserServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DaoManager manager = (DaoManager) request.getServletContext().getAttribute("manager");
        User user = (User) request.getSession().getAttribute("user");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmpassword");
        String firstName = request.getParameter("firstname");
        String lastName = request.getParameter("lastname");
        int id = Integer.parseInt(request.getParameter("hiddenId"));
        UserType userType = UserType.User;
        if (request.getParameter("usertype") != null && request.getParameter("usertype").equals("admin"))
            userType = UserType.Admin;
        if (user.getId() == id && userType == UserType.User && user.getUserType() == UserType.Admin && request.getParameter("usertype")!=null) {
            request.setAttribute("error", "You can't downgrade yourself to user");
            request.getRequestDispatcher("users-list").forward(request, response);
            return;
        }
        if ((password == null || confirmPassword == null)) {
            if (user.getUserType() == UserType.Admin)
                request.getRequestDispatcher("users-list").forward(request, response);
            else request.getRequestDispatcher("profile").forward(request, response);
            return;
        }

        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "Passwords don't match");
            logger.error("Passwords don't match");
            if (user.getUserType() == UserType.Admin)
                request.getRequestDispatcher("users-list").forward(request, response);
            else request.getRequestDispatcher("profile").forward(request, response);
            return;
        }
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(Cracker.code(password));
        if (request.getParameter("usertype")!= null) user.setUserType(userType);
        manager.update(user);

        if (user.getUserType() == UserType.Admin) request.getRequestDispatcher("users-list").forward(request, response);
        else request.getRequestDispatcher("profile").forward(request, response);
    }

}
