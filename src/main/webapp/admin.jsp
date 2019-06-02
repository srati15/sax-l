<%@ page import="dao.AnnouncementDao" %>
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

<%DaoManager manager = (DaoManager) request.getServletContext().getAttribute("manager");
    AnnouncementDao announcementDao = manager.getDao(DaoType.Announcement);
%>
<!-- ***** Preloader Start ***** -->
<div id="preloader">
    <div class="mosh-preloader"></div>
</div>

<!-- ***** Header Area Start ***** -->
<header class="header_area clearfix">
    <jsp:include page="header.jsp"/>
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
                            <li class="breadcrumb-item"><a href="/">Home</a></li>
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
            <!-- Single Feature Area -->
            <div class="col-12 col-sm-6 col-md-4">
                <div class="single-feature-area d-flex mb-100">
                    <div class="feature-icon mr-30">
                        <img src="img/core-img/promotion.png" alt="">
                    </div>
                    <div class="feature-content">
                        <h4>Announcements!</h4>
                        <p><a href="announcements">Manage announcements</a></p>
                    </div>
                </div>
            </div>
            <!-- Single Feature Area -->
            <div class="col-12 col-sm-6 col-md-4">
                <div class="single-feature-area d-flex mb-100">
                    <div class="feature-icon mr-30">
                        <img src="img/core-img/edit.png" alt="">
                    </div>
                    <div class="feature-content">
                        <h4>Users</h4>
                        <p><a href="users-list">Manage Users</a></p>
                    </div>
                </div>
            </div>
            <!-- Single Feature Area -->
            <div class="col-12 col-sm-6 col-md-4">
                <div class="single-feature-area d-flex mb-100">
                    <div class="feature-icon mr-30">
                        <img src="img/core-img/video-camera.png" alt="">
                    </div>
                    <div class="feature-content">
                        <h4>Quizzes</h4>
                        <p>Manage Quizzes.</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- ***** Services Area End ***** -->

<!-- ***** Footer Area Start ***** -->
<footer class="footer-area clearfix">
    <jsp:include page="footer.jsp"/>
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