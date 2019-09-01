<%@ page import="dao.AnnouncementDao" %>
<%@ page import="dao.UserDao" %>
<%@ page import="datatypes.messages.QuizChallenge" %>
<%@ page import="datatypes.user.Person" %>
<%@ page import="datatypes.user.User" %>
<%@ page import="enums.DaoType" %>
<%@ page import="manager.DaoManager" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    DaoManager manager = (DaoManager) application.getAttribute("manager");
    User user = (User) session.getAttribute("user");
    UserDao userDao = manager.getDao(DaoType.User);
    AnnouncementDao announcementDao = manager.getDao(DaoType.Announcement);
    pageContext.setAttribute("announcementDao", announcementDao);
    pageContext.setAttribute("userDao", userDao);
%>
<c:forEach items="<%=announcementDao.findAll().stream().filter(s->s.isActive()).collect(Collectors.toList())%>" var="announcement">
    <div class="toast" role="alert" aria-live="assertive" aria-atomic="true">
        <div class="toast-header">
            <strong class="mr-auto">Notification</strong>
            <button type="button" class="ml-2 mb-1 close" data-dismiss="toast" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
        <div class="toast-body">
                ${announcement.announcementText}
        </div>
    </div>
</c:forEach>