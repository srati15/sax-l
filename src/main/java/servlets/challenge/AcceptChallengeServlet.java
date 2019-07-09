package servlets.challenge;


import dao.QuizChallengeDao;
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
import java.util.stream.Collectors;

@WebServlet("/AcceptChallengeServlet")
public class AcceptChallengeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DaoManager manager = (DaoManager) request.getServletContext().getAttribute("manager");
        QuizChallengeDao quizChallengeDao = manager.getDao(DaoType.QuizChallenge);
        int challengeId = Integer.parseInt(request.getParameter("challengeId"));
        QuizChallenge challenge = quizChallengeDao.findById(challengeId);
        QuizDao quizDao = manager.getDao(DaoType.Quiz);
        Quiz quiz = quizDao.findById(challenge.getQuizId());
        User user = (User) request.getSession().getAttribute("user");
        user.getQuizChallenges().removeIf(s->s.getId() == challengeId);
        challenge.setRequestStatus(RequestStatus.Accepted);
        quizChallengeDao.update(challenge);
        startQuiz(request, response, quiz);
    }

    public static void startQuiz(HttpServletRequest request, HttpServletResponse response, Quiz quiz) throws ServletException, IOException {
        List<Question> questionList = quiz.getQuestionAnswerMap().keySet().stream().collect(Collectors.toList());
        if (quiz.isRandomized()) {
            Collections.shuffle(questionList);
        }
        request.setAttribute("currentQuizQuestions", questionList);
        request.setAttribute("questionAnswerMap", quiz.getQuestionAnswerMap());
        String url = "/start-quiz.jsp?quizId="+quiz.getId();
        if (!quiz.isOnePage()) url="start-quiz?quizId="+quiz.getId()+"&questionId=1";
        request.getRequestDispatcher(url).forward(request, response);
    }


}
