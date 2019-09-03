package context;

import dao.ActivityDao;
import dao.AnnouncementDao;
import dao.ToastDao;
import datatypes.announcement.Announcement;
import datatypes.server.Activity;
import datatypes.toast.Toast;
import datatypes.user.User;
import enums.DaoType;
import manager.DaoManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@WebListener()
public class ContextListener implements ServletContextListener {
    private static final Logger logger = LogManager.getLogger(ContextListener.class);
    private Map<Integer, User> onlineUsers = new ConcurrentHashMap<>();
    private DaoManager manager;
    public void contextInitialized(ServletContextEvent sce) {
        manager = new DaoManager();
        sce.getServletContext().setAttribute("manager", manager);
        sce.getServletContext().setAttribute("onlineUsers", onlineUsers);
        AnnouncementDao announcementDao = manager.getDao(DaoType.Announcement);
        ToastDao toastDao = manager.getDao(DaoType.Toast);
        sce.getServletContext().setAttribute("announcements", announcementDao.findAll().stream().filter(Announcement::isActive).collect(Collectors.toList()));
        sce.getServletContext().setAttribute("toasts", toastDao.findAll().stream().sorted(Comparator.comparing(Toast::getDateCreated).reversed()).collect(Collectors.toList()));

        logger.info("Server is running...");
    }

    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("Server is shutting down...");
        sce.getServletContext().removeAttribute("manager");
        sce.getServletContext().removeAttribute("onlineUsers");
        manager.shutDown();
        logger.info("Server has shut down !!");
    }
}
