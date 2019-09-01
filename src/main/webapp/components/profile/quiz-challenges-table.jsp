<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:choose>
    <c:when test="${sessionScope.user.quizChallenges.size() > 0}">
        <table id="challengesTable" class="table table-striped table-bordered table-sm">
            <thead>
            <tr>
                <th>Challenger</th>
                <th>Quiz</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="challenge" items="${sessionScope.user.quizChallenges}">
                <td>
                    <a href="user-profile?userid=${challenge.senderId}">${requestScope.userDao.findById(challenge.senderId).userName}</a>
                </td>
                <td>
                        ${requestScope.quizDao.findById(challenge.quizId).quizName}
                </td>
                <td>
                    <form action="AcceptChallengeServlet" method="post">
                        <input type="hidden" name="challengeId" value="${challenge.id}">
                        <button type="submit" class="btn btn-info btn-sm" style="float:left">
                            <i class="fa fa-hourglass-start"></i> Start
                        </button>
                    </form>
                    <form action="RejectChallengeServlet" method="post">
                        <input type="hidden" name="challengeId" value="${challenge.id}">
                        <button type="submit" class="btn btn-danger btn-sm">
                            <i class="fa fa-trash"></i> Reject
                        </button>
                    </form>

                </td>
            </c:forEach>
            </tbody>
            <tfoot>
            <tr>
                <th>Challenger</th>
                <th>Quiz</th>
                <th>Action</th>
            </tr>
            </tfoot>
        </table>
    </c:when>
    <c:otherwise>
        You have no pending Quiz Challenges
    </c:otherwise>
</c:choose>

