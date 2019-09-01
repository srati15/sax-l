<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <title>Sax-L Quiz Website</title>
    <link rel="stylesheet" href="css/font-awesome.min.css">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/sidebar.css">
    <link rel="stylesheet" href="css/timeline.css">
    <link rel="stylesheet" href="css/segment.css">
    <link rel="stylesheet" href="css/label.min.css">
    <link rel="stylesheet" href="css/button.min.css">

    <link rel="stylesheet" type="text/css" href="css/dataTables.bootstrap4.min.css"/>

</head>

<body>

<div class="wrapper">
    <!-- Sidebar Holder -->
    <jsp:include page="components/sidebar.jsp"/>
    <!-- Page Content Holder -->
    <div id="content">
        <jsp:include page="components/topbar.jsp"/>
        <section>
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
                        <c:forEach items="${requestScope.quizDao.findAll()}" var="quiz">
                            <tr>
                                <td>${i+1}
                                </td>
                                <td><a href="quiz-details?quizId=${quiz.id}">${quiz.quizName}</a>
                                </td>
                                <td>
                                    <a href="user-profile?userid=${requestScope.userDao.findById(quiz.authorId).id}">${requestScope.userDao.findById(quiz.authorId).userName}</a>
                                </td>
                                <td>

                                    <h:start quiz="${quiz}" buttonClass="mini ui primary button" styled="true"/>
                                    <c:if test="${sessionScope.user.userType == UserType.Admin}">
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

        <!-- ***** Quiz Area Start ***** -->
        <section class=" clearfix">
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
                                <div class="mini blue ui buttons" id="uiButtons">
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
        <jsp:include page="components/notifications.jsp"/>

    </div>
</div>

<script src="js/solid.js"></script>
<script src="js/fontawesome.js"></script>

<script src="js/jquery.min.js"></script>
<!-- Popper.JS -->
<script src="js/popper.min.js"></script>
<!-- Bootstrap JS -->
<script src="js/bootstrap.min.js"></script>
<script src="js/bootstrap.bundle.min.js"></script>
<script src="js/jquery.dataTables.min.js"></script>

<script src="js/dataTables.bootstrap4.min.js"></script>
<script src="js/quiz.js"></script>

<script type="text/javascript">
    $(document).ready(function () {
        $('#sidebarCollapse').on('click', function () {
            $('#sidebar').toggleClass('active');
            $(this).toggleClass('active');
        });
        $('.table').DataTable();
        $('.modal').appendTo("body");
        $('.toast').appendTo("body");
    });
</script>

<script src="//cdnjs.cloudflare.com/ajax/libs/jquery-form-validator/2.3.26/jquery.form-validator.min.js"></script>

</body>

</html>