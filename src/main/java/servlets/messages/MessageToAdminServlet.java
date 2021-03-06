package servlets.messages;

import datatypes.messages.AdminMessage;
import datatypes.promise.Promise;
import manager.DaoManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet(value = "/MessageToAdminServlet", asyncSupported = true)
public class MessageToAdminServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DaoManager manager = (DaoManager) request.getServletContext().getAttribute("manager");
        String name = request.getParameter("name");
        String mail = request.getParameter("mail");
        String messageText = request.getParameter("message");
        String subject = request.getParameter("subject");
        Promise promise = manager.insert(new AdminMessage(name, mail, subject, messageText, LocalDateTime.now(), false));
        request.getSession().setAttribute(promise.getLevel().getValue(), promise.getText());
        response.sendRedirect("contact");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

    }
}
