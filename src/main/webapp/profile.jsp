<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="dao.UserDao" %>
<%@ page import="datatypes.user.Achievement" %>
<%@ page import="datatypes.user.User" %>
<%@ page import="datatypes.messages.Message" %>
<%@ page import="datatypes.messages.TextMessage" %>
<%@ page import="enums.DaoType" %>
<%@ page import="manager.DaoManager" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Comparator" %>
<%@ page import="java.util.List" %>
<%@ page import="datatypes.user.UserAchievement" %>
<%@ page import="dao.QuizDao" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="description" content="">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- The above 4 meta tags *must* come first in the head; any other head content must come *after* these tags -->

    <!-- Title -->
    <title>Sax-L - Quiz Website | Home</title>

    <!-- Favicon -->
    <link rel="icon" href="img/core-img/favicon.ico">

    <!-- Core Stylesheet -->
    <link href="style.css" rel="stylesheet">

    <link href="css/loginpanel.css" rel="stylesheet">

    <!-- Responsive CSS -->
    <link href="css/responsive.css" rel="stylesheet">

</head>

<body>

<%
    DaoManager manager = (DaoManager) request.getServletContext().getAttribute("manager");
    User user = (User) request.getSession().getAttribute("user");
    UserDao userDao = manager.getDao(DaoType.User);
    QuizDao quizDao = manager.getDao(DaoType.Quiz);
    pageContext.setAttribute("friendsIds", user.getFriends());
    pageContext.setAttribute("requestList", user.getPendingFriendRequests());
    pageContext.setAttribute("userDao", userDao);
    pageContext.setAttribute("quizDao", quizDao);
    pageContext.setAttribute("user", user);
%>
<!-- ***** Preloader Start ***** -->
<div id="preloader">
    <div class="mosh-preloader"></div>
</div>

<!-- ***** Header Area Start ***** -->
<header class="header_area clearfix">
    <jsp:include page="components/header.jsp"/>
</header>
<!-- ***** Header Area End ***** -->

<!-- ***** Breadcumb Area Start ***** -->
<div class="mosh-breadcumb-area" style="background-image: url(img/core-img/breadcumb.png);">
    <div class="container h-100">
        <div class="row h-100 align-items-center">
            <div class="col-12">
                <div class="bradcumbContent">
                    <h2><%=user.getUserName()%>'s Profile</h2>
                    <nav aria-label="breadcrumb">
                        <ol class="breadcrumb">
                            <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/">Home</a></li>
                            <li class="breadcrumb-item active" aria-current="page"><%=user.getUserName()%>'s profile
                            </li>
                        </ol>
                    </nav>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- ***** Breadcumb Area End ***** -->
