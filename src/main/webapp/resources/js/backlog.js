$(document).ready(function() {

    $('.new_task_button').click(function () {
        if ($('.new_task').css('display') == 'none' ) {
            $('.new_task').css('display', 'block');
            $('.new_task_button').css("background-color", "#3cb0fd");
            $('.new_task_button').css("border", "1px solid #3cb0fd");
            $('.tasks').css('display','none');
            $('.modify_task').css('display', 'none');
        }
        else {
            $('.new_task').css('display', 'none');
            $('.tasks').css('display','inline-block');
            $('.modify_task').css('display', 'inline-block');
            $('.new_task_button').css("background-color", "#4CAF50");
            $('.new_task_button').css("border", "1px solid #4CAF50");
        }
    });
});