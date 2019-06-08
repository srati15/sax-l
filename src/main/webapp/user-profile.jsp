<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="datatypes.User" %>
<%@ page import="manager.DaoManager" %>
<%@ page import="dao.FriendRequestDao" %>
<%@ page import="enums.DaoType" %>
<%@ page import="datatypes.messages.FriendRequest" %>
<%@ page import="enums.RequestStatus" %>
<%@ page import="dao.UserDao" %>
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
    UserDao userDao = manager.getDao(DaoType.User);
    User user = (User) request.getSession().getAttribute("user");
    int id = Integer.parseInt(request.getParameter("userid"));
    User profileUser = userDao.findById(id);
    FriendRequest request1 = friendRequestDao.findBySenderReceiverId(user.getId(), id);
    FriendRequest request2 = friendRequestDao.findBySenderReceiverId(id, user.getId());
    pageContext.setAttribute("request1", request1);
    pageContext.setAttribute("request2", request2);
    pageContext.setAttribute("pending", RequestStatus.Pending);
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
                    <h2><%=profileUser.getUserName()%>'s Profile</h2>
                    <nav aria-label="breadcrumb">
                        <ol class="breadcrumb">
                            <li class="breadcrumb-item"><a href="/">Home</a></li>
                            <li class="breadcrumb-item active" aria-current="page"><%=profileUser.getUserName()%>'s
                                profile
                            </li>
                            <c:choose>
                                <c:when test="${request1 == null && request2 == null}">
                                    <form action="FriendRequestSenderServlet" method="post">
                                        <input type="submit" class="btn btn-success btn-sm"
                                               value="Send Friend Request"/>
                                        <input type="text" hidden name="receiverId" value="<%=id%>"/>
                                    </form>
                                </c:when>
                                <c:otherwise>
                                    <c:choose>
                                        <c:when test="${request1!=null && request1.status == RequestStatus.Pending}">
                                            <form action="FriendRequestDeleteServlet" method="post">
                                                <input type="submit" class="btn btn-warning btn-sm"
                                                       value="Cancel Friend Request"/>
                                                <input type="text" hidden name="receiverId" value="<%=id%>"/>
                                            </form>
                                        </c:when>
                                        <c:otherwise>
                                            <c:choose>
                                                <c:when test="${(request1!=null && request1.status == RequestStatus.Accepted) || (request2 !=null && request2.status==RequestStatus.Accepted)}">
                                                    <form action="FriendRequestDeleteServlet" method="post">
                                                        <input type="submit" class="btn btn-warning btn-sm"
                                                               value="Remove Friend"/>
                                                        <input type="text" hidden name="receiverId" value="<%=id%>"/>
                                                    </form>
                                                </c:when>
                                            </c:choose>
                                        </c:otherwise>
                                    </c:choose>
                                </c:otherwise>
                            </c:choose>
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