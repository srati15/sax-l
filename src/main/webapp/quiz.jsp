<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <link href="css/toastr.css" rel="stylesheet">

</head>

<body>

<%
    DaoManager manager = (DaoManager) request.getServletContext().getAttribute("manager");
    AnnouncementDao announcementDao = manager.getDao(DaoType.Announcement);
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
                            <li class="breadcrumb-item"><a href="/">Home</a></li>
                            <li class="breadcrumb-item active" aria-current="page">Quizzes</li>
                        </ol>
                    </nav>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- ***** Welcome Area Start ***** -->

<section class="mosh-aboutUs-area">
    <div class="container" id="createContainer">
        <h2 id="questionNum">Question N1</h2>
        <p>
            <button class="btn btn-info" type="button" data-toggle="collapse" data-target="#questionResponse"
                    aria-expanded="false" aria-controls="collapseExample">
                Question-Response
            </button>
            <button class="btn btn-info" type="button" data-toggle="collapse" data-target="#pictureResponse"
                    aria-expanded="false" aria-controls="collapseExample">
                Picture-Response
            </button>
        </p>
        <div class="collapse" id="questionResponse">
            <div class="card card-body">
                <div class="form-group">
                    <label class="col-md-4 control-label" for="simpleQuestionText">Question</label>
                    <div class="col-md-4">
                        <textarea class="form-control" id="simpleQuestionText" name="questionText"></textarea>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-4 control-label" for="simpleAnswerText">Answer</label>
                    <div class="col-md-4">
                        <textarea class="form-control" id="simpleAnswerText" name="answerText"></textarea>
                    </div>
                </div>
            </div>
            <button type="button" class="btn btn-info" id="simpleQuestionSubmit">Submit</button>

        </div>
        <div class="collapse" id="pictureResponse">
            <div class="card card-body">
                <div class="form-group">
                    <label class="col-md-4 control-label" for="pictureQuestionText">Picture</label>
                    <div class="col-md-4">
                        <textarea class="form-control" id="pictureQuestionText" name="questionText"></textarea>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-4 control-label" for="pictureAnswerText">Answer</label>
                    <div class="col-md-4">
                        <textarea class="form-control" id="pictureAnswerText" name="answerText"></textarea>
                    </div>
                </div>
            </div>
            <button type="button" class="btn btn-info" id="pictureSubmit">Submit</button>
        </div>
        <form action = "QuizCreationServlet" method="post" id="submitQuizForm">
            <button type="submit" class="btn btn-success" id="finishCreationButton" style="display: none">Finish</button>
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
    $(document).ready(function () {
        $('.collapse').on('show.bs.collapse', function () {
            $('.collapse.show').each(function () {
                $(this).collapse('hide');
            });
        });
    });
    $(document).ready(function () {
        var questionsList = [];
        var finishButton = '<button type="button" class="btn btn-success" id="finishCreationButton">Finish</button>';

        $("#simpleQuestionSubmit").click(function () {
            var question = $('#simpleQuestionText').val();
            var answer = $('#simpleAnswerText').val();
            var pictQuest = {};
            pictQuest['type'] = 'questionResponse';
            pictQuest['question'] = question;
            pictQuest['answer'] = answer;
            questionsList.push(pictQuest);
            if (questionsList.length == 10) {
                $("#finishCreationButton").css("display", "flex");
                var input = $("<input>").attr("type", "hidden").attr("name", "questions").val(JSON.stringify(questionsList));
                $('#submitQuizForm').append(input);
            }else if (questionsList.length >10) {
                $('[name=questions]').val(JSON.stringify(questionsList));
            }
            $('#questionNum').text("Question N" + (questionsList.length + 1));
            console.log(questionsList)
        });


        $("#pictureSubmit").click(function () {
            var question = $('#pictureQuestionText').val();
            var answer = $('#pictureAnswerText').val();
            var pictQuest = {};
            pictQuest['type'] = 'pictureResponse';
            pictQuest['question'] = question;
            pictQuest['answer'] = answer;
            questionsList.push(pictQuest);
            if (questionsList.length == 10) {
                $("#finishCreationButton").css("display", "flex");
                var input = $("<input>").attr("type", "hidden").attr("name", "questions").val(JSON.stringify(questionsList));
                $('#submitQuizForm').append(input);
            }else if (questionsList.length >10) {
                $('[name=questions]').val(JSON.stringify(questionsList));
            }
            $('#questionNum').text("Question N" + (questionsList.length + 1));
            console.log(questionsList)
        });
    });
</script>

</body>

</html>