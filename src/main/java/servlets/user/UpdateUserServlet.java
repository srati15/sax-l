package servlets.user;

import dao.UserDao;
import datatypes.User;
import enums.DaoType;
import enums.UserType;
import manager.DaoManager;

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
        UserType userType = request.getParameter("usertype").equals("admin")? UserType.Admin:UserType.User;
        if (user.getId() == id && userType == UserType.User) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "You can't downgrade yourself to user");
            return;
        }
        if (password == null || confirmPassword == null) request.getRequestDispatcher("users-list").forward(request, response);
        User updatedUser = new User(userName, password, firstName, lastName, mail);
        updatedUser.setId(id);
        updatedUser.setUserType(userType);
        if (!password.equals(confirmPassword)) {
            response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE, "Passwords don't match");
            return;
        }

        userDao.update(updatedUser);
        request.getRequestDispatcher("users-list").forward(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
