package servlets.announcement;

import dao.AnnouncementDao;
import datatypes.announcement.Announcement;
import datatypes.user.User;
import enums.DaoType;
import enums.FormFields;
import manager.DaoManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/announcements")
public class AnnouncementPageServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DaoManager manager = (DaoManager) request.getServletContext().getAttribute("manager");
        AnnouncementDao announcementDao = manager.getDao(DaoType.Announcement);
        request.setAttribute("allAnnouncements", announcementDao.findAll());
        request.getRequestDispatcher("announcements.jsp").forward(request, response);
    }
}
