var currentTaskName;
var currentTaskDescription;
var currentTaskType;
var currentTaskPriority;
var currentTaskStatus;
var currentTaskVotes;

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

function  getTask(index){
    $( ".task" ).each(function( i ) {
        if(i == index) {
            currentTaskName = $(this).find("td:eq(1)").text();
            currentTaskDescription = $(this).find("td:eq(2)").text();
            currentTaskType = $(this).find("td:eq(3)").text();
            currentTaskPriority = $(this).find("td:eq(4)").text();
            currentTaskStatus = $(this).find("td:eq(5)").text();
            currentTaskVotes = $(this).find("td:eq(6)").text();
        }
    });

    $("#current_task_name").val(function () {
        return currentTaskName;
    });

    $("#current_task_description").val(function () {
        return currentTaskDescription;
    });

    $("#current_task_type").val(function () {
        return currentTaskType;
    });

    $("#current_task_priority").val(function () {
        return currentTaskPriority;
    });

    $("#current_task_status").val(function () {
        return currentTaskStatus;
    });

    $("#current_task_votes").val(function () {
        return currentTaskVotes;
    });

    $("#current_task_index").val(function () {
        return index;
    });

}