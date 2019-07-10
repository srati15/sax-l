package servlets.challenge;


import dao.QuizChallengeDao;
import datatypes.messages.QuizChallenge;
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

@WebServlet("/RejectChallengeServlet")
public class RejectChallengeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DaoManager manager = (DaoManager) request.getServletContext().getAttribute("manager");
        QuizChallengeDao quizChallengeDao = manager.getDao(DaoType.QuizChallenge);
        int challengeId = Integer.parseInt(request.getParameter("challengeId"));
        QuizChallenge challenge = quizChallengeDao.findById(challengeId);
        User user = (User) request.getSession().getAttribute("user");
        user.getQuizChallenges().removeIf(s->s.getId() == challengeId);
        challenge.setRequestStatus(RequestStatus.Rejected);
        quizChallengeDao.update(challenge);
        request.getRequestDispatcher("profile").forward(request,response);
    }

}
