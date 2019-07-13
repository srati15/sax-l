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
            toastr.options.timeOut = 10000;
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
            toastr.options.timeOut = 10000;
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
            toastr.options.timeOut = 10000;
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
            toastr.options.timeOut = 10000;
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
        toastr.options.timeOut = 10000;
        toastr.success("Question added");
        $('.input').attr('value','');
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
