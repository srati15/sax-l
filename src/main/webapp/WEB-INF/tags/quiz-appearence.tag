<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag import="java.time.format.DateTimeFormatter" %>
<%@ tag import="java.util.stream.Collectors" %>
<%@ attribute name="quizzes" required="true" type="java.util.List" %>
<%@ attribute name="title" required="true" type="java.lang.String" %>
<%@ attribute name="quizResultDao" required="true" type="dao.QuizResultDao" %>
<c:if test="${quizzes.size() > 0}">
    <section class="mosh-clients-area section_padding_100 clearfix">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-12 col-md-10">
                    <div class="section-heading text-center">
                        <h2>${title}</h2>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-12">
                    <div class="mosh-team-slides owl-carousel">
                        <c:forEach var="quiz" items="${quizzes}">
                            <div class="single-team-slide text-center border border-warning rounded-top">
                                <!-- Thumbnail -->
                                <div class="team-thumbnail">
                                    <img src="${quiz.quizImageURL}" alt="">
                                </div>
                                <!-- Meta Info -->
                                <div class="team-meta-info">
                                    <h4>${quiz.quizName}
                                    </h4>
                                    <span>Date created: ${DateTimeFormatter.ofPattern("MMM dd yyyy").format(quiz.getDateCreated().toLocalDate())}</span>
                                    <span>Results</span>
                                    <c:forEach var="result" items="${quizResultDao.findAll().stream().filter(s->s.getQuizId()==quiz.getId()).collect(Collectors.toList())}">
                                        <div class="card">
                                            <div class="card-body">
                                                <span>Score: <b>${result.score}</b></span>
                                                <span>Time: <b>${result.timeSpent}</b> Seconds </span>
                                            </div>
                                        </div>
                                    </c:forEach>

                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
    </section>

</c:if>

