
<%@ attribute name="userDao" required="true" type="dao.UserDao" %>
<%@ attribute name="title" required="true" type="java.lang.String" %>
<%@ attribute name="quizResults" required="true" type="java.util.List" %>
<h2 class="text-center">Top Performers Today</h2>
<div class="card-deck border border-warning rounded-top section_padding_50">
    <c:forEach var="quizResult" items="${quizResults}">
        <div class="team-meta-info">
            <div class="card" style="width: 18rem;">
                <h5 class="card-title">User: <b><a
                        href="user-profile?userid=${userDao.findById(quizResult.userId).id}">${userDao.findById(quizResult.userId).userName}</a></b>
                </h5>
                <div class="card-body">
                    <p class="card-text">Score: <b>${quizResult.score}</b> Time:
                        <b>${quizResult.timeSpent}</b> Seconds</p>
                </div>
            </div>
        </div>
    </c:forEach>
</div>