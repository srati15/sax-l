package servlets.messages;

import dao.AdminMessageDao;
import datatypes.messages.AdminMessage;
import enums.DaoType;
import manager.DaoManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/DeleteInboxMessageServlet")
public class DeleteInboxMessageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DaoManager manager = (DaoManager) request.getServletContext().getAttribute("manager");
        AdminMessageDao adminMessageDao = manager.getDao(DaoType.AdminMessage);
        int messageId = Integer.parseInt(request.getParameter("messageId"));
        if (adminMessageDao.deleteById(messageId)){
            request.setAttribute("info", "Message deleted.");
        }else {
            request.setAttribute("error", "Error deleting Message.");
        }
        request.getRequestDispatcher("inbox").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

    }
}
