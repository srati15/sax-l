package servlets.admin;

import dao.AnnouncementDao;
import dao.UserAchievementDao;
import dao.UserDao;
import enums.DaoType;
import manager.DaoManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/statistics")
public class StatisticsServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(StatisticsServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        DaoManager manager = (DaoManager) getServletContext().getAttribute("manager");
        Map<String, Integer> stats = new HashMap<>();

        UserDao userDao = manager.getDao(DaoType.User);
        stats.put("Users", userDao.findAll().size());

        AnnouncementDao announcementDao = manager.getDao(DaoType.Announcement);
        stats.put("Announcements", announcementDao.findAll().size());

        UserAchievementDao achievementDao = manager.getDao(DaoType.UserAchievement);
        stats.put("Achievements", achievementDao.findAll().size());

        request.setAttribute("stats", stats);

        request.getRequestDispatcher("/statistics.jsp").forward(request, response);
    }

}
