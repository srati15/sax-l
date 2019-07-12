<%@ page import="dao.ActivityDao" %>
<%@ page import="dao.UserDao" %>
<%@ page import="datatypes.server.Activity" %>
<%@ page import="datatypes.user.User" %>
<%@ page import="enums.DaoType" %>
<%@ page import="manager.DaoManager" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Comparator" %>
<%@ page import="java.util.List" %>
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


    <!-- Title -->
    <title>Sax-L - Quiz Website | Activities</title>

    <!-- Favicon -->
    <link rel="icon" href="img/core-img/favicon.ico">

    <!-- Core Stylesheet -->
    <link href="style.css" rel="stylesheet">
    <!-- Responsive CSS -->
    <link href="css/responsive.css" rel="stylesheet">

    <link href="css/datatables.min.css" rel="stylesheet">

    <link href="css/toastr.css" rel="stylesheet">
</head>
<body>

<%
    DaoManager daoManager = (DaoManager) request.getServletContext().getAttribute("manager");
    ActivityDao activityDao = daoManager.getDao(DaoType.Activity);
    UserDao userDao = daoManager.getDao(DaoType.User);
    List<Activity> activities = new ArrayList<>(activityDao.findAll());
    activities.sort(Comparator.comparing(Activity::getDateTime).reversed());
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss MMM dd, yyyy");
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
                    <h2>Activity logs</h2>
                    <nav aria-label="breadcrumb">
                        <ol class="breadcrumb">
                            <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/">Home</a></li>
                            <li class="breadcrumb-item active" aria-current="page">Activities</li>
                        </ol>
                    </nav>
                </div>
            </div>
        </div>
    </div>
</div>

<section class="mosh-aboutUs-area">
    <div class="container"  >
        <h3 class="mb-30">All Activities</h3>

        <table id="myTable" class="table table-striped table-bordered table-sm" style="display: inline-table">
            <thead>
            <tr>
                <th>#</th>
                <th>User</th>
                <th>Action</th>
                <th>Date</th>
            </tr>
            </thead>
            <tbody>
            <c:set var="i" value="0" scope="page"/>
            <c:forEach items="<%=activities%>" var="activity">
                <tr>
                    <%
                        Activity activity = (Activity) pageContext.getAttribute("activity");
                        User user = userDao.findById(activity.getUserId());
                    %>
                    <td>${i+1}</td>
                    <td>
                        <c:choose>
                            <c:when test="${sessionScope.user.id != activity.userId}">
                                <a href="user-profile?userid=${activity.userId}"><%=user.getUserName()%>
                                </a>
                            </c:when>
                            <c:otherwise>
                                <a href="profile"><%=user.getUserName()%>
                                </a>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                            ${activity.activityName}
                    </td>
                    <td>
                        <%=formatter.format(activity.getDateTime())%>
                    </td>
                </tr>
                <c:set var="i" value="${i + 1}" scope="page"/>
            </c:forEach>
            </tbody>
            <tfoot>
            <tr>
                <th>#</th>
                <th>User</th>
                <th>Action</th>
                <th>Date</th>
            </tr>
            </tfoot>
        </table>
    </div>
</section>
<!-- ***** Users list Area End ***** -->
<footer class="footer-area clearfix">
    <jsp:include page="components/footer.jsp"/>
</footer>

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

<!---table scroll -->
<script type="text/javascript" src="js/datatables.min.js"></script>
<script>
    $(document).ready(function () {
        $('#myTable').DataTable();
        $('.dataTables_length').addClass('bs-select');
    });
</script>
<script src="js/toastr.js"></script>

<jsp:include page="components/notifications.jsp"/>

</body>
</html>
