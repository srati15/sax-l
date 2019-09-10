package servlets.friendrequest;

import datatypes.messages.FriendRequest;
import datatypes.promise.Promise;
import datatypes.user.User;
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

@WebServlet(value = "/FriendRequestReceiverServlet", asyncSupported = true)
public class FriendRequestReceiverServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        DaoManager manager = (DaoManager) request.getServletContext().getAttribute("manager");
        User user = (User) request.getSession().getAttribute("user");
        int receiverId = Integer.parseInt(request.getParameter("receiverId"));
        Timestamp dateSent = Timestamp.valueOf(LocalDateTime.now());
        FriendRequest friendRequest = new FriendRequest(user.getId(), receiverId, RequestStatus.Pending, dateSent.toLocalDateTime() );
        Promise promise = manager.insert(friendRequest);
        request.getSession().setAttribute(promise.getLevel().getValue(), promise.getText());
        response.sendRedirect("user-profile?userid=" + receiverId);
    }
}
