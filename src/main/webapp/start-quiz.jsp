<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="dao.AnnouncementDao" %>
<%@ page import="dao.QuizDao" %>
<%@ page import="dao.UserDao" %>
<%@ page import="datatypes.quiz.Quiz" %>
<%@ page import="enums.DaoType" %>
<%@ page import="manager.DaoManager" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="description" content="">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">


    <!-- Title -->
    <title>Sax-L - Quiz Website | Start Quiz</title>

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
    pageContext.setAttribute("quizDao", quizDao);
    Quiz quiz = quizDao.findById(Integer.valueOf(request.getParameter("quizId")));
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
<!-- ***** Welcome Area Start ***** -->

<section class="mosh-clients-area section_padding_100">
    <div class="container">
        <form action="CompleteQuizServlet" method="post">
            <h2 id="quizTimer">
                <time>00:00:00</time>
            </h2>
            <input type="hidden" id="completeTime" name="completeTime" value="">
            <c:set var="questionNum" value="1"/>
            <c:set var="questAnsMap" value="${requestScope.questionAnswerMap}"/>

            <c:forEach items="${requestScope.currentQuizQuestions}" var="question">
                <h:question question="${question}" answer="${requestScope.questionAnswerMap.get(question)}"
                            questionNumber="${questionNum}"/>
                <c:set var="questionNum" value="${questionNum+1}"/>
            </c:forEach>
            <button type="submit" class="btn btn-success" id="clear"><i class="fa fa-hourglass-end"></i> Finish
                <input type="text" hidden name="quizId" value="<%=quiz.getId()%>">
            </button>
        </form>
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


<script src="js/stopwatch.js"></script>
<jsp:include page="components/notifications.jsp"/>

</body>

</html>