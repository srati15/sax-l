<%@ page import="dao.QuizDao" %>
<%@ page import="enums.DaoType" %>
<%@ page import="manager.DaoManager" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Comparator" %>
<%@ page import="java.util.List" %>
<%@ page import="datatypes.quiz.Quiz" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h" %>

<%
    DaoManager manager = (DaoManager) request.getServletContext().getAttribute("manager");
    QuizDao quizDao = manager.getDao(DaoType.Quiz);
    List<Quiz> quizzes = new ArrayList<>(quizDao.findAll());
    quizzes.sort(Comparator.comparingInt(Quiz::getTimesDone).reversed());
    if (quizzes.size() > 0) {
%>
<section class="mosh-team-area section_padding_100">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-12 col-md-10">
                <div class="section-heading text-center">
                    <h2>Top Quizzes</h2>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-12">
                <div class="mosh-team-slides owl-carousel">
                    <%
                        int num = 0;
                        for (Quiz quiz : quizzes) {
                            num++;
                            if (num > 5) break;
                    %>

                    <!-- Top quizzes -->
                    <div class="single-team-slide text-center border border-warning rounded-top">
                        <!-- Thumbnail -->
                        <div class="team-thumbnail">
                            <img src="<%=quiz.getQuizImageURL()%>" alt="">
                        </div>
                        <!-- Meta Info -->
                        <div class="team-meta-info">
                            <h4><%=quiz.getQuizName()%>
                            </h4>
                            <span>Date created: <%=DateTimeFormatter.ofPattern("MMM dd yyyy").format(quiz.getDateCreated().toLocalDate())%></span>
                            <span>Done <%=quiz.getTimesDone()%> times</span>
                        </div>
                        <h:start quiz="<%=quiz%>" buttonClass="btn mosh-btn mosh-btn-2" styled="false"/>
                    </div>
                    <!-- Single Team Slide -->

                    <%}%>
                </div>
            </div>
        </div>

    </div>
</section>

<%}%>