<section class="mosh-aboutUs-area">
    <div class="container">
        <ul class="nav nav-tabs nav-fill">
            <li class="nav-item">
                <a class="nav-link active" data-toggle="tab" href="#requests">Friend Requests<span
                        class="badge badge-info">${requestList.size()}</span></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" data-toggle="tab" href="#people">Friends<span
                        class="badge badge-info">${friendsIds.size()}</span></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" data-toggle="tab" href="#messages">Messages</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" data-toggle="tab" href="#achievements">Achievements
                    <span class="badge badge-info">${user.achievements.size()}</span></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" data-toggle="tab" href="#challenges">Quiz Challenges
                    <span class="badge badge-info">${user.quizChallenges.size()}</span></a>
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
                    <c:forEach items="${requestList}" var="friendRequest">
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
                    <c:forEach items="${friendsIds}" var="friend">
                        <tr>
                            <td>
                                <a href="user-profile?userid=${friend.id}">${friend.userName}
                                </a>
                            </td>
                            <td>
                                    ${friend.firstName}
                            </td>
                            <td>
                                    ${friend.lastName}
                            </td>
                            <td>0</td>
                            <td>0</td>
                            <td>
                                <!--  ************ challenge modal **********-->
                                <h:challenge
                                        receiverId="${friend.id}"
                                        receiverName="${friend.userName}"
                                        actionServlet="ChallengeSenderServlet">
                                </h:challenge>

                                <form action="FriendRequestDeleteServlet" method="post">
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
                <%
                    List<TextMessage> textMessages = new ArrayList<>();
                    for (List<TextMessage> messages : user.getTextMessages().values()) {
                        for (TextMessage message : messages) {
                            if (message.getSenderId() != user.getId()) textMessages.add(message);
                        }
                    }
                    textMessages.sort(Comparator.comparing(Message::getTimestamp).reversed());
                    for (int i = 0; i < Math.min(textMessages.size(), 5); i++) {
                        User sender = userDao.findById(textMessages.get(i).getSenderId());
                %>
                <ul class="list-group">
                    <li class="list-group-item"><a
                            href="user-profile?userid=<%=sender.getId()%>"><%=userDao.findById(textMessages.get(i).getSenderId()).getUserName()%>
                    </a> : <%=textMessages.get(i).getTextMessage()%>
                    </li>
                </ul>
                <%}%>
            </div>

            <div class="tab-pane fade" id="achievements">
                <ul>
                    <%for (UserAchievement achievement : user.getAchievements()) {%>
                    <li><%=achievement.getAchievement().getAchievementName()%>
                    </li>
                    <%}%>
                </ul>
            </div>

            <div class="tab-pane fade" id="challenges">
                <table id="challengesTable" class="table table-striped table-bordered table-sm">
                    <thead>
                    <tr>
                        <th>Challenger</th>
                        <th>Quiz</th>
                        <th>Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="challenge" items="${sessionScope.user.quizChallenges}">
                        <td>
                                ${userDao.findById(challenge.senderId).userName}
                        </td>
                        <td>
                                ${quizDao.findById(challenge.quizId).quizName}
                        </td>
                        <td>
                            <form action="AcceptChallengeServlet" method="post">
                                <input type="hidden" name="challengeId" value="${challenge.id}">
                                <button type="submit" class="btn btn-info btn-sm" style="float:left">
                                    <i class="fa fa-hourglass-start"></i> Start
                                </button>
                            </form>

                        </td>
                    </c:forEach>
                    </tbody>
                    <tfoot>
                    <tr>
                        <th>Challenger</th>
                        <th>Quiz</th>
                        <th>Action</th>
                    </tr>
                    </tfoot>
                </table>

            </div>

            <div class="tab-pane fade" id="edit">
                <form action="UpdateUserServlet" method="post" id="editForm">
                    <center>
                        <label>
                            <input type="text" disabled value="<%=user.getUserName()%>" name="username" required
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
                            <input type="email" disabled placeholder="<%=user.getMail()%>" name="mail" required>
                        </label>
                        <br>

                        <label>
                            <input type="text" value="<%=user.getFirstName()%>" name="firstname">
                        </label>
                        <br>

                        <label>
                            <input type="text" value="<%=user.getLastName()%>" name="lastname">
                        </label>
                        <br>
                        <button type="submit" class="btn btn-outline-info btn-sm"
                                style="display: block; margin: 0 auto;">
                            <i class="fa fa-sign-in"></i> Update
                        </button>
                        <br>

                        <input type="text" hidden name="hiddenId" value="<%=user.getId()%>">
                        <input type="text" hidden name="calledFrom" value="profile">
                    </center>
                </form>
            </div>
        </div>
    </div>
</section>

<!-- ***** Footer Area Start ***** -->
<footer class="footer-area clearfix">
    <jsp:include page="components/footer.jsp"/>
</footer>
<!-- ***** Footer Area End ***** -->

<!-- jQuery-2.2.4 js -->
<script src="js/jquery-2.2.4.min.js"></script>
<!-- Popper js -->
<script src="js/popper.min.js"></script>
<!-- Bootstrap js -->
<script src="js/bootstrap.min.js"></script>
<!-- All Plugins js -->
<script src="js/plugins.js"></script>
<!-- Active js -->
<script src="js/active.js"></script>

<script type="text/javascript" src="js/datatables.min.js"></script>
<script>
    $(document).ready(function () {
        $('#friendRequestTable').DataTable();
        $('.dataTables_length').addClass('bs-select');
    });
    $(document).ready(function () {
        $('#friendsTable').DataTable();
        $('.dataTables_length').addClass('bs-select');
    });

</script>
</body>

</html>