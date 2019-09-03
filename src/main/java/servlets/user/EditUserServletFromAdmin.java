package servlets.user;

import dao.UserDao;
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

@WebServlet("/EditUserServletFromAdmin")
public class EditUserServletFromAdmin extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(EditUserServletFromAdmin.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        DaoManager manager = (DaoManager) getServletContext().getAttribute("manager");
        UserDao userRepository = ((DaoManager) getServletContext().getAttribute("manager")).getDao(DaoType.User);
        String password = request.getParameter(FormFields.password.getValue());
        String confirmPassword = request.getParameter(FormFields.confirmpassword.getValue());
        String firstName = request.getParameter(FormFields.firstname.getValue());
        String lastName = request.getParameter(FormFields.lastname.getValue());
        UserType userType = request.getParameter("usertype").equals("admin")? UserType.Admin:UserType.User;
        int hiddenId = Integer.parseInt(request.getParameter("hiddenId"));
        User editedUser = userRepository.findById(hiddenId);
        if (!password.equals(confirmPassword)){
            request.getSession().setAttribute("error", "Passwords don't match");
            request.getRequestDispatcher("users-list").forward(request,response);
            return;
        }
        editedUser.setUserType(userType);
        editedUser.setPassword(Cracker.code(password));
        editedUser.setFirstName(firstName);
        editedUser.setLastName(lastName);
        manager.update(editedUser);
        request.getSession().setAttribute("info", "Profile updated successfully");
        request.getRequestDispatcher("users-list").forward(request, response);
    }
}
