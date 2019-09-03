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
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
            request.getSession().setAttribute("error", "Wrong login credentials");
            logger.error("User with username {} deosn't exist", userName);
            response.sendRedirect("/");
            return;
        }
        if (!user.getPassword().equals(passwordHash)) {
            request.getSession().setAttribute("error", "Wrong login credentials");
            logger.debug("Wrong login credentials");
            response.sendRedirect("/");
            return;
        }
        request.getSession().setAttribute("user", user);
        request.getSession().setAttribute("info", "Successful login.\n"+user.getUserName()+", Welcome to Sax-L");
        logger.info("Successful login, {}", userName);
        Map<Integer, User> userMap = (Map<Integer, User>) request.getServletContext().getAttribute("onlineUsers");
        userMap.put(user.getId(), user);
        ActivityDao activityDao = manager.getDao(DaoType.Activity);
        List<Activity> friendsActivities = new ArrayList<>();
        user.getFriends().forEach(friend->friendsActivities.addAll(activityDao.findAllForUser(friend.getId())));

        List<Activity> userActivities = activityDao.findAll().stream().filter(s -> s.getUserId() == user.getId()).
                sorted(Comparator.comparing(Activity::getDateTime).reversed()).collect(Collectors.toList());
        request.getSession().setAttribute("activities", userActivities);
        request.getSession().setAttribute("friendsIds", user.getFriends());

        response.sendRedirect("/");
    }

}
