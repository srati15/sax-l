<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="dao.AnnouncementDao" %>
<%@ page import="dao.QuizDao" %>
<%@ page import="dao.QuizResultDao" %>
<%@ page import="dao.UserDao" %>
<%@ page import="datatypes.quiz.QuizResult" %>
<%@ page import="datatypes.user.User" %>
<%@ page import="enums.DaoType" %>
<%@ page import="manager.DaoManager" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ page import="java.time.LocalDate" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h" %>

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
    UserDao userDao = manager.getDao(DaoType.User);
    QuizDao quizDao = manager.getDao(DaoType.Quiz);
    QuizResultDao quizResultDao = manager.getDao(DaoType.QuizResult);
    int quizId = Integer.valueOf(request.getParameter("quizId"));
    Map<Integer, List<QuizResult>> quizResultMap = new HashMap<>();
    quizResultDao.findAll().stream().filter(s -> s.getQuizId() == quizId).collect(Collectors.toList()).forEach(s -> {
        quizResultMap.putIfAbsent(s.getUserId(), new ArrayList<>());
        quizResultMap.get(s.getUserId()).add(s);
    });
    List<QuizResult> topPerformers = quizResultDao.findAll().stream().sorted(Comparator.comparing(QuizResult::getScore).reversed().thenComparing(QuizResult::getTimeSpent)).limit(3).collect(Collectors.toList());
    List<QuizResult> topPerformersToday = quizResultDao.findAll().stream().filter(q -> q.getTimestamp().toLocalDate().equals(LocalDate.now())).sorted(Comparator.comparing(QuizResult::getScore).reversed().thenComparing(QuizResult::getTimeSpent)).limit(3).collect(Collectors.toList());
    pageContext.setAttribute("topPerformersToday", topPerformersToday);
    pageContext.setAttribute("quizResults", quizResultMap);
    pageContext.setAttribute("topPerformers", topPerformers);
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

<c:set var="quiz" value="<%=quizDao.findById(quizId)%>"/>
<section class="mosh-aboutUs-area">
    <div class="container">
        <div class="row">
            <!-- Single Blog Start -->
            <div class="col-12">
                <div class="single-blog wow fadeInUp" data-wow-delay="0.2s">
                    <!-- Post Meta -->
                    <div class="post-meta">
                        <h6>By <a
                                href="user-profile?userid=${userDao.findById(quiz.authorId).id}">${userDao.findById(quiz.authorId).userName},</a>${quiz.dateCreated.toLocalDate()}
                        </h6>
                    </div>
                    <!-- Quiz Title -->
                    <h2>${quiz.quizName}</h2>
                    <!-- Post Thumb -->
                    <div class="blog-post-thumb text-center">
                        <img src="${quiz.quizImageURL}" class="rounded" alt="" style="max-height:500px;">
                    </div>

                    <!-- Quiz desc -->
                    <c:if test="${quiz.description.length()>0}">
                        <div class="card">
                            <div class="card-body">
                                    ${quiz.description}
                            </div>
                        </div>
                    </c:if>
                    <!-- Take Quiz -->
                    <p class="text-center">
                        <h:start quiz="${quiz}" buttonClass="btn mosh-btn" styled="false"/>
                    </p>

                    <h:performer userDao="${userDao}" title="Top Performers" quizResults="${topPerformers}"/>
                    <h:performer userDao="${userDao}" title="Top Performers Today" quizResults="${topPerformersToday}"/>


                    <c:if test="${quizResults.size() > 0}">
                        <h2 class="text-center">All Performers</h2>
                        <div class="card-deck border border-warning rounded-top section_padding_50">
                            <c:forEach var="entry" items="${quizResults}">
                                <div class="team-meta-info">
                                    <div class="card" style="width: 18rem;">
                                        <h5 class="card-title">User: <b><a
                                                href="user-profile?userid=${userDao.findById(entry.key).id}">${userDao.findById(entry.key).userName}</a></b>
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
                    </c:if>
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