package servlets.announcement;

import dao.AnnouncementDao;
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

@WebServlet(value = "/DeleteAnnouncementServlet", asyncSupported = true)
public class DeleteAnnouncementServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DaoManager manager = (DaoManager) request.getServletContext().getAttribute("manager");
        AnnouncementDao announcementDao = manager.getDao(DaoType.Announcement);
        User user = (User) request.getSession().getAttribute("user");
        Promise promise = manager.delete(user.getId(),announcementDao.findById(Integer.parseInt(request.getParameter("announcementId"))));
        request.getSession().setAttribute(promise.getLevel().getValue(), promise.getText());
        response.sendRedirect("announcements");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

    }
}
