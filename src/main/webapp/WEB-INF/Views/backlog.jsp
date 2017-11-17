<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="../../resources/css/menu.css">
    <link rel="stylesheet" type="text/css" href="../../resources/css/backlog.css">

    <title>RFT Jira</title>
</head>
<body>

<ul>
    <li class="logout"><a class="logout" onclick="document.forms['logoutForm'].submit()">LOGOUT</a></li>
    <li class="user"><a class="user" href="profile">${firstName} ${lastName}</a></li>
    <li class="projects"><a class="projects" href="manage_projects">MANAGE PROJECTS</a></li>
    <li class="backlog"><a class="backlog" href="backlog">BACKLOG</a></li>
    <li class="taskboard"><a class="taskboard" href="taskboard">TASK BOARD</a></li>
</ul>

<div class="project_loader">
    <button class="projects_button">PROJECTS </button>
    <div class="projects_content">
        <a>Project 1</a>
        <a>Project 2</a>
        <a>Project 3</a>
    </div>
</div>

<div class="new_task">
    <button class="new_task_button" type="button">NEW TASK</button>
    <input id="taskname" type="text" placeholder="Task name">
    <textarea id="taskdescription" placeholder="Task description"></textarea>
    <input id="tasktype">

</div>

<div class="container">
    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <form id="logoutForm" method="POST" action="logout">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
    </c:if>
</div>


<!-- /container -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
</body>
</html>>