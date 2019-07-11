<%@ page import="manager.DaoManager" %>
<%@ page import="datatypes.user.User" %>
<%@ page import="enums.DaoType" %>
<%@ page import="dao.UserDao" %>
<%@ page import="dao.AnnouncementDao" %>
<%@ page import="datatypes.messages.QuizChallenge" %>
<%@ page import="datatypes.user.Person" %>
<%@ page import="com.sun.xml.internal.ws.util.StringUtils" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="userDao" value="${applicationScope.manager.getDao(DaoType.User)}"/>
<c:set var="announcementDao" value="${applicationScope.manager.getDao(DaoType.Announcement)}"/>
<%
    DaoManager manager = (DaoManager) application.getAttribute("manager");
    User user = (User) session.getAttribute("user");
    UserDao userDao = manager.getDao(DaoType.User);
    AnnouncementDao announcementDao = manager.getDao(DaoType.Announcement);
%>

<script>
    $(document).ready(function () {
        toastr.options.timeOut = 10000;
        toastr.options.position = "toast-bottom-right";
        <c:forEach items="<%=announcementDao.findAll()%>" var="announcement">
        toastr.info("${announcement.announcementText}", "Announcement");
        </c:forEach>
        <c:if test="${sessionScope.user!= null}">
        toastr.options.timeOut = 10000;
        toastr.options.position = "toast-bottom-right";
        <c:forEach var="challenge" items="<%=user.getQuizChallenges()%>">
        <% QuizChallenge challenge = (QuizChallenge) pageContext.getAttribute("challenge");%>
        toastr.success("sent you a challenge" + '\n' + "<%=challenge.getTimestamp()%>", "<%=StringUtils.capitalize(userDao.findById(challenge.getSenderId()).getUserName())%>");
        </c:forEach>
        <c:forEach var="person" items="<%=user.getPendingFriendRequests()%>">
        <% Person person = (Person) pageContext.getAttribute("person");%>
        toastr.success("<%=person.getUserName()%> sent you a Friend Request");
        </c:forEach>
        </c:if>
        <c:if test="${requestScope.error !=null}">
        toastr.options.position = "toast-bottom-right";
        toastr.error("${requestScope.error}");
        ${requestScope.remove("error")}
        </c:if>
        <c:if test="${requestScope.info !=null}">
        toastr.options.position = "toast-bottom-right";
        toastr.success("${requestScope.info}");
        ${requestScope.remove("info")}
        </c:if>
        <c:if test="${requestScope.warn !=null}">
        toastr.options.position = "toast-bottom-right";

        toastr.warning("${requestScope.warn}");
        ${requestScope.remove("warn")}
        </c:if>
    });

</script>
