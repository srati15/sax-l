<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page import="java.time.format.DateTimeFormatter" %>

<c:if test="${sessionScope.topQuizzes != null}">
    <section class="mosh-clients-area section_padding_100 clearfix">
        <div class="container border border-info rounded">
            <h1 class="text-center">Top Quizzes</h1>
            <div class="card-deck">
                <c:forEach var="quiz" items="${sessionScope.topQuizzes}">
                    <div class="card border-dark mb-3" style="max-width: 20%; float: left;">
                        <img class="card-img-top" src="${quiz.quizImageURL}" alt="${quiz.quizName}">
                        <div class="card-header">
                            <h5 class="card-title">${quiz.quizName}
                            </h5>
                        </div>
                        <div class="card-body">
                            <h6 class="card-subtitle mb-2 text-dark">Date
                                created: ${DateTimeFormatter.ofPattern("MMM dd yyyy").format(quiz.dateCreated.toLocalDate())}
                            </h6>
                            <h6 class="card-subtitle mb-2 text-dark">Times done: ${quiz.timesDone}
                            </h6>
                        </div>
                        <div class="card-footer bg-transparent border-success">
                            <a href="start-quiz?quizId=${quiz.id}">
                                <button type="submit" class="btn btn-info btn-sm">
                                    <i class="fa fa-sign-in"></i> Start
                                </button>
                            </a>
                        </div>
                    </div>

                </c:forEach>
            </div>
        </div>
    </section>
</c:if>