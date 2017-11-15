var currentProjectName;
var currentProjectDescription;
var currentProjectIndex;
var previousProjectIndex;

$(document).ready(function() {

    $('#add_member_button').click(function(){
        if($('#username').css('display') == 'none') {
            $('#username').css('display', 'inline-block');
            $('#plus').css('display', 'inline-block');
        }
        else {
            $('#username').css('display', 'none');
            $('#plus').css('display', 'none');
        }
    });

});

function  getProject(index){
    currentProjectIndex = index;
    $( ".project" ).each(function( i ) {
        if(i == currentProjectIndex) {
            currentProjectName = $(this).get(0).firstChild.textContent;
            currentProjectDescription = $(this).get(0).lastChild.textContent;
            $(this).css("background-color", "#3cb0fd");
            $(this).css("border", "1px solid #3cb0fd");
        }
        if(i == previousProjectIndex) {
            $(this).css("background-color", "#4CAF50");
            $(this).css("border", "1px solid #4CAF50");
        }
    });
    previousProjectIndex = currentProjectIndex;

    $("#current_project_name").val(function () {
        return currentProjectName;
    });

    $("#current_project_description").val(function () {
        return currentProjectDescription;
    });

    $("#current_project_index").val(function () {
        return currentProjectIndex;
    });

}