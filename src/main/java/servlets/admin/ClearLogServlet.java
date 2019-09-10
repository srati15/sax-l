package servlets.admin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(value = "/clearLogs", asyncSupported = true)
public class ClearLogServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(ClearLogServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String fileName = "application.log";
        new PrintWriter(fileName).close();
        request.getSession().setAttribute("info", "Logs are deleted successfully");
        response.sendRedirect("/server-logs");
    }

}
