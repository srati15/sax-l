package servlets.user;

import dao.ActivityDao;
import dao.QuizDao;
import dao.UserDao;
import datatypes.Activity;
import datatypes.Quiz;
import datatypes.User;
import enums.DaoType;
import manager.DaoManager;
import security.Cracker;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        DaoManager manager = (DaoManager) getServletContext().getAttribute("manager");
        UserDao userRepository = manager.getDao(DaoType.User);
        String userName = request.getParameter("username");
        String passwordHash = Cracker.code(request.getParameter("password"));
        User user = userRepository.findByUserName(userName);
        if (user == null) {
            request.setAttribute("error", "Wrong login credentials");
            System.out.println("Wrong username");
            request.getRequestDispatcher("login").forward(request, response);
            return;
        }
        if (!user.getPassword().equals(passwordHash)) {
            request.setAttribute("error", "Wrong login credentials");
            System.out.println("Wrong login credentials");
            request.getRequestDispatcher("login").forward(request, response);
            return;
        }
        request.getSession().setAttribute("user", user);
        ActivityDao activityDao = manager.getDao(DaoType.Activity);
        activityDao.insert(new Activity(user.getId(), "logged in", LocalDateTime.now()));
        RequestDispatcher dispatcher = request.getRequestDispatcher("");

        QuizDao quizDao = manager.getDao(DaoType.Quiz);
        List<Quiz> quizzes = new ArrayList<>(quizDao.findAll());
        quizzes.sort(Comparator.comparingInt(Quiz::getTimesDone).reversed());
        quizzes = quizzes.stream().limit(5).collect(Collectors.toList());
        request.getSession().setAttribute("topQuizzes", quizzes);

        dispatcher.forward(request, response);
    }
}
