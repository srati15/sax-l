<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="description" content="">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">


    <!-- Title -->
    <title>Sax-L - Quiz Website | Details</title>

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

<section class="mosh-aboutUs-area">
    <div class="container">
        <div class="row">
            <div class="col-12">

                <div class="single-blog wow fadeInUp" data-wow-delay="0.2s">
                    <div class="ui divided items">
                        <div class="item">
                            <div class="image">
                                <img src="${requestScope.quiz.quizImageURL}">
                            </div>
                            <div class="content">
                                <a class="header">${requestScope.quiz.quizName}</a>
                                <div class="meta">
                                    <span class="cinema">
                                        By <a
                                            href="user-profile?userid=${requestScope.userDao.findById(requestScope.quiz.authorId).id}">${userDao.findById(quiz.authorId).userName},</a>${quiz.dateCreated.toLocalDate()}
                                    </span>
                                </div>
                                <div class="description">
                                    <p>${requestScope.quiz.description}</p>
                                </div>
                                <div class="extra" style="float:right">
                                    <h:start quiz="${requestScope.quiz}" buttonClass="btn mosh-btn" styled="false"/>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="ui comments blue segments">
                        <h3 class="ui top attached header">Comments</h3>
                        <c:forEach var="comment" items="${requestScope.quiz.comments}">
                            <h:comments userDao="${requestScope.userDao}" comment="${comment}"/>
                        </c:forEach>
                        <form class="ui reply form" action="AddCommentServlet" method="post">
                            <textarea class="form-control" name="commentText"></textarea>
                            <input hidden name="quizId" value="${requestScope.quiz.id}"/>
                            <button class="ui blue button" type="submit">
                                <i class="fa fa-reply"></i> Add Reply
                            </button>
                        </form>
                    </div>
                    <div class="row ui horizontal segments">
                        <c:if test="${requestScope.topPerformers.size() > 0}">
                            <div class="col-sm">
                                <h:performer userDao="${requestScope.userDao}" title="Top Performers"
                                             quizResults="${requestScope.topPerformers}"/>
                            </div>
                        </c:if>
                        <c:if test="${requestScope.topPerformersToday.size() > 0}">
                            <div class="col-sm">
                                <h:performer userDao="${requestScope.userDao}" title="Top Performers Today"
                                             quizResults="${requestScope.topPerformersToday}"/>
                            </div>
                        </c:if>
                        <c:if test="${requestScope.quizResults.size() > 0}">
                            <div class="col-sm">
                                <div class="ui segment">
                                    <h2 class="text-center">All Performers</h2>
                                    <div class="card-deck">
                                        <c:forEach var="entry" items="${requestScope.quizResults}">
                                            <div class="team-meta-info">
                                                <div class="card" style="width: 18rem;">
                                                    <h5 class="card-title">User: <b><a
                                                            href="user-profile?userid=${requestScope.userDao.findById(entry.key).id}">${userDao.findById(entry.key).userName}</a></b>
                                                    </h5>
                                                    <div class="card-body">
                                                        <c:forEach var="quizRes" items="${entry.value}">
                                                            <p class="card-text">Score: <b>${quizRes.score}</b> Time:
                                                                <b>${quizRes.timeSpent}</b> Seconds</p>
                                                        </c:forEach>
                                                    </div>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </div>

                                </div>
                            </div>
                        </c:if>
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

<jsp:include page="components/notifications.jsp"/>


</body>

</html>