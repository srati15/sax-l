<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="dao.AnnouncementDao" %>
<%@ page import="dao.QuizDao" %>
<%@ page import="dao.UserDao" %>
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

<section class="mosh-aboutUs-area">
    <div class="container" id="createContainer">
        <!----quizzes table --->
        <table id="myTable" class="table table-striped table-bordered table-sm">
            <thead>
            <tr>
                <th>#</th>
                <th>Quiz Name</th>
                <th>Author</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody>
            <c:set var="i" value="0" scope="page"/>
            <c:forEach items="<%=quizDao.findAll()%>" var="quiz">
                <tr>
                    <td>${i+1}
                    </td>
                    <td>${quiz.quizName}
                    </td>
                    <td>
                            ${userDao.findById(quiz.authorId).userName}
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${quiz.onePage}">
                                <a href="start-quiz?quizId=${quiz.id}">
                                    <button type="button" class="btn btn-info btn-sm">
                                        <i class="fa fa-hourglass-start"></i> Start
                                    </button>
                                </a>
                            </c:when>
                            <c:otherwise>
                                <a href="start-quiz?quizId=${quiz.id}&questionId=1">
                                    <button type="button" class="btn btn-info btn-sm">
                                        <i class="fa fa-hourglass-start"></i> Start
                                    </button>
                                </a>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
                <c:set var="i" value="${i + 1}" scope="page"/>
            </c:forEach>
            </tbody>
            <tfoot>
            <tr>
                <th>#</th>
                <th>Quiz Name</th>
                <th>Author</th>
                <th>Action</th>
            </tr>
            </tfoot>
        </table>

        <!------quizzes table-->
    </div>
</section>
<!-- ***** Welcome Area End ***** -->

