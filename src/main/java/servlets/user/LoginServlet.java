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

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(LoginServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        DaoManager manager = (DaoManager) getServletContext().getAttribute("manager");
        UserDao userRepository = manager.getDao(DaoType.User);
        String userName = request.getParameter("username");
        String passwordHash = Cracker.code(request.getParameter("password"));
        User user = userRepository.findByUserName(userName);
        if (user == null) {
            request.setAttribute("error", "Wrong login credentials");
            logger.error("User with username {} deosn't exist", userName);
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }
        if (!user.getPassword().equals(passwordHash)) {
            request.setAttribute("error", "Wrong login credentials");
            logger.debug("Wrong login credentials");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }
        request.getSession().setAttribute("user", user);
        request.setAttribute("info", "Successful login.\n"+user.getUserName()+", Welcome to Sax-L");
        Map<Integer, User> userMap = (Map<Integer, User>) request.getServletContext().getAttribute("onlineUsers");
        userMap.put(user.getId(), user);
        ActivityDao activityDao = manager.getDao(DaoType.Activity);
        activityDao.insert(new Activity(user.getId(), "logged in", LocalDateTime.now()));
        RequestDispatcher dispatcher = request.getRequestDispatcher("");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }
}
