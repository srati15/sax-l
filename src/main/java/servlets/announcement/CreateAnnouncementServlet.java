package servlets.announcement;

import datatypes.Announcement;
import datatypes.User;
import enums.FormFields;
import manager.DaoManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/CreateAnnouncementServlet")
public class CreateAnnouncementServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DaoManager manager = (DaoManager) request.getServletContext().getAttribute("manager");
        User user = (User) request.getSession().getAttribute("user");
        String announcementText = request.getParameter("announcementText");
        String hyperlink = request.getParameter("hyperlink");
        boolean active = request.getParameter(FormFields.activeOrNot.getValue()).equals("Active");
        Announcement announcement = new Announcement(user.getId(), announcementText, hyperlink, active);
        manager.insert(announcement);
        request.getRequestDispatcher("announcements").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
