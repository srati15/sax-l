<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="dao.FriendRequestDao" %>
<%@ page import="dao.UserDao" %>
<%@ page import="datatypes.User" %>
<%@ page import="enums.DaoType" %>
<%@ page import="manager.DaoManager" %>
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

    <!-- Responsive CSS -->
    <link href="css/responsive.css" rel="stylesheet">

</head>

<body>

<%
    DaoManager manager = (DaoManager) request.getServletContext().getAttribute("manager");
    User user = (User) request.getSession().getAttribute("user");
    UserDao userDao = manager.getDao(DaoType.User);
    FriendRequestDao requestDao = manager.getDao(DaoType.FriendRequest);
    pageContext.setAttribute("friendsIds", requestDao.getFriendsIdsFor(user.getId()));
    pageContext.setAttribute("requestList", requestDao.getPendingRequestsFor(user.getId()));
    pageContext.setAttribute("userDao", userDao);
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
                            <li class="breadcrumb-item"><a href="/">Home</a></li>
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
                <a class="nav-link" data-toggle="tab" href="#friends">Friends<span
                        class="badge badge-info">${friendsIds.size()}</span></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" data-toggle="tab" href="#messages">Messages</a>
            </li>
        </ul>
        <div id="myTabContent" class="tab-content">

            <div class="tab-pane fade active show" id="requests">
                <table id="friendRequestTable" class="table table-striped table-bordered table-sm" cellspacing="0"
                       width="100%">
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
                                <a href="user-profile?userid=${friendRequest.senderId}">
                                        ${userDao.findById(friendRequest.senderId).userName} sent you a friend request
                                </a>
                            </td>
                            <td>
                                <form action="FriendRequestAcceptServlet" method="post" style="float:left">
                                    <button type="submit" class="btn btn-success btn-sm">
                                        Accept
                                    </button>
                                    <input type="text" hidden name="receiverId" value="${friendRequest.senderId}"/>
                                </form>
                                <form action="FriendRequestDeleteServlet" method="post" style="float:left">
                                    <button type="submit" class="btn btn-warning btn-sm">
                                        Reject
                                    </button>
                                    <input type="text" hidden name="receiverId" value="${friendRequest.senderId}"/>
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
            <div class="tab-pane fade" id="friends">
                <table id="friendsTable" class="table table-striped table-bordered table-sm" cellspacing="0"
                       width="100%">
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
                    <c:forEach items="${friendsIds}" var="friendId">
                        <% User currentFriend = userDao.findById((Integer) pageContext.getAttribute("friendId"));%>
                        <tr>
                            <td>
                                <a href="user-profile?userid=${friendId}"><%=currentFriend.getUserName()%>
                                </a>
                            </td>
                            <td>
                                <%=currentFriend.getFirstName()%>
                            </td>
                            <td>
                                <%=currentFriend.getLastName()%>
                            </td>
                            <td>0</td>
                            <td>0</td>
                            <td>
                                <form action="FriendRequestDeleteServlet" method="post">
                                    <button type="submit" class="btn btn-danger btn-sm">
                                        <i class="fa fa-trash"></i> Unfriend
                                    </button>
                                    <input type="text" hidden name="receiverId" value="<%=currentFriend.getId()%>"/>
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
    $('#friendsTable tr').click(function () {
        $(this).remove();

        return false;
    });
</script>
</body>

</html>