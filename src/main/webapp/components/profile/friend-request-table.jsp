<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:choose>
    <c:when test="${sessionScope.user.pendingFriendRequests.size() > 0}">
        <table id="friendRequestTable" class="table table-striped table-bordered table-sm">
            <thead>
            <tr>
                <th>Friend Request</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${sessionScope.user.pendingFriendRequests}" var="friendRequest">
                <tr>
                    <td>
                        <a href="user-profile?userid=${friendRequest.id}">
                                ${friendRequest.userName} sent you a friend request
                        </a>
                    </td>
                    <td>
                        <form action="FriendRequestAcceptServlet" method="post" style="float:left">
                            <button type="submit" class="btn btn-success btn-sm">
                                Accept
                            </button>
                            <input type="text" hidden name="receiverId" value="${friendRequest.id}"/>
                        </form>
                        <form action="FriendRequestDeleteServlet" method="post" style="float:left">
                            <button type="submit" class="btn btn-warning btn-sm">
                                Reject
                            </button>
                            <input type="text" hidden name="receiverId" value="${friendRequest.id}"/>
                            <input type="text" hidden name="callingPage" value="profile">
                        </form>
                        <br>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
            <tfoot>
            <tr>
                <th>Friend Request</th>
                <th>Action</th>
            </tr>
            </tfoot>
        </table>
    </c:when>
    <c:otherwise>
        You have no pending Friend Requests
    </c:otherwise>
</c:choose>

