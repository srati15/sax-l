<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="enums.RequestStatus" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h" %>
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
    <link rel="stylesheet" href="css/card.min.css">

    <link rel="stylesheet" type="text/css" href="css/dataTables.bootstrap4.min.css"/>

</head>

<body>

<div class="wrapper">
    <!-- Sidebar Holder -->
    <jsp:include page="components/sidebar.jsp"/>
    <!-- Page Content Holder -->
    <div id="content">
        <jsp:include page="components/topbar.jsp"/>
        <div class="container">
            <div class="ui link cards">
                <div class="card">
                    <div class="content">
                        <div class="header">${requestScope.profileUser.userName}
                        </div>
                        <div class="description">
                            Name: ${requestScope.profileUser.firstName}<br>
                            Last Name: ${requestScope.profileUser.lastName}<br>
                            Achievements: ${requestScope.profileUser.achievements.size()}<br>
                            Friends: ${requestScope.profileUser.friends.size()}<br>
                        </div>
                    </div>
                    <div class="extra content">
                    <span class="right floated">
                        <c:choose>
                            <c:when test="${requestScope.request1 == null && requestScope.request2 == null}">
                <div style="margin: -5px 2px 0 10px">
                    <form action="FriendRequestSenderServlet" method="post">
                        <input type="submit" class="btn btn-success btn-sm"
                               value="Send Friend Request" style="float:left"/>
                        <input type="text" hidden name="receiverId" value="${requestScope.profileUser.id}"/>
                    </form>
                </div>
                            </c:when>
                            <c:otherwise>
                                <c:choose>
                                    <c:when test="${requestScope.request1!=null && requestScope.request1.status == RequestStatus.Pending}">
                        <div style="margin: -5px 2px 0 10px">
                            <form action="FriendRequestDeleteServlet" method="post">
                                <input type="submit" style="float:left" class="btn btn-warning btn-sm"
                                       value="Cancel Friend Request"/>
                                <input type="text" hidden name="receiverId" value="${requestScope.profileUser.id}"/>
                            </form>
                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <c:choose>
                                            <c:when test="${(requestScope.request1!=null && requestScope.request1.status == RequestStatus.Accepted) || (requestScope.request2 !=null && requestScope.request2.status==RequestStatus.Accepted)}">
                                <div style="margin: -5px 2px 0 10px">
                                    <form action="FriendRequestDeleteServlet" method="post">
                                        <input type="submit" style="float:left" class="btn btn-warning btn-sm"
                                               value="Remove Friend"/>
                                        <input type="text" hidden name="receiverId" value="${requestScope.profileUser.id}"/>
                                    </form>
                                </div>
                                            </c:when>
                                        </c:choose>
                                    </c:otherwise>
                                </c:choose>
                            </c:otherwise>
                        </c:choose>
                    </span>
                        <span>
                        <div style="margin: -5px 2px 0 0">
            <button class="btn btn-success btn-sm" onclick="openForm()" style="float:left"><i class="fa fa-paper-plane"></i> Send Message
            </button>
        </div>
                    </span>
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