package servlets.friendrequest;

import dao.FriendRequestDao;
import datatypes.user.User;
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

@WebServlet("/FriendRequestAcceptServlet")
public class FriendRequestAcceptServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        DaoManager manager = (DaoManager) request.getServletContext().getAttribute("manager");
        FriendRequestDao friendRequestDao = manager.getDao(DaoType.FriendRequest);
        User user = (User) request.getSession().getAttribute("user");
        int receiverId = Integer.parseInt(request.getParameter("receiverId"));
        FriendRequest request1 = friendRequestDao.findBySenderReceiverId(user.getId(), receiverId);
        FriendRequest request2 = friendRequestDao.findBySenderReceiverId(receiverId, user.getId());
        if(request1 != null){
            request1.setStatus(RequestStatus.Accepted);
            manager.update(request1);
        }
        else if(request2 != null){
            request2.setStatus(RequestStatus.Accepted);
            manager.update(request2);
        }
        request.getRequestDispatcher("profile").forward(request, response);
    }
}
