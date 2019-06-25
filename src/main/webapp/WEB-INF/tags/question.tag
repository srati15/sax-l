<%@ tag import="enums.QuestionType" %>
<%@ tag import="java.util.Arrays" %>
<%@ tag import="java.util.Collections" %>
<%@ attribute name="question" required="true" type="datatypes.question.Question" %>
<%@ attribute name="answer" required="true" type="datatypes.answer.Answer" %>
<%@ attribute name="questionNumber" required="true" type="java.lang.Integer" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:choose>
    <c:when test="${question.questionType == QuestionType.QuestionResponse}">
        <div class="form-group border border-info fa-border border rounded">
            <label for="questionN${question.id}" class="control-label">Question N<%=questionNumber%>
            </label>
            <p class="mb-0 d-block p-2 bg-dark text-white rounded">${question.question}
            <div class="input-group">
                <span class="input-group-addon bg-info">Answer<i class="fa fa-arrow-right"></i></span>
                <input type="text" class="form-control bg-light" id="questionN${question.id}"
                       name="questionN${question.id}" placeholder="...">
            </div>
        </div>
    </c:when>
    <c:when test="${question.questionType == QuestionType.MultipleChoise}">
        <div class="form-group border border-info fa-border border rounded">
            <label for="questionN${question.id}" class="control-label">Question N<%=questionNumber%>
            </label>
            <p class="mb-0 d-block p-2 bg-dark text-white rounded">${question.question}
            <div class="input-group">
                <span class="input-group-addon bg-info">Answer<i class="fa fa-arrow-right"></i></span>
                <c:set var="possibleAnswers" value="${Collections.shuffle(Arrays.asList(answer.answer.split(',')))}"/>
                <c:forEach items="${possibleAnswers}" var="possibleAnswer">
                    <div class="radio">
                        <label><input type="radio" name="questionN${question.id}" value="${possibleAnswer}">${possibleAnswer}</label>
                    </div>
                </c:forEach>
            </div>
        </div>
    </c:when>
</c:choose>
