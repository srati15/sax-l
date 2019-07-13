package servlets.quiz;

import dao.QuizDao;
import dao.QuizResultDao;
import dao.UserDao;
import datatypes.quiz.QuizResult;
import datatypes.user.User;
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
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@WebServlet("/quiz-details")
public class QuizDetails extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(QuizDetails.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        DaoManager manager = (DaoManager) request.getServletContext().getAttribute("manager");
        UserDao userDao = manager.getDao(DaoType.User);
        QuizDao quizDao = manager.getDao(DaoType.Quiz);
        QuizResultDao quizResultDao = manager.getDao(DaoType.QuizResult);
        int quizId = Integer.valueOf(request.getParameter("quizId"));
        Map<Integer, List<QuizResult>> quizResultMap = new HashMap<>();
        quizResultDao.findAll().stream().filter(s -> s.getQuizId() == quizId).collect(Collectors.toList()).forEach(s -> {
            quizResultMap.putIfAbsent(s.getUserId(), new ArrayList<>());
            quizResultMap.get(s.getUserId()).add(s);
        });
        List<QuizResult> topPerformers = quizResultDao.findAll().stream().sorted(Comparator.comparing(QuizResult::getScore).reversed().thenComparing(QuizResult::getTimeSpent)).limit(3).collect(Collectors.toList());
        List<QuizResult> topPerformersToday = quizResultDao.findAll().stream().filter(q -> q.getTimestamp().toLocalDate().equals(LocalDate.now())).sorted(Comparator.comparing(QuizResult::getScore).reversed().thenComparing(QuizResult::getTimeSpent)).limit(3).collect(Collectors.toList());
        request.setAttribute("topPerformersToday", topPerformersToday);
        request.setAttribute("quizResults", quizResultMap);
        request.setAttribute("topPerformers", topPerformers);
        request.setAttribute("userDao", userDao);
        request.setAttribute("quizDao", quizDao);
        request.setAttribute("quiz", quizDao.findById(quizId));
        request.getRequestDispatcher("/quiz-details.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
