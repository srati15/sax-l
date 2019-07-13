package servlets.user;

import dao.UserDao;
import datatypes.user.User;
import enums.DaoType;
import enums.UserType;
import manager.DaoManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/PromoteUserServlet")
public class PromoteUserServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(PromoteUserServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DaoManager manager = (DaoManager) request.getServletContext().getAttribute("manager");
        int promotableUserId = Integer.parseInt(request.getParameter("promotableUserId"));
        UserDao userDao = manager.getDao(DaoType.User);
        User promotable = userDao.findById(promotableUserId);
        promotable.setUserType(UserType.Admin);
        if (manager.update(promotable)){
            logger.info("{} is promoted to Admin", promotable.getUserName());
        }else {
            logger.error("Error promoting {}", promotable.getUserName());
        }
        request.getRequestDispatcher("/users-list").forward(request, response);
    }

}
