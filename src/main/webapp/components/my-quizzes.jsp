<%@ page import="datatypes.quiz.Quiz" %>
<%@ page import="datatypes.quiz.QuizResult" %>
<%@ page import="datatypes.user.User" %>
<%@ page import="enums.DaoType" %>
<%@ page import="manager.DaoManager" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="dao.QuizResultDao" %>
<%@ page import="java.util.stream.Collectors" %>

<%
    DaoManager manager = (DaoManager) request.getServletContext().getAttribute("manager");
    User user = (User) request.getSession().getAttribute("user");
    QuizResultDao quizResultDao = manager.getDao(DaoType.QuizResult);
    if (user.getQuizzes().size() > 0) {
%>
<div class="ui blue segment">

    <div class="container">
        <div class="huge ui blue ribbon label">
            My Created Quizzes
        </div>

        <div class="row">
            <div class="col-12">
                <div class="mosh-team-slides owl-carousel">
                    <%for (Quiz quiz : user.getQuizzes()) {%>
                    <!-- My Quizzes -->
                    <div class="single-team-slide text-center border border-warning rounded-top">
                        <!-- Thumbnail -->
                        <div class="team-thumbnail">
                            <img src="<%=quiz.getQuizImageURL()%>" alt="">
                        </div>
                        <!-- Meta Info -->
                        <div class="team-meta-info">
                            <h4><a href="quiz-details?quizId=<%=quiz.getId()%>"><%=quiz.getQuizName()%>
                            </a>
                            </h4>
                            <span>Date created: <%=DateTimeFormatter.ofPattern("MMM dd yyyy").format(quiz.getDateCreated().toLocalDate())%></span>
                            <span>Results</span>
                            <%for (QuizResult result : quizResultDao.findAll().stream().filter(s -> s.getQuizId() == quiz.getId()).collect(Collectors.toList())) {%>
                            <div class="card">
                                <div class="card-body">
                                    <span>Score: <b><%=result.getScore()%></b></span>
                                    <span>Time: <b><%=result.getTimeSpent() / 60%></b> Minutes <b><%=result.getTimeSpent() % 60%></b> Seconds </span>
                                </div>
                            </div>
                            <%}%>
                        </div>
                        <h:start quiz="<%=quiz%>" buttonClass="btn mosh-btn mosh-btn-2" styled="false"/>
                    </div>
                    <!-- My Quizzes -->
                    <%}%>
                </div>
            </div>
        </div>
    </div>
</div>
<%}%>
