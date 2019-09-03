<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${sessionScope.info !=null}">
    <div class="alert alert-success alert-dismissible fade show" role="alert">
        ${sessionScope.info}
        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
            <span aria-hidden="true">&times;</span>
        </button>
    </div>
    <c:remove var="info" scope="session" />
</c:if>
<c:if test="${applicationScope.announcements !=null}">
    <c:forEach var="announcement" items="${applicationScope.announcements}">
        <div class="alert alert-primary alert-dismissible fade show" role="alert">
                <a href="${announcement.hyperLink}">${announcement.announcementText}</a>
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:forEach>
</c:if>
<c:if test="${sessionScope.error !=null}">
    <div class="alert alert-danger alert-dismissible fade show" role="alert">
            ${sessionScope.error}
        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
            <span aria-hidden="true">&times;</span>
        </button>
    </div>
    <c:remove var="error" scope="session" />
</c:if>
<c:if test="${sessionScope.warn !=null}">
    <div class="alert alert-warning alert-dismissible fade show" role="alert">
            ${sessionScope.warn}
        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
            <span aria-hidden="true">&times;</span>
        </button>
    </div>
    <c:remove var="warn" scope="session" />
</c:if>
