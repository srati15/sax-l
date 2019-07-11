<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                            <li class="breadcrumb-item active" aria-current="page">Quiz Result</li>
                        </ol>
                    </nav>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- ***** Welcome Area Start ***** -->

<section class="mosh-aboutUs-area">
    <div class="container" >
        <h2>Result: ${requestScope.score} / ${requestScope.results.size()} Time: ${requestScope.timespent} </h2>
        <table id="myTable" class="table table-striped table-bordered table-sm " style="width: auto">
            <thead>
            <tr>
                <th>Question</th>
                <th>Answer</th>
                <th>Correct</th>
            </tr>
            </thead>
            <tbody>
            <c:set var="i" value="0" scope="page"/>
            <c:forEach items="${requestScope.results.keySet()}" var="question">
                <c:choose>
                    <c:when test="${requestScope.results.get(question)}">
                        <tr class="table-success">
                            <td>
                                <p class="mb-0 p-2 text-dark rounded">${question.question}</p>
                            </td>
                            <td>
                                <p class="mb-0 p-2 text-dark rounded">${requestScope.userAnswers.get(question.id)}</p>
                            </td>
                            <td>
                                <p class="mb-0 p-2 text-dark rounded"><i class="fa fa-check" aria-hidden="true">Correct</i></p>
                            </td>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <tr class="table-danger">
                            <td>
                                <p class="mb-0 p-2 text-dark rounded">${question.question}</p>
                            </td>
                            <td>
                                <p class="mb-0 p-2 text-dark rounded">${requestScope.userAnswers.get(question.id)}</p>
                            </td>
                            <td>
                                <p class="mb-0 p-2 text-dark rounded"><i class="fa fa-remove" aria-hidden="true">Incorrect</i></p>
                            </td>
                        </tr>
                    </c:otherwise>
                </c:choose>
                <c:set var="i" value="${i + 1}" scope="page"/>
            </c:forEach>
            </tbody>
            <tfoot>
            <tr>
                <th>Question</th>
                <th>Answer</th>
                <th>Correct</th>
            </tr>
            </tfoot>
        </table>

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
<jsp:include page="components/notifications.jsp"/>

</body>

</html>