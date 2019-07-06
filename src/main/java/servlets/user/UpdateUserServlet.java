package servlets.user;

import dao.UserDao;
import datatypes.User;
import enums.DaoType;
import enums.UserType;
import manager.DaoManager;
import security.Cracker;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/UpdateUserServlet")
public class UpdateUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DaoManager manager = (DaoManager) request.getServletContext().getAttribute("manager");
        UserDao userDao = manager.getDao(DaoType.User);
        User user = (User) request.getSession().getAttribute("user");
        String userName = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmpassword");
        String firstName = request.getParameter("firstname");
        String lastName = request.getParameter("lastname");
        String mail = request.getParameter("email");
        int id = Integer.parseInt(request.getParameter("hiddenId"));
        UserType userType = UserType.User;
        if (request.getParameter("usertype") != null && request.getParameter("usertype").equals("admin") ) userType = UserType.Admin;
        if (user.getId() == id && userType == UserType.User && user.getUserType() == UserType.Admin) {
            request.setAttribute("error", "You can't downgrade yourself to user");
            request.getRequestDispatcher("users-list").forward(request, response);
            return;
        }
        if ((password == null || confirmPassword == null )) {
            if (user.getUserType()== UserType.Admin) request.getRequestDispatcher("users-list").forward(request, response);
            else request.getRequestDispatcher("profile").forward(request, response);
            return;
        }

        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "Passwords don't match");
            System.out.println("Passwords don't match");
            if (user.getUserType()== UserType.Admin) request.getRequestDispatcher("users-list").forward(request, response);
            else request.getRequestDispatcher("profile").forward(request, response);
            return;
        }
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(new Cracker().code(password));
        user.setUserType(userType);
        userDao.update(user);
        if (user.getUserType()== UserType.Admin) request.getRequestDispatcher("users-list").forward(request, response);
        else request.getRequestDispatcher("profile").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
