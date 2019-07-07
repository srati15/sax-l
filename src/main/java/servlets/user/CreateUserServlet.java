package servlets.user;

import dao.UserDao;
import datatypes.User;
import enums.DaoType;
import enums.FormFields;
import enums.UserType;
import manager.DaoManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/CreateUserServlet")
public class CreateUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        UserDao userRepository = ((DaoManager) getServletContext().getAttribute("manager")).getDao(DaoType.User);
        String userName = request.getParameter(FormFields.username.getValue());
        String password = request.getParameter(FormFields.password.getValue());
        String confirmPassword = request.getParameter(FormFields.confirmpassword.getValue());
        String firstName = request.getParameter(FormFields.firstname.getValue());
        String lastName = request.getParameter(FormFields.lastname.getValue());
        String mail = request.getParameter(FormFields.mail.getValue());
        UserType userType = request.getParameter("usertype").equals("admin")? UserType.Admin:UserType.User;
        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "Passwords don't match");
            System.out.println("Passwords don't match");
            request.getRequestDispatcher("users-list").forward(request, response);
            return;
        }
        if (userRepository.findByUserName(userName) != null) {
            request.setAttribute("error", "Username is already taken");
            System.out.println("Username is already taken");
            request.getRequestDispatcher("users-list").forward(request, response);
            return;
        }

        User user = new User(userName, password, firstName, lastName, mail);
        user.setUserType(userType);
        userRepository.insert(user);
        request.getRequestDispatcher("users-list").forward(request, response);
    }
}
