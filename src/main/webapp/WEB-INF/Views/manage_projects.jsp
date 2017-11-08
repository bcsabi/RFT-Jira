<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="../../resources/menu.css">
    <link rel="stylesheet" type="text/css" href="../../resources/manage_projects.css">

    <title>RFT Jira</title>
</head>
<body>

<ul>
    <li class="logout"><a class="logout" onclick="document.forms['logoutForm'].submit()">LOGOUT</a></li>
    <li class="user"><a class="user" href="profil">${firstName} ${lastName}</a></li>
    <li class="projects"><a class="projects" href="manage_projects">MANAGE PROJECTS</a></li>
    <li class="backlog"><a class="backlog" href="backlog">BACKLOG</a></li>
    <li class="taskboard"><a class="taskboard" href="taskboard">TASK BOARD</a></li>
</ul>

<div class="new_project">
    <h2 id="new_project">New project</h2>
    <textarea id="project_name" type="text" placeholder="Project name"></textarea>
    <textarea id="project_description" type="text" placeholder="Project description"></textarea>
    <button id="create_project_button" type="submit">CREATE PROJECT</button>
</div>

<div class="my_projects">
    <h2 id="my_projects">My projects</h2>
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
</html>