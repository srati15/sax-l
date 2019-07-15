<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="quiz" required="true" type="datatypes.quiz.Quiz" %>
<%@ attribute name="buttonClass" required="true" type="java.lang.String" %>
<%@ attribute name="styled" required="true" type="java.lang.Boolean" %>

<c:choose>
    <c:when test="${quiz.onePage}">
        <a href="start-quiz?quizId=${quiz.id}">
            <button type="button" class="${buttonClass}" <c:if test="${styled}">style="float:left"</c:if>>
                <i class="fa fa-hourglass-start"></i> Start
            </button>
        </a>
    </c:when>
    <c:otherwise>
        <a href="start-quiz?quizId=${quiz.id}&questionId=1">
            <button type="button" class="${buttonClass}"  <c:if test="${styled}">style="float:left"</c:if>>
                <i class="fa fa-hourglass-start"></i> Start
            </button>
        </a>
    </c:otherwise>

</c:choose>
<c:if test="${quiz.allowedPracticemode}">
    <a href="start-quiz?quizId=${quiz.id}&practice=true">
        <button type="button" class="${buttonClass}"  <c:if test="${styled}">style="float:left"</c:if>>
            <i class="fa fa-hourglass-start"></i> Practice
        </button>
        <input hidden name="practice" value="true">
    </a>
</c:if>