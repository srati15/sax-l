<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="dao.AnnouncementDao" %>
<%@ page import="dao.QuizDao" %>
<%@ page import="dao.UserDao" %>
<%@ page import="datatypes.user.User" %>
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
    <link href="css/toastr.css" rel="stylesheet">

</head>

<body>

<%
    DaoManager manager = (DaoManager) request.getServletContext().getAttribute("manager");
    AnnouncementDao announcementDao = manager.getDao(DaoType.Announcement);
    UserDao userDao = manager.getDao(DaoType.User);
    QuizDao quizDao = manager.getDao(DaoType.Quiz);
    pageContext.setAttribute("userDao", userDao);
    User user = (User) request.getSession().getAttribute("user");
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
<div class="mosh-breadcumb-area" style="background-image: url(img/core-img/breadcumb.png);">
    <div class="container h-100">
        <div class="row h-100 align-items-center">
            <div class="col-12">
                <div class="bradcumbContent">
                    <h2>Quizzes</h2>
                    <nav aria-label="breadcrumb">
                        <ol class="breadcrumb">
                            <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/">Home</a></li>
                            <li class="breadcrumb-item active" aria-current="page">Quizzes</li>
                        </ol>
                    </nav>
                </div>
            </div>
        </div>
    </div>
</div>

<%
    int quizId =Integer.valueOf(request.getParameter("quizId"));
%>
<c:set var="quiz" value="<%=quizDao.findById(quizId)%>"/>
<section class="mosh-aboutUs-area">
    <div class="container">
        <div class="row">
            <div class="col-12 col-md-8">
                <div class="mosh-blog-posts">
                    <div class="row">
                        <!-- Single Blog Start -->
                        <div class="col-12">
                            <div class="single-blog wow fadeInUp" data-wow-delay="0.2s">
                                <!-- Post Thumb -->
                                <div class="blog-post-thumb">
                                    <img src="${quiz.quizImageURL}" class="rounded" alt="">
                                </div>
                                <!-- Post Meta -->
                                <div class="post-meta">
                                    <h6>By <a href="user-profile?userid=${userDao.findById(quiz.authorId).id}">${userDao.findById(quiz.authorId).userName},</a><a href="#">${quiz.dateCreated.toLocalDate()}</a></h6>
                                </div>
                                <!-- Post Title -->
                                <h2>${quiz.quizName}</h2>
                                <!-- Post Excerpt -->
                                <p>${quiz.description}</p>
                                <!-- Read More btn -->
                                <c:choose>
                                    <c:when test="${quiz.onePage}">
                                        <a href="start-quiz?quizId=${quiz.id}">
                                            <button type="button" class="btn btn-info btn-sm"  style="float:left">
                                                <i class="fa fa-hourglass-start"></i> Start
                                            </button>
                                        </a>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="start-quiz?quizId=${quiz.id}&questionId=1">
                                            <button type="button" class="btn btn-info btn-sm"  style="float:left">
                                                <i class="fa fa-hourglass-start"></i> Start
                                            </button>
                                        </a>
                                    </c:otherwise>
                                </c:choose>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
</section>
<!-- ***** Welcome Area End ***** -->


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
<script src="js/jquery.validate.js"></script>
<script src="js/quiz.js"></script>

<script>
    $(document).ready(function () {
        toastr.options.closeButton = true;
        toastr.options.timeOut = 0;
        toastr.options.extendedTimeOut = 0;
        toastr.options.positionClass = "toast-bottom-right";
        <c:forEach items="<%=announcementDao.findAll()%>" var="announcement">
        toastr.info('${announcement.announcementText}');
        </c:forEach>
    });
</script>

</body>

</html>