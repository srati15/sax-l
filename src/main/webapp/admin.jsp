
<!DOCTYPE html>
<html lang="en">
<%@ taglib tagdir="/WEB-INF/tags" prefix="h" %>

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

<div class="mosh-breadcumb-area" style="background-image: url(img/core-img/breadcumb.png);">
    <div class="container h-100">
        <div class="row h-100 align-items-center">
            <div class="col-12">
                <div class="bradcumbContent">
                    <h2>Admin Page</h2>
                    <nav aria-label="breadcrumb">
                        <ol class="breadcrumb">
                            <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/">Home</a></li>
                            <li class="breadcrumb-item active" aria-current="page">Admin</li>
                        </ol>
                    </nav>
                </div>
            </div>
        </div>
    </div>
</div>


<!-- ***** Services Area Start ***** -->
<section class="mosh--services-area section_padding_100">
    <div class="container">
        <div class="row">
            <h:admin-feature imageSource="img/core-img/promotion.png" heading="Announcements" reference="announcements"></h:admin-feature>
            <h:admin-feature imageSource="img/core-img/user.png" heading="Users" reference="users-list"></h:admin-feature>
            <h:admin-feature imageSource="img/core-img/quiz.png" heading="Quizzes" reference="quiz"></h:admin-feature>
            <h:admin-feature imageSource="img/core-img/activities.png" heading="Activities" reference="activities"></h:admin-feature>
            <h:admin-feature imageSource="img/core-img/server.png" heading="Server logs" reference="server-logs"></h:admin-feature>
            <h:admin-feature imageSource="img/core-img/mail.png" heading="Inbox Messages" reference="inbox"></h:admin-feature>
            <h:admin-feature imageSource="img/core-img/stats.png" heading="Statistics" reference="statistics"></h:admin-feature>
        </div>
    </div>
</section>
<!-- ***** Services Area End ***** -->

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

<script src="js/toastr.js"></script>
<jsp:include page="components/notifications.jsp"/>

</body>

</html>