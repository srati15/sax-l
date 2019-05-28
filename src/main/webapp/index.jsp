<%@ page import="manager.DaoManager" %>
<%@ page import="dao.QuizDao" %>
<%@ page import="dao.AnnouncementDao" %>
<%@ page import="datatypes.Announcement" %>
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
    AnnouncementDao announcementDao = manager.getAnnouncementDao();
%>
<!-- ***** Preloader Start ***** -->
<div id="preloader">
    <div class="mosh-preloader"></div>
</div>

<!-- ***** Header Area Start ***** -->
<header class="header_area clearfix">
    <!-- ***** Announcements start ***** -->
    <%for (Announcement announcement: announcementDao.findAll()){%>
    <div class="alert alert-warning alert-dismissible fade show" role="alert">
        <strong><%=announcement.getAnnouncementText()%></strong>
        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
            <span aria-hidden="true">&times;</span>
        </button>
    </div>
    <%}
    %>
    <!-- ***** Announcements end ***** -->

    <jsp:include page="header.jsp"/>
</header>
<!-- ***** Header Area End ***** -->

<!-- ***** Welcome Area Start ***** -->
<section class="welcome_area clearfix" id="home" style="background-image: url(img/bg-img/welcome-bg.png)">
    <div class="hero-slides owl-carousel">
        <!-- Single Hero Slides -->
        <div class="single-hero-slide d-flex align-items-end justify-content-center">
            <div class="hero-slide-content text-center">
                <h2>Sax-l</h2>
                <h4>Top Quizzes!</h4>
            </div>
        </div>
        <!-- Single Hero Slides -->
    </div>
</section>
<!-- ***** Welcome Area End ***** -->

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