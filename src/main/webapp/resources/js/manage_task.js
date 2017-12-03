$(document).ready(function() {

    var modal = document.getElementById('myModal');
    var delete_button = document.getElementById("delete_task_button");
    var no_button = document.getElementById("no_button");
    var span = document.getElementsByClassName("close")[0];

    delete_button.onclick = function() {
            modal.style.display = "block";
    }

    no_button.onclick = function () {
        modal.style.display = "none";
    }

    span.onclick = function() {
        modal.style.display = "none";
    }

    window.onclick = function(event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }

});