package servlets.admin;

import datatypes.server.ServerLog;
import enums.Level;
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
import java.util.List;
@WebServlet(value = "/server-logs", asyncSupported = true)
public class ServerLogServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(ServerLogServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String fileName = "application.log";
        String line;
        List<ServerLog> serverLogs = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while((line = bufferedReader.readLine()) != null) {
                String[] ar = line.split("!!");
                Level level = Level.getByValue(ar[1]);
                serverLogs.add(new ServerLog(level, ar[0], ar[2]));
            }
            bufferedReader.close();
            Collections.reverse(serverLogs);
            request.setAttribute("logs",serverLogs);
        }
        catch(FileNotFoundException ex) {
            logger.error("Unable to open file '{}'",fileName);
        }
        catch(IOException ex) {
            logger.error("Error reading file '{}'", fileName);
        }
        request.getRequestDispatcher("/server-logs.jsp").forward(request, response);
    }

}
