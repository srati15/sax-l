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
    <div class="container" id="createContainer" style="display: inline-block;">
        <h2 id="questionNum">Question N1</h2>
        <div class="progress" id="progressBar">
            <div class="progress-bar progress-bar-striped" id="progressLabel" role="progressbar" aria-valuenow="0"
                 aria-valuemin="0" aria-valuemax="100" style="width:0%">
                0%
            </div>
        </div>
        <div style="float: left; width: 80%">
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

                    <!-- Text input-->
                    <div class="form-group">
                        <label class="col-md-4 control-label" for="simpleQuestionText">Question</label>
                        <div class="col-md-8">
                            <input id="simpleQuestionText" name="simpleQuestionText" type="text" placeholder=""
                                   class="form-control input-md" required>

                        </div>
                    </div>

                    <!-- Text input-->
                    <div class="form-group">
                        <label class="col-md-4 control-label" for="simpleAnswerText">Answer</label>
                        <div class="col-md-8">
                            <input id="simpleAnswerText" name="simpleAnswerText" type="text" placeholder=""
                                   class="form-control input-md" required>
                        </div>
                    </div>
                    <button type="button" class="btn btn-info" id="simpleQuestionSubmit">Submit</button>

                </div>
            </div>
            <div class="collapse" id="pictureResponse">
                <div class="card card-body">
                    <!-- Text input-->
                    <div class="form-group">
                        <label class="col-md-4 control-label" for="pictureQuestionText">Image source</label>
                        <div class="col-md-8">
                            <input id="pictureQuestionText" name="pictureQuestionText" type="text" placeholder=""
                                   class="form-control input-md" required>

                        </div>
                    </div>

                    <!-- Text input-->
                    <div class="form-group">
                        <label class="col-md-4 control-label" for="pictureAnswerText">Answer</label>
                        <div class="col-md-8">
                            <input id="pictureAnswerText" name="pictureAnswerText" type="text" placeholder=""
                                   class="form-control input-md" required>
                        </div>
                    </div>
                </div>
                <button type="button" class="btn btn-info" id="pictureSubmit">Submit</button>
            </div>
        </div>
        <div style="float: end">
            <form action="QuizCreationServlet" method="post" id="submitQuizForm">
                <fieldset>
                    <!-- Text input-->
                    <div class="form-group">
                        <label for="quizname">Quiz Name</label>
                        <div>
                            <input id="quizname" name="quizname" type="text" placeholder=""
                                   class="form-control input-md"
                                   required="">
                        </div>
                    </div>

                    <!-- Select Basic -->
                    <div class="form-group">
                        <label for="correction">Allow immediate correction</label>
                        <div>
                            <select id="correction" name="correction" class="form-control">
                                <option value="yes">Yes</option>
                                <option value="no">No</option>
                            </select>
                        </div>
                    </div>

                    <!-- Select Basic -->
                    <div class="form-group">
                        <label for="singlepage">Show all question on single page</label>
                        <div>
                            <select id="singlepage" name="singlepage" class="form-control">
                                <option value="yes">Yes</option>
                                <option value="no">No</option>
                            </select>
                        </div>
                    </div>

                    <!-- Select Basic -->
                    <div class="form-group">
                        <label for="practice">Allow practice mode</label>
                        <div>
                            <select id="practice" name="practice" class="form-control">
                                <option value="yes">Yes</option>
                                <option value="no">No</option>
                            </select>
                        </div>
                    </div>

                    <!-- Select Basic -->
                    <div class="form-group">
                        <label for="randomized">Randomize questions</label>
                        <div>
                            <select id="randomized" name="randomized" class="form-control">
                                <option value="yes">Yes</option>
                                <option value="no">No</option>
                            </select>
                        </div>
                    </div>

                </fieldset>

                <button type="submit" class="btn btn-success" id="finishCreationButton" style="display: none">Finish
                </button>
            </form>
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
        $("#simpleQuestionSubmit").click(function () {
            var question = $('#simpleQuestionText').val();
            var answer = $('#simpleAnswerText').val();
            var pictQuest = {};
            pictQuest['type'] = 'questionResponse';
            pictQuest['question'] = question;
            pictQuest['answer'] = answer;
            questionsList.push(pictQuest);
            checker(questionsList);
        });


        $("#pictureSubmit").click(function () {
            var question = $('#pictureQuestionText').val();
            var answer = $('#pictureAnswerText').val();
            var pictQuest = {};
            pictQuest['type'] = 'pictureResponse';
            pictQuest['question'] = question;
            pictQuest['answer'] = answer;
            questionsList.push(pictQuest);
            checker();
        });

        function checker() {
            if (questionsList.length <= 10) {
                $("#progressLabel").attr("aria-valuenow", questionsList.length * 10)
                    .css("width", questionsList.length * 10 + "%")
                    .text(questionsList.length * 10 + "%");
            }
            if (questionsList.length === 10) {
                $("#finishCreationButton").css("display", "flex");
                var input = $("<input>").attr("type", "hidden").attr("name", "questions").val(JSON.stringify(questionsList));
                $('#submitQuizForm').append(input);
            } else if (questionsList.length > 10) {
                $('[name=questions]').val(JSON.stringify(questionsList));
            }
            $('#questionNum').text("Question N" + (questionsList.length + 1));
            console.log(questionsList)
        }
    });
</script>

</body>

</html>