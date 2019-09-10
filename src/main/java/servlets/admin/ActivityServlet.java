package servlets.admin;

import dao.ActivityDao;
import dao.UserDao;
import datatypes.server.Activity;
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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@WebServlet(value = "/activities", asyncSupported = true)
public class ActivityServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(ActivityServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        DaoManager daoManager = (DaoManager) request.getServletContext().getAttribute("manager");
        ActivityDao activityDao = daoManager.getDao(DaoType.Activity);
        UserDao userDao = daoManager.getDao(DaoType.User);
        List<Activity> activities = new ArrayList<>(activityDao.findAll());
        activities.sort(Comparator.comparing(Activity::getDateTime).reversed());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss MMM dd, yyyy");
        request.setAttribute("formatter", formatter);
        request.setAttribute("activities", activities);
        request.setAttribute("userDao", userDao);

        request.getRequestDispatcher("/activities.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
