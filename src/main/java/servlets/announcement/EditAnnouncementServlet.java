package servlets.announcement;

import datatypes.announcement.Announcement;
import datatypes.user.User;
import manager.DaoManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/EditAnnouncementServlet")
public class EditAnnouncementServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DaoManager manager = (DaoManager) request.getServletContext().getAttribute("manager");
        User user = (User) request.getSession().getAttribute("user");
        String announcementText = request.getParameter("announcementText");
        String hyperlink = request.getParameter("hyperlink");
        if (hyperlink == null) hyperlink="/";
        boolean active = request.getParameter("activeOrNot").equals("Active");
        int id = Integer.parseInt(request.getParameter("editAnnouncementId"));
        Announcement announcement = new Announcement(id, user.getId(), announcementText, hyperlink, active);
        manager.update(announcement);
        request.setAttribute("info", "Announcement updated successfully");
        request.getRequestDispatcher("announcements").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

    }

}
