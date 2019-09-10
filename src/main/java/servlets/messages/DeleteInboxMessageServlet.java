package servlets.messages;

import dao.AdminMessageDao;
import datatypes.promise.Promise;
import enums.DaoType;
import manager.DaoManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/DeleteInboxMessageServlet", asyncSupported = true)
public class DeleteInboxMessageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DaoManager manager = (DaoManager) request.getServletContext().getAttribute("manager");
        AdminMessageDao adminMessageDao = manager.getDao(DaoType.AdminMessage);
        int messageId = Integer.parseInt(request.getParameter("messageId"));
        Promise promise = adminMessageDao.deleteById(messageId);
        request.getSession().setAttribute(promise.getLevel().getValue(), promise.getText());
        response.sendRedirect("inbox");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

    }
}
