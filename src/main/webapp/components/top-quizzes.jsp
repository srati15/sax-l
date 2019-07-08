<%@ page import="dao.QuizDao" %>
<%@ page import="enums.DaoType" %>
<%@ page import="manager.DaoManager" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Comparator" %>
<%@ page import="java.util.List" %>
<%@ page import="datatypes.quiz.Quiz" %>
<%
    DaoManager manager = (DaoManager) request.getServletContext().getAttribute("manager");
    QuizDao quizDao = manager.getDao(DaoType.Quiz);
    List<Quiz> quizzes = new ArrayList<>(quizDao.findAll());
    quizzes.sort(Comparator.comparingInt(Quiz::getTimesDone).reversed());
    if (quizzes.size() > 0) {
%>
<section class="mosh-clients-area section_padding_100 clearfix">
    <div class="container border border-info rounded">
        <h1 class="text-center">Top Quizzes</h1>
        <div class="card-deck">
            <%
                int num = 0;
                for (Quiz quiz : quizzes) {
                    num++;
                    if (num > 5) break;
            %>
            <div class="card border-dark mb-3" style="max-width: 20%; float: left;">
                <img class="card-img-top" src="<%=quiz.getQuizImageURL()%>" alt="<%=quiz.getQuizName()%>">
                <div class="card-header">
                    <h5 class="card-title"><%=quiz.getQuizName()%>
                    </h5>
                </div>
                <div class="card-body">
                    <h6 class="card-subtitle mb-2 text-dark">Date
                        created: <%=DateTimeFormatter.ofPattern("MMM dd yyyy").format(quiz.getDateCreated().toLocalDate())%>
                    </h6>
                    <h6 class="card-subtitle mb-2 text-dark">Times done: <%=quiz.getTimesDone()%>
                    </h6>
                </div>
                <div class="card-footer bg-transparent border-success">
                    <a href="start-quiz?quizId=<%=quiz.getId()%>">
                        <button type="submit" class="btn btn-info btn-sm">
                            <i class="fa fa-sign-in"></i> Start
                        </button>
                    </a>
                </div>
            </div>
            <%}%>
        </div>
    </div>
</section>
<%}%>
