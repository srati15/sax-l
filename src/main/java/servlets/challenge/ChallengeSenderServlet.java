package servlets.challenge;


import dao.QuizDao;
import datatypes.messages.QuizChallenge;
import datatypes.quiz.Quiz;
import datatypes.quiz.question.Question;
import datatypes.user.User;
import enums.DaoType;
import enums.RequestStatus;
import manager.DaoManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
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
        QuizChallenge challenge = new QuizChallenge(user.getId(), receiverId, LocalDateTime.now(), quizId, RequestStatus.Pending);
        manager.insert(challenge);
        Map<Integer, Set<String>> setMap = (Map<Integer, Set<String>>) getServletContext().getAttribute("notifications");
        setMap.putIfAbsent(receiverId, new ConcurrentSkipListSet<>());
        setMap.get(receiverId).add(user.getUserName()+" sent you a challenge "+challenge.getTimestamp());
        startQuiz(request, response, quiz);
    }

    public static void startQuiz(HttpServletRequest request, HttpServletResponse response, Quiz quiz) throws ServletException, IOException {
        List<Question> questionList = quiz.getQuestionAnswerMap().keySet().stream().collect(Collectors.toList());
        if (quiz.isRandomized()) {
            Collections.shuffle(questionList);
        }
        request.setAttribute("currentQuizQuestions", questionList);
        request.setAttribute("questionAnswerMap", quiz.getQuestionAnswerMap());
        request.getRequestDispatcher("/start-quiz.jsp").forward(request, response);
    }


}
