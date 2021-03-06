package servlets.friendrequest;

import dao.FriendRequestDao;
import datatypes.messages.FriendRequest;
import datatypes.promise.Promise;
import datatypes.user.User;
import enums.DaoType;
import enums.RequestStatus;
import manager.DaoManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(value = "/FriendRequestAcceptServlet", asyncSupported = true)
public class FriendRequestAcceptServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        DaoManager manager = (DaoManager) request.getServletContext().getAttribute("manager");
        FriendRequestDao friendRequestDao = manager.getDao(DaoType.FriendRequest);
        User user = (User) request.getSession().getAttribute("user");
        int receiverId = Integer.parseInt(request.getParameter("receiverId"));
        FriendRequest request1 = friendRequestDao.findBySenderReceiverId(user.getId(), receiverId);
        FriendRequest request2 = friendRequestDao.findBySenderReceiverId(receiverId, user.getId());
        if (request1 != null) {
            removeFromNotifications(manager, request1, request.getSession());
        } else if (request2 != null) {
            removeFromNotifications(manager, request2, request.getSession());
        };
        response.sendRedirect("profile");
    }

    private void removeFromNotifications(DaoManager manager, FriendRequest request2, HttpSession session) {
        request2.setStatus(RequestStatus.Accepted);
        Promise promise = manager.update(request2);
        session.setAttribute(promise.getLevel().getValue(), promise.getText());
    }
}
