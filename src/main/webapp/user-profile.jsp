<%@ page import="datatypes.User" %>
<%@ page import="manager.DaoManager" %>
<%@ page import="dao.FriendRequestDao" %>
<%@ page import="enums.DaoType" %>
<%@ page import="datatypes.messages.FriendRequest" %>
<%@ page import="enums.RequestStatus" %>
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
    FriendRequestDao friendRequestDao = manager.getDao(DaoType.FriendRequest);
    User user = (User) request.getSession().getAttribute("user");
    int id = Integer.parseInt(request.getParameter("userid"));
    FriendRequest request1 = friendRequestDao.findBySenderReceiverId(user.getId(), id);
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
                            <%
                                if (request1 == null) {
                            %>
                            <form action="FriendRequestSenderServlet" method="post">
                                <input type="submit" class="btn btn-primary" value="Send Friend Request"/>
                                <input type="text" hidden name="receiverId" value="<%=id%>"/>
                            </form>
                            <%
                            } else if (request1.getStatus() == RequestStatus.Pending) { %>
                            <form action="FriendRequestDeleteServlet" method="post">
                                <input type="submit" class="btn btn-info" value="Cancel Friend Request"/>
                                <input type="text" hidden name="receiverId" value="<%=id%>"/>
                            </form>
                            <%
                                }
                            %>
                        </ol>
                    </nav>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- ***** Breadcumb Area End ***** -->

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
</body>

</html>