package servlets.friendrequest;

import dao.FriendRequestDao;
import datatypes.messages.FriendRequest;
import datatypes.user.User;
import enums.DaoType;
import enums.RequestStatus;
import manager.DaoManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

@WebServlet("/FriendRequestSenderServlet")
public class FriendRequestSenderServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        DaoManager manager = (DaoManager) request.getServletContext().getAttribute("manager");
        FriendRequestDao friendRequestDao = manager.getDao(DaoType.FriendRequest);
        User user = (User) request.getSession().getAttribute("user");
        int receiverId = Integer.parseInt(request.getParameter("receiverId"));
        Timestamp dateSent = Timestamp.valueOf(LocalDateTime.now());
        FriendRequest friendRequest = new FriendRequest(user.getId(), receiverId, RequestStatus.Pending, dateSent.toLocalDateTime() );
        manager.insert(friendRequest);
        Map<Integer, Set<String>> setMap = (Map<Integer, Set<String>>) getServletContext().getAttribute("notifications");
        setMap.putIfAbsent(receiverId, new ConcurrentSkipListSet<>());
        setMap.get(receiverId).add(user.getUserName()+" sent you a friend request "+friendRequest.getTimestamp());
        request.getRequestDispatcher("user-profile?userid=" + receiverId).forward(request, response);
    }

}
