<%@ page import="java.time.format.DateTimeFormatter" %>
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
    <title>Sax-L - Quiz Website | Inbox</title>

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
                    <h2>Admin inbox</h2>
                    <nav aria-label="breadcrumb">
                        <ol class="breadcrumb">
                            <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/">Home</a></li>
                            <li class="breadcrumb-item active" aria-current="page">Inbox</li>
                        </ol>
                    </nav>
                </div>
            </div>
        </div>
    </div>
</div>

<section class="mosh-aboutUs-area">
    <div class="container">
        <div class="ui segment">
            <a class="huge ui black ribbon label">
                New Messages
            </a>
            <table id="newMessage" class="table table-striped table-bordered table-sm">
                <thead>
                <tr>
                    <th>#</th>
                    <th>Name</th>
                    <th>Mail</th>
                    <th>Subject</th>
                    <th>Date</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                <c:set var="i" value="0" scope="page"/>
                <c:forEach items="${requestScope.notSeen}" var="message">
                    <tr>
                        <td>${i+1}</td>
                        <td>${message.name}</td>
                        <td>${message.mail}</td>
                        <td>${message.subject}</td>
                        <td>${DateTimeFormatter.ofPattern("HH:mm:ss MMM dd, yyyy").format(message.time)}</td>
                        <td>
                            <h:mail message="${message}"/>
                            <h:delete entityName="Message" actionServlet="DeleteInboxMessageServlet" hiddenParameterName="messageId" hiddenParameterValue="${message.id}"/>
                        </td>
                    </tr>
                    <c:set var="i" value="${i + 1}" scope="page"/>
                </c:forEach>
                </tbody>
                <tfoot>
                <tr>
                    <th>#</th>
                    <th>Name</th>
                    <th>Mail</th>
                    <th>Subject</th>
                    <th>Date</th>
                    <th>Action</th>
                </tr>
                </tfoot>
            </table>
        </div>
        <div class="ui segment">
            <a class="huge ui black ribbon label">
                Old Messages
            </a>
            <table id="oldMessage" class="table table-striped table-bordered table-sm">
                <thead>
                <tr>
                    <th>#</th>
                    <th>Name</th>
                    <th>Mail</th>
                    <th>Subject</th>
                    <th>Date</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                <c:set var="i" value="0" scope="page"/>
                <c:forEach items="${requestScope.seen}" var="message">
                    <tr>
                        <td>${i+1}</td>
                        <td>${message.name}</td>
                        <td>${message.mail}</td>
                        <td>${message.subject}</td>
                        <td>${DateTimeFormatter.ofPattern("HH:mm:ss MMM dd, yyyy").format(message.time)}</td>
                        <td><h:delete entityName="Message" actionServlet="DeleteInboxMessageServlet" hiddenParameterName="messageId" hiddenParameterValue="${message.id}"/></td>
                    </tr>
                    <c:set var="i" value="${i + 1}" scope="page"/>
                </c:forEach>
                </tbody>
                <tfoot>
                <tr>
                    <th>#</th>
                    <th>Name</th>
                    <th>Mail</th>
                    <th>Subject</th>
                    <th>Date</th>
                    <th>Action</th>
                </tr>
                </tfoot>
            </table>
        </div>
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
        $('.table').DataTable();
        $('.dataTables_length').addClass('bs-select');
    });
</script>
<script src="js/toastr.js"></script>

<jsp:include page="components/notifications.jsp"/>

</body>
</html>
