<%@ tag import="enums.QuestionType" %>
<%@ attribute name="question" required="true" type="datatypes.question.Question" %>
<%@ attribute name="answer" required="true" type="datatypes.answer.Answer" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    if (question.getQuestionType() == QuestionType.QuestionResponse) {
%>
    <div class="form-group">
        <label for="questionN<%=question.getId()%>" class="control-label">Question</label>
        <input type="text" class="form-control" id="questionN<%=question.getId()%>" name="questionN<%=question.getId()%>">
    </div>

<%
    }
%>