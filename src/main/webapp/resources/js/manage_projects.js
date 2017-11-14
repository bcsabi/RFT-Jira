var currentProjectName;
var currentProjectDescription;

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
    $( ".project" ).each(function( i ) {
        if(i == index) {
            currentProjectName = $(this).get(0).firstChild.textContent;
            currentProjectDescription = $(this).get(0).lastChild.textContent;
            console.log(currentProjectDescription);
        }
    });

    $("#current_project_name").val(function () {
        return currentProjectName;
    });

    $("#current_project_description").val(function () {
        return currentProjectDescription;
    });

}