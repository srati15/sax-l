<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<c:choose>
    <c:when test="${sessionScope.activities.size() > 0}">
        <table id="historyTable" class="table table-striped table-bordered table-sm">
            <thead>
            <tr>
                <th>#</th>
                <th>Action</th>
                <th>Date</th>
            </tr>
            </thead>
            <tbody>
            <c:set var="i" value="0" scope="page"/>
            <c:forEach items="${sessionScope.activities}" var="activity">
                <tr>
                    <td>${i+1}</td>
                    <td>
                            ${activity.activityName}
                    </td>
                    <td>
                            ${DateTimeFormatter.ofPattern("HH:mm:ss MMM dd, yyyy").format(activity.dateTime)}
                    </td>
                </tr>
                <c:set var="i" value="${i + 1}" scope="page"/>
            </c:forEach>
            </tbody>
            <tfoot>
            <tr>
                <th>#</th>
                <th>Action</th>
                <th>Date</th>
            </tr>
            </tfoot>
        </table>
    </c:when>
    <c:otherwise>
        You have no recent activities
    </c:otherwise>
</c:choose>