<!-- ***** Clients Area Start ***** -->
<section class="mosh-clients-area section_padding_100 clearfix">
    <div class="container">
        <h2 id="questionNum">Question N1</h2>
        <div class="progress" id="progressBar">
            <div class="progress-bar progress-bar-striped" id="progressLabel" role="progressbar" aria-valuenow="0"
                 aria-valuemin="0" aria-valuemax="100" style="width:0">
                0%
            </div>
        </div>
        <div style="float: left; width: 70%; display: block">
            <p>
                <button class="btn btn-info" type="button" data-toggle="collapse" data-target="#questionResponse"
                        aria-expanded="false" aria-controls="collapseExample">
                    Question-Response
                </button>
                <button class="btn btn-info" type="button" data-toggle="collapse" data-target="#pictureResponse"
                        aria-expanded="false" aria-controls="collapseExample">
                    Picture-Response
                </button>
                <button class="btn btn-info" type="button" data-toggle="collapse" data-target="#fillBlank"
                        aria-expanded="false" aria-controls="collapseExample">
                    Fill in the blank
                </button>
                <button class="btn btn-info" type="button" data-toggle="collapse" data-target="#multipleChoice"
                        aria-expanded="false" aria-controls="collapseExample">
                    Multiple choice
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

                    <div class="form-group">
                        <button type="button" class="btn btn-info" id="simpleQuestionSubmit">Submit</button>
                    </div>
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
                    <div class="form-group">
                        <button type="button" class="btn btn-info" id="pictureSubmit">Submit</button>
                    </div>
                </div>
            </div>
            <div class="collapse" id="fillBlank">
                <div class="card card-body">
                    <!-- Text input-->
                    <div class="form-group">
                        <label class="col-md-4 control-label" for="fillBlankText1">Question</label>
                        <div class="row">
                            <div class="col-md-8">
                                <input id="fillBlankText1" name="fillBlankText1" type="text"
                                       placeholder="Question before blank"
                                       class="form-control input-md" required>
                            </div>
                            <div class="col-md-8">
                                <input id="fillBlankText2" name="fillBlankText2" type="text"
                                       placeholder="Question after blank"
                                       class="form-control input-md" required>
                            </div>
                        </div>
                    </div>

                    <!-- Text input-->
                    <div class="form-group">
                        <label class="col-md-4 control-label" for="fillBlankAnswer">Answer</label>
                        <div class="col-md-8">
                            <input id="fillBlankAnswer" name="fillBlankAnswer" type="text" placeholder=""
                                   class="form-control input-md" required>
                        </div>
                    </div>
                    <div class="form-group">
                        <button type="button" class="btn btn-info" id="fillBlankSubmit">Submit</button>
                    </div>
                </div>
            </div>
            <div class="collapse" id="multipleChoice">
                <form id="multiple">
                    <div class="card card-body">
                        <!-- Text input-->
                        <div class="form-group">
                            <label class="col-md-4 control-label" for="multipleChoiceText">Question</label>
                            <div class="col-md-8">
                                <input id="multipleChoiceText" name="multipleChoiceText" type="text" placeholder=""
                                       class="form-control input-md" required>
                            </div>
                        </div>

                        <!-- Text input-->
                        <div class="form-group">
                            <label class="col-md-4 control-label" for="multipleCorrectAnswer">Correct Answer</label>
                            <div class="col-md-8">
                                <input id="multipleCorrectAnswer" name="multipleCorrectAnswer" type="text"
                                       placeholder=""
                                       class="form-control input-md" required>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-4 control-label" for="multipleWrongAnswer1">Wrong Answers</label>
                            <div class="col-md-8">
                                <input id="multipleWrongAnswer1" name="multipleWrongAnswer1" type="text" placeholder=""
                                       class="form-control input-md" required>
                                <input id="multipleWrongAnswer2" name="multipleWrongAnswer2" type="text" placeholder=""
                                       class="form-control input-md" required>
                                <input id="multipleWrongAnswer3" name="multipleWrongAnswer3" type="text" placeholder=""
                                       class="form-control input-md" required>
                            </div>
                        </div>
                        <div class="form-group">
                            <button type="button" class="btn btn-info" id="multipleChoiceSubmit">Submit</button>
                        </div>
                    </div>
                </form>
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
<!-- ***** Clients Area End ***** -->


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
            if (question === "" || answer === ""){
                toastr.options.timeOut = 100;
                toastr.error("Please, fill all fields");
            }else {
                var pictQuest = {};
                pictQuest['type'] = 'questionResponse';
                pictQuest['question'] = question;
                pictQuest['answer'] = answer;
                questionsList.push(pictQuest);
                checker(questionsList);
            }
        });


        $("#pictureSubmit").click(function () {
            var question = $('#pictureQuestionText').val();
            var answer = $('#pictureAnswerText').val();
            if (question === "" || answer === ""){
                toastr.options.timeOut = 100;
                toastr.error("Please, fill all fields");
            }else {
                var pictQuest = {};
                pictQuest['type'] = 'pictureResponse';
                pictQuest['question'] = question;
                pictQuest['answer'] = answer;
                questionsList.push(pictQuest);
                checker();
            }
        });
        $("#fillBlankSubmit").click(function () {
            var question = $('#fillBlankText1').val() + "_" + $('#fillBlankText2').val();
            var answer = $('#fillBlankAnswer').val();
            if (question === "" || answer === ""){
                toastr.options.timeOut = 100;
                toastr.error("Please, fill all fields");
            } else {
                var quest = {};
                quest['type'] = 'fillBlank';
                quest['question'] = question;
                quest['answer'] = answer;
                questionsList.push(quest);
                checker();
            }
        });
        $("#multipleChoiceSubmit").click(function () {
            var question = $('#multipleChoiceText').val();
            var wrongAnswer1 = $('#multipleWrongAnswer1').val();
            var wrongAnswer2 = $('#multipleWrongAnswer2').val();
            var wrongAnswer3 = $('#multipleWrongAnswer3').val();
            var correctAnswer = $('#multipleCorrectAnswer').val();
            if (question === "" || wrongAnswer1 === "" || wrongAnswer2 === "" || wrongAnswer3 === "" || correctAnswer === "") {
                toastr.options.timeOut = 100;
                toastr.error("Please, fill all fields");
            }else {
                var answer = correctAnswer + "," + wrongAnswer1 + "," + wrongAnswer2 + "," + wrongAnswer3;
                var quest = {};
                quest['type'] = 'multipleChoice';
                quest['question'] = question;
                quest['answer'] = answer;
                questionsList.push(quest);
                checker();
            }
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