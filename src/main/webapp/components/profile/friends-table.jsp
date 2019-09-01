<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:choose>
    <c:when test="${sessionScope.friendsIds.size() > 0}">
        <table id="friendsTable" class="table table-striped table-bordered table-sm">
            <thead>
            <tr>
                <th>Username</th>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Achievements</th>
                <th>Completed Quizzes</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${sessionScope.friendsIds}" var="friend">
                <tr>
                    <td>

                        <a href="user-profile?userid=${friend.id}">${friend.userName}
                            <c:if test="${applicationScope.onlineUsers.containsValue(friend)}">
                                <span class="badge badge-success">Online</span>
                            </c:if>
                        </a>
                    </td>
                    <td>
                            ${friend.firstName}
                    </td>
                    <td>
                            ${friend.lastName}
                    </td>
                    <td>${friend.achievements.size()}</td>
                    <td>${friend.quizResults.size()}</td>
                    <td>
                        <!--  ************ challenge modal **********-->
                        <h:challenge
                                receiverId="${friend.id}"
                                receiverName="${friend.userName}"
                                actionServlet="ChallengeSenderServlet">
                        </h:challenge>

                        <form action="FriendRequestDeleteServlet" method="post" style="float: left;">
                            <button type="submit" class="btn btn-danger btn-sm">
                                <i class="fa fa-trash"></i> Unfriend
                            </button>
                            <input type="text" hidden name="receiverId" value="${friend.id}"/>
                            <input type="text" hidden name="callingPage" value="profile">
                        </form>
                    </td>

                </tr>
            </c:forEach>
            </tbody>
            <tfoot>
            <tr>
                <th>Username</th>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Achievements</th>
                <th>Completed Quizzes</th>
                <th>Action</th>
            </tr>
            </tfoot>
        </table>
    </c:when>
    <c:otherwise>
        Oops :( You have no friends
    </c:otherwise>
</c:choose>

