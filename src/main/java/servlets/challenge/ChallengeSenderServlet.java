package servlets.challenge;


import dao.QuizDao;
import datatypes.Quiz;
import datatypes.User;
import datatypes.question.Question;
import enums.DaoType;
import manager.DaoManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/ChallengeSenderServlet")
public class ChallengeSenderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DaoManager manager = (DaoManager) request.getServletContext().getAttribute("manager");
        User user = (User) request.getSession().getAttribute("user");
        int receiverId = Integer.parseInt(request.getParameter("receiverId"));
        int quizId = Integer.parseInt(request.getParameter("quizId"));
        QuizDao quizDao = manager.getDao(DaoType.Quiz);
        Quiz quiz = quizDao.findById(Integer.valueOf(quizId));
        List<Question> questionList = quiz.getQuestionAnswerMap().keySet().stream().collect(Collectors.toList());
        if (quiz.isRandomized()) {
            Collections.shuffle(questionList);
        }
        request.setAttribute("currentQuizQuestions", questionList);
        request.setAttribute("questionAnswerMap", quiz.getQuestionAnswerMap());
        request.getRequestDispatcher("/start-quiz.jsp").forward(request, response);
    }


}
