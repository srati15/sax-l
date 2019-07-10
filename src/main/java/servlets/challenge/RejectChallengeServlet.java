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
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

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

        Map<Integer, Set<String>> setMap = (Map<Integer, Set<String>>) getServletContext().getAttribute("notifications");
        setMap.putIfAbsent(challenge.getReceiverId(), new ConcurrentSkipListSet<>());
        setMap.get(challenge.getReceiverId()).removeIf(notif->notif.endsWith(challenge.getTimestamp().toString()));
        request.getRequestDispatcher("profile").forward(request,response);
    }

}
