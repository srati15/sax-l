<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <title>Sax-L Quiz Website</title>
    <link rel="stylesheet" href="css/font-awesome.min.css">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/sidebar.css">
    <link rel="stylesheet" href="css/timeline.css">
    <link rel="stylesheet" href="css/segment.css">
    <link rel="stylesheet" href="css/label.min.css">
    <link rel="stylesheet" href="css/button.min.css">

    <link rel="stylesheet" type="text/css" href="css/dataTables.bootstrap4.min.css"/>

</head>

<body>

<div class="wrapper">
    <!-- Sidebar Holder -->
    <jsp:include page="components/sidebar.jsp"/>
    <!-- Page Content Holder -->
    <div id="content">
        <jsp:include page="components/topbar.jsp"/>
        <div class="ui segment">
            <div class="container">
                <ul class="nav nav-tabs nav-fill">
                    <li class="nav-item">
                        <a class="nav-link active" data-toggle="tab" href="#requests">Friend Requests<span
                                class="badge badge-info">${requestScope.requestList.size()}</span></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" data-toggle="tab" href="#people">Friends<span
                                class="badge badge-info">${requestScope.friendsIds.size()}</span></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" data-toggle="tab" href="#messages">Messages</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" data-toggle="tab" href="#achievements">Achievements
                            <span class="badge badge-info">${sessionScope.user.achievements.size()}</span></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" data-toggle="tab" href="#challenges">Quiz Challenges
                            <span class="badge badge-info">${sessionScope.user.quizChallenges.size()}</span></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" data-toggle="tab" href="#history">History</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" data-toggle="tab" href="#edit">Edit Profile</a>
                    </li>
                </ul>
                <div id="myTabContent" class="tab-content">

                    <div class="tab-pane fade active show" id="requests">
                        <table id="friendRequestTable" class="table table-striped table-bordered table-sm">
                            <thead>
                            <tr>
                                <th>Friend Request</th>
                                <th>Action</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${requestScope.requestList}" var="friendRequest">
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
                    </div>
                    <div class="tab-pane fade" id="people">
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
                            <c:forEach items="${requestScope.friendsIds}" var="friend">
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
                    </div>
                    <div class="tab-pane fade" id="messages">
                        <c:forEach var="textMessage" items="${requestScope.textMessages}">
                            <c:set var="sender" value="${requestScope.userDao.findById(textMessage.senderId)}"/>
                            <ul class="list-group">
                                <li class="list-group-item">
                                    <div class="card">
                                        <h5 class="card-header"><a
                                                href="user-profile?userid=${sender.id}">${sender.userName}
                                        </a></h5>
                                        <div class="card-body">
                                            <h6 class="lead" style=" float: left">
                                                    ${textMessage.textMessage}
                                            </h6>
                                            <h6 class="card-text" style="float: right">${textMessage.timestamp}
                                            </h6>
                                        </div>
                                    </div>
                                </li>
                            </ul>

                        </c:forEach>
                    </div>

                    <div class="tab-pane fade" id="achievements">
                        <ul class="list-group">
                            <p class="text-primary"></p>
                            <c:forEach var="achievement" items="${sessionScope.user.achievements}">
                                <li class="list-group-item">
                                    <button type="button" class="btn btn-secondary" data-toggle="tooltip" data-html="true"
                                            data-placement="top"
                                            title="${achievement.achievement.achievementName}, <p class='text-success'>${achievement.achievement.achievementCriteria}</p>">
                                        <img src="img/core-img/medal.png"
                                             alt="${achievement.achievement.achievementName}">
                                    </button>
                                </li>

                            </c:forEach>

                        </ul>
                    </div>


                    <div class="tab-pane fade" id="edit">
                        <form action="UpdateUserServlet" method="post" id="editForm">
                            <center>
                                <label>
                                    <input type="text" disabled value="${sessionScope.user.userName}" name="username"
                                           required
                                           minlength="4">
                                </label>
                                <br>
                                <label>
                                    <input type="password" placeholder="Password" name="password" required minlength="4">
                                </label>
                                <br>
                                <label>
                                    <input type="password" placeholder="Confirm password" name="confirmpassword" required
                                           minlength="4">
                                </label>
                                <br>

                                <label>
                                    <input type="email" disabled placeholder="${sessionScope.user.mail}" name="mail"
                                           required>
                                </label>
                                <br>

                                <label>
                                    <input type="text" value="${sessionScope.user.firstName}" name="firstname">
                                </label>
                                <br>

                                <label>
                                    <input type="text" value="${sessionScope.user.lastName}" name="lastname">
                                </label>
                                <br>
                                <button type="submit" class="btn btn-outline-info btn-sm"
                                        style="display: block; margin: 0 auto;">
                                    <i class="fa fa-sign-in"></i> Update
                                </button>
                                <br>

                                <input type="text" hidden name="hiddenId" value="${sessionScope.user.id}">
                                <input type="text" hidden name="calledFrom" value="profile">
                            </center>
                        </form>
                    </div>
                    <div class="tab-pane fade" id="history">
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
                            <c:forEach items="${requestScope.activities}" var="activity">
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

                    </div>

                </div>
            </div>
        </div>
        <jsp:include page="components/notifications.jsp"/>

    </div>
</div>

<script src="js/solid.js"></script>
<script src="js/fontawesome.js"></script>

<script src="js/jquery.min.js"></script>
<!-- Popper.JS -->
<script src="js/popper.min.js"></script>
<!-- Bootstrap JS -->
<script src="js/bootstrap.min.js"></script>
<script src="js/bootstrap.bundle.min.js"></script>
<script src="js/jquery.dataTables.min.js"></script>

<script src="js/dataTables.bootstrap4.min.js"></script>

<script type="text/javascript">
    $(document).ready(function () {
        $('#sidebarCollapse').on('click', function () {
            $('#sidebar').toggleClass('active');
            $(this).toggleClass('active');
        });
        $('.table').DataTable();
        $('.modal').appendTo("body");
        $('.toast').appendTo("body");
    });
</script>

<script src="//cdnjs.cloudflare.com/ajax/libs/jquery-form-validator/2.3.26/jquery.form-validator.min.js"></script>

</body>

</html>