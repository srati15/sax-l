package context;

import dao.ActivityDao;
import datatypes.server.Activity;
import datatypes.user.User;
import enums.DaoType;
import manager.DaoManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@WebListener()
public class ContextListener implements ServletContextListener {
    private static final Logger logger = LogManager.getLogger(ContextListener.class);
    private Map<Integer, User> onlineUsers = new ConcurrentHashMap<>();
    private DaoManager manager;
    public void contextInitialized(ServletContextEvent sce) {
        manager = new DaoManager();
        sce.getServletContext().setAttribute("manager", manager);
        sce.getServletContext().setAttribute("onlineUsers", onlineUsers);
        logger.info("Server is running...");
    }

    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("Server is shutting down...");
        ActivityDao activityDao = manager.getDao(DaoType.Activity);
        onlineUsers.values().forEach(user -> activityDao.insert(new Activity(user.getId(), "logged out", LocalDateTime.now())) );
        sce.getServletContext().removeAttribute("manager");
        sce.getServletContext().removeAttribute("onlineUsers");
        logger.info("Server has shut down !!");
    }
}
