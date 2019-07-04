<%@ page import="manager.DaoManager" %>
<%@ page import="datatypes.Quiz" %>
<%@ page import="java.util.TreeSet" %>
<%@ page import="java.util.Comparator" %>
<%@ page import="dao.QuizDao" %>
<%@ page import="enums.DaoType" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%
    DaoManager manager = (DaoManager) request.getServletContext().getAttribute("manager");
    QuizDao quizDao = manager.getDao(DaoType.Quiz);
    List<Quiz> quizzes = new ArrayList<>();
    quizzes.addAll(quizDao.findAll());
    quizzes.sort(Comparator.comparingInt(Quiz::getTimesDone).reversed());
    pageContext.setAttribute("topQuizzes", quizzes);
%>
<section class="mosh-clients-area section_padding_100 clearfix">
    <div class="container border border-info rounded" >
        <h1 class="text-center">Top Quizzes</h1>
        <div class="card-deck">
        <%
            for (Quiz quiz : quizzes) {
        %>
            <div class="col-sm-6">
                <div class="card border-dark mb-3" style="width: 18rem; float: left;">
                    <div class="card-header">
                        <h5 class="card-title"><%=quiz.getQuizName()%></h5>
                    </div>
                    <div class="card-body">
                        <h6 class="card-subtitle mb-2 text-dark">Date created: <%=DateTimeFormatter.ofPattern("MMM dd yyyy").format(quiz.getDateCreated().toLocalDateTime().toLocalDate())%></h6>
                        <h6 class="card-subtitle mb-2 text-dark">Times done: <%=quiz.getTimesDone()%></h6>
                    </div>
                    <div class="card-footer bg-transparent border-success">
                        <a href="start-quiz?quizId=<%=quiz.getId()%>">
                            <button type="submit" class="btn btn-info btn-sm">
                                <i class="fa fa-sign-in"></i> Start
                            </button>
                        </a>
                    </div>
                </div>
            </div>
        <%
            }
        %>
        </div>
    </div>
</section>

