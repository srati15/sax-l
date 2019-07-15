package servlets.quiz;

import dao.QuizDao;
import dao.UserAchievementDao;
import datatypes.quiz.Quiz;
import datatypes.quiz.QuizResult;
import datatypes.quiz.question.Question;
import datatypes.user.Achievement;
import datatypes.user.User;
import datatypes.user.UserAchievement;
import enums.DaoType;
import manager.DaoManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/CompleteQuizServlet")
public class CompleteQuizServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        DaoManager manager = (DaoManager) request.getServletContext().getAttribute("manager");
        QuizDao quizDao = manager.getDao(DaoType.Quiz);
        Map<String, String[]> params = request.getParameterMap();
        Map<Integer, String> questionAnswers = new HashMap<>();
        Map<Question, Boolean> quizResults = new HashMap<>();
        User user = (User) request.getSession().getAttribute("user");
        params.keySet().stream().filter(question->question.startsWith("questionN")).forEach(question->questionAnswers.put(Integer.valueOf(question.substring(question.lastIndexOf("N")+1)), request.getParameter(question)));
        Quiz quiz = quizDao.findById(Integer.valueOf(request.getParameter("quizId")));

        for (Question question : quiz.getQuestionAnswerMap().keySet()) {
            String answer = quiz.getQuestionAnswerMap().get(question).getAnswer().split(",")[0];
            if (questionAnswers.get(question.getId())!= null && questionAnswers.get(question.getId()).equalsIgnoreCase(answer)) {
                quizResults.put(question, true);
            }else {
                quizResults.put(question, false);
            }
        }
        String time = request.getParameter("completeTime");
        int seconds = getSeconds(time);
        int result = (int) quizResults.values().stream().filter(s -> s).count();


        QuizResult quizResult = new QuizResult(quiz.getId(), user.getId(), result, seconds, LocalDateTime.now());
        if (!request.getParameter("practice").equals("true")){
            manager.insert(quizResult);
        }else {
            boolean found = false;
            for (UserAchievement userAchievement: user.getAchievements()) {
                if (userAchievement.getAchievement().getAchievementName().equals("Practice makes perfect")){
                     found = true;
                     break;
                }
            }
            if (!found) {
                UserAchievement achievement = new UserAchievement(user.getId(), new Achievement("Practice makes perfect", "gained for practicing quiz"));
                UserAchievementDao achievementDao = manager.getDao(DaoType.UserAchievement);
                achievementDao.insert(achievement);
                user.getAchievements().add(achievement);
            }
        }
        request.setAttribute("results", quizResults);
        request.setAttribute("userAnswers", questionAnswers);
        request.setAttribute("score", result);
        request.setAttribute("timespent", time);
        request.setAttribute("info", "You have successfully finished quiz.\n Score:"+result);
        request.getRequestDispatcher("quiz-result?quizId="+quiz.getId()).forward(request, response);
    }

    private int getSeconds(String time) {
        int hours = Integer.parseInt(time.substring(0,2));
        int minutes = Integer.parseInt(time.substring(3,5));
        int seconds = Integer.parseInt(time.substring(6));
        return 3600*hours + 60 * minutes + seconds;
    }
}
