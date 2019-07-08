package servlets.friendrequest;

import dao.FriendRequestDao;
import datatypes.User;
import datatypes.messages.FriendRequest;
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

@WebServlet("/FriendRequestReceiverServlet")
public class FriendRequestReceiverServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        DaoManager manager = (DaoManager) request.getServletContext().getAttribute("manager");
        User user = (User) request.getSession().getAttribute("user");
        int receiverId = Integer.parseInt(request.getParameter("receiverId"));
        Timestamp dateSent = Timestamp.valueOf(LocalDateTime.now());
        FriendRequest friendRequest = new FriendRequest(user.getId(), receiverId, RequestStatus.Pending, dateSent.toLocalDateTime() );
        manager.insert(friendRequest);
        request.getRequestDispatcher("user-profile?userid=" + receiverId).forward(request, response);
    }
}
