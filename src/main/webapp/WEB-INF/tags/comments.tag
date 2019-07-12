<%@ tag import="java.time.format.DateTimeFormatter" %>
<%@ attribute name="userDao" required="true" type="dao.UserDao" %>
<%@ attribute name="comment" required="true" type="datatypes.quiz.Comment"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="comment">
    <div class="ui segment" id="collapseSegment">
        <a class="avatar">
            <img src="${pageContext.request.contextPath}/img/core-img/avatar.png">
        </a>
        <div class="content">
            <a class="author" href="user-profile?userid=${userDao.findById(comment.userId).id}">${userDao.findById(comment.userId).userName}</a>
            <div class="metadata">
                <span class="date"><%=DateTimeFormatter.ofPattern("EEEE, MMM dd, HH:mm").format(comment.getCommentDate())%></span>
            </div>
            <div class="text">
                ${comment.commentText}
            </div>
            <div class="actions">

                <c:if test="${sessionScope.user.id == comment.userId}">
                    <form action="DeleteCommentServlet" method="post">
                        <input hidden name="deleteCommentId" value="${comment.id}"/>
                        <input hidden name="quizId" value="${comment.quizId}"/>
                        <button class="mini ui red button reply" type="submit">Delete</button>
                    </form>
                </c:if>
            </div>
        </div>
    </div>

</div>