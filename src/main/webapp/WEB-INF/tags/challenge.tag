<%@ tag import="dao.QuizDao" %>
<%@ tag import="datatypes.quiz.Quiz" %>
<%@ tag import="enums.DaoType" %>
<%@ tag import="manager.DaoManager" %>
<%@ tag import="java.time.format.DateTimeFormatter" %>
<%@ tag import="java.util.ArrayList" %>
<%@ tag import="java.util.Comparator" %>
<%@ tag import="java.util.List" %>
<%@ attribute name="actionServlet" required="true" %>
<%@ attribute name="receiverName" required="true" %>
<%@ attribute name="receiverId" required="true" type="java.lang.Integer" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="target" value="chooseQuiz${receiverId}"/>


<%  DaoManager manager = (DaoManager) request.getServletContext().getAttribute("manager");
    QuizDao quizDao = manager.getDao(DaoType.Quiz);
    List<Quiz> quizzes = new ArrayList<>(quizDao.findAll());
    quizzes.sort(Comparator.comparingInt(Quiz::getTimesDone).reversed());
%>
<button type="button" class="btn btn-primary btn-sm" data-toggle="modal" data-target="#${target}">
    Challenge
</button>
<div class="modal fade" id="${target}" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLongTitle">Challenge ${receiverName} </h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>

            <div class="modal-body">

                <div class="container border border-info rounded">
                    <div class="row col-lg-auto">
                        <%
                            for (Quiz quiz : quizzes) {
                        %>
                        <div class="card border-dark mx-1 mb-3" style="max-width: 30%; float: left;">
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
                                <form action="${actionServlet}" method="post">
                                    <input type="text" hidden name="quizId" value="<%=quiz.getId()%>"/>
                                    <input type="text" hidden name="receiverId" value="${receiverId}"/>

                                    <a href="start-quiz?quizId=<%=quiz.getId()%>">
                                        <button type="submit" class="btn btn-info btn-sm">
                                            <i class="fa fa-sign-in"></i> Start
                                        </button>
                                    </a>
                                </form>
                            </div>
                        </div>
                        <%}%>
                    </div>
                </div>
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>