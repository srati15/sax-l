package servlets.user;

import dao.ActivityDao;
import dao.QuizDao;
import dao.UserDao;
import datatypes.messages.Message;
import datatypes.messages.TextMessage;
import datatypes.server.Activity;
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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(ProfileServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        DaoManager manager = (DaoManager) request.getServletContext().getAttribute("manager");
        User user = (User) request.getSession().getAttribute("user");
        UserDao userDao = manager.getDao(DaoType.User);
        QuizDao quizDao = manager.getDao(DaoType.Quiz);
        ActivityDao activityDao = manager.getDao(DaoType.Activity);
        List<Activity> userActivities = activityDao.findAll().stream().filter(s -> s.getUserId() == user.getId()).collect(Collectors.toList());
        userActivities.sort(Comparator.comparing(Activity::getDateTime).reversed());
        List<TextMessage> textMessages = new ArrayList<>();
        for (List<TextMessage> messages : user.getTextMessages().values()) {
            for (TextMessage message : messages) {
                if (message.getSenderId() != user.getId()) textMessages.add(message);
            }
        }
        textMessages.sort(Comparator.comparing(Message::getTimestamp).reversed());
        request.setAttribute("textMessages", textMessages);
        request.setAttribute("activities", userActivities);
        request.setAttribute("friendsIds", user.getFriends());
        request.setAttribute("requestList", user.getPendingFriendRequests());
        request.setAttribute("userDao", userDao);
        request.setAttribute("quizDao", quizDao);
        request.setAttribute("user", user);
        request.getRequestDispatcher("/profile.jsp").forward(request, response);
    }
}
