package servlets.messages;

import dao.TextMessageDao;
import datatypes.User;
import datatypes.messages.TextMessage;
import enums.DaoType;
import manager.DaoManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@WebServlet("/TextMessageServlet")
public class TextMessageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DaoManager manager = (DaoManager) request.getServletContext().getAttribute("manager");
        User user = (User) request.getSession().getAttribute("user");
        int receiverId = Integer.parseInt(request.getParameter("receiverId"));
        TextMessageDao textMessageDao = manager.getDao(DaoType.TextMessage);
        Timestamp dateSent = Timestamp.valueOf(LocalDateTime.now());
        String message = request.getParameter("msg");
        TextMessage mes = new TextMessage(user.getId(), receiverId, dateSent, message);
        manager.insert(mes);
        request.getRequestDispatcher("user-profile?userid=" + receiverId).forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

    }
}
