package servlets.messages;

import datatypes.messages.AdminMessage;
import datatypes.messages.AdminReply;
import datatypes.promise.Promise;
import manager.DaoManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/ReplyMessageServlet")
public class ReplyMessageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DaoManager manager = (DaoManager) request.getServletContext().getAttribute("manager");
        int messageId = Integer.parseInt(request.getParameter("messageId"));
        String messageText = request.getParameter("replyText");
        Promise promise = manager.insert(new AdminReply(messageId, messageText, LocalDateTime.now()));
        request.getSession().setAttribute(promise.getLevel().getValue(), promise.getText());

        response.sendRedirect("inbox");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

    }
}
