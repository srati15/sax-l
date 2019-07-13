<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="dao.AnnouncementDao" %>
<%@ page import="dao.QuizDao" %>
<%@ page import="dao.UserDao" %>
<%@ page import="datatypes.user.User" %>
<%@ page import="enums.DaoType" %>
<%@ page import="enums.UserType" %>
<%@ page import="manager.DaoManager" %>
<%@ page import="dao.QuizResultDao" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="description" content="">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">


    <!-- Title -->
    <title>Sax-L - Quiz Website | Quizzes</title>

    <!-- Favicon -->
    <link rel="icon" href="img/core-img/favicon.ico">

    <!-- Core Stylesheet -->
    <link href="style.css" rel="stylesheet">
    <!-- Responsive CSS -->
    <link href="css/responsive.css" rel="stylesheet">
    <link href="css/toastr.css" rel="stylesheet">
    <link href="css/datatables.min.css" rel="stylesheet">

</head>

<body>

<%
    DaoManager manager = (DaoManager) request.getServletContext().getAttribute("manager");
    UserDao userDao = manager.getDao(DaoType.User);
    QuizDao quizDao = manager.getDao(DaoType.Quiz);
    QuizResultDao quizResultDao = manager.getDao(DaoType.QuizResult);
    pageContext.setAttribute("userDao", userDao);
    pageContext.setAttribute("quizResultDao", quizResultDao);
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

<!-- ***** Welcome Area Start ***** -->

<section class="mosh-aboutUs-area">
    <div class="container" id="createContainer">
        <div class="ui segment">
            <div class="huge ui teal ribbon label">
                Quizzes
            </div>
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
                        <td><a href="quiz-details?quizId=${quiz.id}">${quiz.quizName}</a>
                        </td>
                        <td>
                            <a href="user-profile?userid=${userDao.findById(quiz.authorId).id}">${userDao.findById(quiz.authorId).userName}</a>
                        </td>
                        <td>

                            <h:start quiz="${quiz}" buttonClass="mini ui primary button" styled="true"/>
                            <c:if test="${user.userType == UserType.Admin}">
                                <h:delete entityName="Quiz" actionServlet="DeleteQuizServlet"
                                          hiddenParameterName="deleteQuizId" hiddenParameterValue="${quiz.id}"/>
                                <c:if test="${quiz.timesDone != 0}">
                                    <form action="ClearQuizHistoryServlet" method="post" style="float:right">
                                        <button type="submit" class="mini ui orange button">
                                            <i class="fa fa-trash"></i> Clear History
                                        </button>
                                        <input type="hidden" name="clearedQuizId" value="${quiz.id}">
                                    </form>
                                </c:if>
                            </c:if>
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

    </div>
</section>
<!-- ***** Welcome Area End ***** -->

