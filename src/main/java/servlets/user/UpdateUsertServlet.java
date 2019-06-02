package servlets.user;

import dao.UserDao;
import datatypes.User;
import enums.DaoType;
import manager.DaoManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/UpdateUserServlet")
public class UpdateUsertServlet extends HttpServlet {
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
        if (password == null || confirmPassword == null) request.getRequestDispatcher("users-list").forward(request, response);
        User updatedUser = new User(userName, password, firstName, lastName, mail);
        updatedUser.setId(id);
        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "Passwords don't match");
            System.out.println("Passwords don't match");
            request.getRequestDispatcher("profile").forward(request, response);
            return;
        }

        if (password.length() < 4) {
            System.out.println("Password must be of minimum 4 characters");
            request.setAttribute("error", "Password must be of minimum 4 characters");
            request.getRequestDispatcher("profile").forward(request, response);
        }
        userDao.update(updatedUser);
        request.getRequestDispatcher("users-list").forward(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
