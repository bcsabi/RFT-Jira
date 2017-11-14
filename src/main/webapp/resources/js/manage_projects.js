var focusedProjectName;

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
            focusedProjectName = $(this).get(0).firstChild.textContent;
        }
    });

}