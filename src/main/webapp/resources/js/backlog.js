$(document).ready(function() {

    $('.project_loader').click(function () {
       if ($('.projects_content').css('display') == 'none') {
           $('.projects_content').css('display', 'block');
           $('.projects_button').css("background-color", "#3cb0fd");
           $('.projects_butotn').css("border", "1px solid #3cb0fd");
           $('.new_task').css('display', 'none')
           $('.new_task_button').css("background-color", "#4CAF50");
           $('.new_task_button').css("border", "1px solid #4CAF50");

       }
       else {
           $('.projects_content').css('display', 'none');
           $('.projects_button').css("background-color", "#4CAF50");
           $('.projects_button').css("border", "1px solid #4CAF50");
       }
    });

    $('.new_task_button').click(function () {
        if ($('.new_task').css('display') == 'none' ) {
            $('.new_task').css('display', 'block');
            $('.new_task_button').css("background-color", "#3cb0fd");
            $('.new_task_button').css("border", "1px solid #3cb0fd");
            $('.projects_content').css('display', 'none');
            $('.projects_button').css("background-color", "#4CAF50");
            $('.projects_button').css("border", "1px solid #4CAF50");
        }
        else {
            $('.new_task').css('display', 'none');
            $('.new_task_button').css("background-color", "#4CAF50");
            $('.new_task_button').css("border", "1px solid #4CAF50");
        }
    });

    $('.create_task_button').click(function () {
        $('.new_task').css('display', 'none');
    })

});