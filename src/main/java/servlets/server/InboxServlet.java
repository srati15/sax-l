package servlets.server;

import dao.AdminMessageDao;
import datatypes.messages.AdminMessage;
import datatypes.server.ServerLog;
import enums.DaoType;
import enums.Level;
import manager.DaoManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/inbox")
public class InboxServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(InboxServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        DaoManager daoManager = (DaoManager) getServletContext().getAttribute("manager");
        AdminMessageDao adminMessageDao = daoManager.getDao(DaoType.AdminMessage);
        List<AdminMessage> notSeen = adminMessageDao.findAll().stream().filter(s->!s.isSeen()).collect(Collectors.toList());
        List<AdminMessage> seen = adminMessageDao.findAll().stream().filter(AdminMessage::isSeen).collect(Collectors.toList());

        notSeen.sort(Comparator.comparing(AdminMessage::getTime).reversed());
        seen.sort(Comparator.comparing(AdminMessage::getTime).reversed());

        request.setAttribute("seen", seen);
        request.setAttribute("notSeen", notSeen);

        request.getRequestDispatcher("/inbox.jsp").forward(request, response);
    }

}
