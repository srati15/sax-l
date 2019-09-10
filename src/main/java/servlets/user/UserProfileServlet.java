package servlets.user;

import dao.FriendRequestDao;
import dao.TextMessageDao;
import dao.UserDao;
import datatypes.messages.TextMessage;
import datatypes.user.User;
import enums.DaoType;
import manager.DaoManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(value = "/user-profile", asyncSupported = true)
public class UserProfileServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(UserProfileServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        DaoManager manager = (DaoManager) request.getServletContext().getAttribute("manager");
        FriendRequestDao friendRequestDao = manager.getDao(DaoType.FriendRequest);
        UserDao userDao = manager.getDao(DaoType.User);
        User user = (User) request.getSession().getAttribute("user");
        int id = Integer.parseInt(request.getParameter("userid"));
        if (user.getId() == id) request.getRequestDispatcher("profile").forward(request, response);
        User profileUser = userDao.findById(id);
        TextMessageDao textMessageDao = manager.getDao(DaoType.TextMessage);
        List<TextMessage> messages = textMessageDao.getTextMessagesOfGivenUsers(user.getId(), id);
        request.setAttribute("request1", friendRequestDao.findBySenderReceiverId(user.getId(), id));
        request.setAttribute("request2", friendRequestDao.findBySenderReceiverId(id, user.getId()));
        request.setAttribute("mess", messages);
        request.setAttribute("profileUser", profileUser);
        request.getRequestDispatcher("/user-profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       doGet(req, resp);
    }
}
