<%@ page import="dao.QuizDao" %>
<%@ page import="datatypes.quiz.Quiz" %>
<%@ page import="datatypes.quiz.QuizResult" %>
<%@ page import="datatypes.user.User" %>
<%@ page import="enums.DaoType" %>
<%@ page import="manager.DaoManager" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.util.*" %>
<%
    DaoManager manager = (DaoManager) request.getServletContext().getAttribute("manager");
    User user = (User) request.getSession().getAttribute("user");
    QuizDao quizDao = manager.getDao(DaoType.Quiz);
    Map<Quiz, List<QuizResult>> quizMap = new HashMap<>();
    user.getQuizResults().forEach(quizResult -> {
        Quiz current = quizDao.findById(quizResult.getQuizId());
        if (!quizMap.containsKey(current)) quizMap.put(current, new ArrayList<>(Arrays.asList(quizResult)));
        else quizMap.get(current).add(quizResult);
    });
    if (quizMap.size() > 0) {
%>
<section class="mosh-clients-area section_padding_100 clearfix">
    <div class="container border border-info rounded">
        <h1 class="text-center">Last Quiz Activities</h1>
        <div class="card-deck col-auto">
            <%
                for (Quiz quiz : quizMap.keySet()) {
            %>
            <div class="card border-dark mb-3 col-md-15 col-sm-3" style="width: 18rem; float: left;">
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
                    <p class="text-info">Results</p>
                    <%for (QuizResult result : quizMap.get(quiz)) {%>
                    <p class="text-dark">Score: <%=result.getScore()%>. Time: <%=result.getTimeSpent()/60%> Minutes <%=result.getTimeSpent()%60%> Seconds </p>
                    <% }%>
                </div>
            </div>
            <%}%>
        </div>
    </div>
</section>
<%}%>
