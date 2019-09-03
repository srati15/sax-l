<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${sessionScope.user != null}">
    <div class="ui red segment">
        <h2><i class="fas fa-rss"></i> Toast Feed</h2>
        <!-- Timeline -->
        <c:forEach var="toast" items="${applicationScope.toasts}">
            <div class="row">
                <ul class="timeline">
                    <li class="timeline-item bg-white rounded ml-3 p-4 shadow" >
                        <div class="timeline-arrow"></div>
                        <h2 class="h5 mb-0">${toast.title}</h2><span class="small text-gray"><i class="fas fa-clock"></i> ${DateTimeFormatter.ofPattern("HH:mm:ss MMM dd, yyyy").format(toast.dateCreated)}</span>
                            ${toast.toastText}
                    </li>
                </ul>
            </div>
            <!-- End -->
        </c:forEach>


    </div>

</c:if>