<!-- ***** Quiz Area Start ***** -->
<section class="mosh-clients-area section_padding_100 clearfix">
    <div class="container">

        <div class="ui vertical segments">

                <div class="huge ui teal ribbon label">
                    Create a Quiz
                </div>
                <div class="ui segment">
                    <h2 id="questionNum">Question N1</h2>
                    <div class="progress" id="progressBar">
                        <div class="progress-bar progress-bar-striped" id="progressLabel" role="progressbar"
                             aria-valuenow="0"
                             aria-valuemin="0" aria-valuemax="100" style="width:0">
                            0%
                        </div>
                    </div>
                </div>
                <div class="ui segment">
                    <div class="ui horizontal segments">
                        <div class="ui segment" id="collapseSegment">
                            <div class="blue ui buttons" id="uiButtons">
                                <button class="ui button" type="button" data-toggle="collapse"
                                        data-target="#questionResponse"
                                        aria-expanded="false" aria-controls="collapseExample">
                                    Question-Response
                                </button>
                                <button class="ui button" type="button" data-toggle="collapse"
                                        data-target="#pictureResponse"
                                        aria-expanded="false" aria-controls="collapseExample">
                                    Picture-Response
                                </button>
                                <button class="ui button" type="button" data-toggle="collapse" data-target="#fillBlank"
                                        aria-expanded="false" aria-controls="collapseExample">
                                    Fill in the blank
                                </button>
                                <button class="ui button" type="button" data-toggle="collapse"
                                        data-target="#multipleChoice"
                                        aria-expanded="false" aria-controls="collapseExample">
                                    Multiple choice
                                </button>
                            </div>

                            <div class="collapse" id="questionResponse">
                                <div class="card card-body">

                                    <!-- Text input-->
                                    <div class="form-group">
                                        <label class="col-md-4 control-label" for="simpleQuestionText">Question</label>
                                        <div class="col-md-8">
                                            <input id="simpleQuestionText" name="simpleQuestionText" type="text"
                                                   placeholder=""
                                                   class="form-control input-md" required>

                                        </div>
                                    </div>

                                    <!-- Text input-->
                                    <div class="form-group">
                                        <label class="col-md-4 control-label" for="simpleAnswerText">Answer</label>
                                        <div class="col-md-8">
                                            <input id="simpleAnswerText" name="simpleAnswerText" type="text"
                                                   placeholder=""
                                                   class="form-control input-md" required>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <button type="button" class="btn btn-info" id="simpleQuestionSubmit">Submit
                                        </button>
                                    </div>
                                </div>
                            </div>
                            <div class="collapse" id="pictureResponse">
                                <div class="card card-body">
                                    <!-- Text input-->
                                    <div class="form-group">
                                        <label class="col-md-4 control-label" for="pictureQuestionText">Image
                                            source</label>
                                        <div class="col-md-8">
                                            <input id="pictureQuestionText" name="pictureQuestionText" type="text"
                                                   placeholder=""
                                                   class="form-control input-md" required>

                                        </div>
                                    </div>

                                    <!-- Text input-->
                                    <div class="form-group">
                                        <label class="col-md-4 control-label" for="pictureAnswerText">Answer</label>
                                        <div class="col-md-8">
                                            <input id="pictureAnswerText" name="pictureAnswerText" type="text"
                                                   placeholder=""
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
                                            <input id="fillBlankAnswer" name="fillBlankAnswer" type="text"
                                                   placeholder=""
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
                                            <label class="col-md-4 control-label"
                                                   for="multipleChoiceText">Question</label>
                                            <div class="col-md-8">
                                                <input id="multipleChoiceText" name="multipleChoiceText" type="text"
                                                       placeholder=""
                                                       class="form-control input-md" required>
                                            </div>
                                        </div>

                                        <!-- Text input-->
                                        <div class="form-group">
                                            <label class="col-md-4 control-label" for="multipleCorrectAnswer">Correct
                                                Answer</label>
                                            <div class="col-md-8">
                                                <input id="multipleCorrectAnswer" name="multipleCorrectAnswer"
                                                       type="text"
                                                       placeholder=""
                                                       class="form-control input-md" required>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-4 control-label" for="multipleWrongAnswer1">Wrong
                                                Answers</label>
                                            <div class="col-md-8">
                                                <input id="multipleWrongAnswer1" name="multipleWrongAnswer1" type="text"
                                                       placeholder=""
                                                       class="form-control input-md" required>
                                                <input id="multipleWrongAnswer2" name="multipleWrongAnswer2" type="text"
                                                       placeholder=""
                                                       class="form-control input-md" required>
                                                <input id="multipleWrongAnswer3" name="multipleWrongAnswer3" type="text"
                                                       placeholder=""
                                                       class="form-control input-md" required>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <button type="button" class="btn btn-info" id="multipleChoiceSubmit">
                                                Submit
                                            </button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>


                        <div class="ui segment">
                            <div>
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

                                        <!-- Quiz Description-->
                                        <div class="form-group">
                                            <label for="quizDescription">Quiz Description</label>
                                            <textarea class="form-control" id="quizDescription" name="quizDescription"
                                                      rows="3"></textarea>
                                        </div>
                                        <!-- Text input-->
                                        <div class="form-group">
                                            <label for="quizname">Quiz Image URL</label>
                                            <div>
                                                <input id="quizImageUrl" name="quizImageUrl" type="text" placeholder=""
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
                                        <button class="ui positive animated button" type="submit" tabindex="0" id="finishCreationButton"
                                                style="display: none">
                                            <div class="visible content">Finish</div>
                                            <div class="hidden content">
                                                <i class="fa fa-arrow-right"></i>
                                            </div>
                                        </button>

                                    </fieldset>
                                </form>
                            </div>

                        </div>
                    </div>

                </div>

        </div>
    </div>
</section>
<!-- ***** Quiz Area End ***** -->


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
<script type="text/javascript" src="js/datatables.min.js"></script>

<script>
    $(document).ready(function () {
        $('#myTable').DataTable();
        $('.dataTables_length').addClass('bs-select');
    });
</script>

<jsp:include page="components/notifications.jsp"/>

myTable
</body>

</html>