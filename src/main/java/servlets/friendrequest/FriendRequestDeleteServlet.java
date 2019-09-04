package servlets.friendrequest;

import dao.FriendRequestDao;
import datatypes.messages.FriendRequest;
import datatypes.promise.Promise;
import datatypes.user.User;
import enums.DaoType;
import manager.DaoManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/FriendRequestDeleteServlet")
public class FriendRequestDeleteServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        DaoManager manager = (DaoManager) request.getServletContext().getAttribute("manager");
        FriendRequestDao friendRequestDao = manager.getDao(DaoType.FriendRequest);
        User user = (User) request.getSession().getAttribute("user");

        int receiverId = Integer.parseInt(request.getParameter("receiverId"));
        FriendRequest request1 = friendRequestDao.findBySenderReceiverId(user.getId(), receiverId);
        FriendRequest request2 = friendRequestDao.findBySenderReceiverId(receiverId, user.getId());
        if (request1 != null) {
            Promise promise = manager.delete(request1);
            request.getSession().setAttribute(promise.getLevel().getValue(), promise.getText());
        } else if (request2 != null) {
            Promise promise = manager.delete(request2);
            request.getSession().setAttribute(promise.getLevel().getValue(), promise.getText());
        }
        String callingPage = request.getParameter("callingPage");

        if (callingPage != null && callingPage.equals("profile")) {
            response.sendRedirect("profile");
        } else
            response.sendRedirect("user-profile?userid=" + receiverId);
    }

    private void removeFromNotifications(DaoManager manager, FriendRequest request2) {
    }
}
