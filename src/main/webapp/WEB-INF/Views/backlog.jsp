<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="../../resources/css/menu.css">
    <link rel="stylesheet" type="text/css" href="../../resources/css/backlog.css">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="../../resources/js/backlog.js"></script>

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

<button class="new_task_button" type="button">NEW TASK</button>
<div class="new_task">
    <input id="task_name" type="text" placeholder="Task name">
    <textarea id="task_description" placeholder="Task description"></textarea>
    <select id="task_type">
        <option value="bug">Bug</option>
        <option value="improvement">Improvement</option>
        <option value="new_feature">New Feature</option>
        <option value="content">Content</option>
    </select>
    <select id="task_priority">
        <option value="highest">Highest</option>
        <option value="high">High</option>
        <option value="medium">Medium</option>
        <option value="low">Low</option>
        <option value="lowest">Lowest</option>
    </select>
    <input id="votes" type="text" placeholder="Votes">
    <button class="create_task_button" type="button">CREATE TASK</button>
</div>

<div class="tasks">
    <h2>Tasks</h2>

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