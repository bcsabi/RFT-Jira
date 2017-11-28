<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="../../resources/css/menu.css">
    <link rel="stylesheet" type="text/css" href="../../resources/css/manage_task.css">

    <title>RFT Jira</title>
</head>
<body>

<ul>
    <li class="logout"><a class="logout" onclick="document.forms['logoutForm'].submit()">LOGOUT</a></li>
    <li class="user"><a class="user" href="profile">${firstName} ${lastName}</a></li>
    <li class="projects"><a class="projects" href="manage_projects">MANAGE PROJECTS</a></li>
    <li class="backlog"><a class="backlog" href="backlog?pid=${pid}">BACKLOG</a></li>
    <li class="taskboard"><a class="taskboard" href="taskboard?pid=${pid}">TASK BOARD</a></li>
</ul>

<h1 id="task_name_header">${currentTask.taskName}</h1>
<div class="task_details">
    <h2>Details</h2>
    <label for="task_name">Task name:</label>
    <input id="task_name" type="text" value="${currentTask.taskName}"><br>

    <label for="task_assigned_to">Assigned to:</label>
    <input id="task_assigned_to" type="text" placeholder="Assigned to..." value="${currentTask.assignedTo} "><br>

    <label for="task_type">Type:</label>
    <select id="task_type">
        <option selected = "selected" value="${currentTask.type}" style="display: none">${currentTask.type}</option>
        <option value="Bug">Bug</option>
        <option value="Improvement">Improvement</option>
        <option value="New feature">New feature</option>
        <option value="Content">Content</option>
    </select><br>

    <label for="task_priority">Priority</label>
    <select id="task_priority">
        <option select="selected" value="${currentTask.priority}" style="display: none">${currentTask.priority}</option>
        <option value="Highest">Highest</option>
        <option value="High">High</option>
        <option value="Medium">Medium</option>
        <option value="Low">Low</option>
        <option value="Lowest">Lowest</option>
    </select><br>

    <label for="task_status">Status:</label>
    <select id="task_status" name="taskStatus">
        <option selected = "selected" value="${currentTask.status}" style="display: none">${currentTask.status}</option>
        <option value="ToDo">To Do</option>
        <option value="Ready">Ready</option>
        <option value="In progress">In progress</option>
        <option value="Ready for test">Ready for Test</option>
        <option value="Done">Done</option>
    </select><br>

    <label for="task_votes">Points:</label>
    <input id="task_votes" type="text" value="${currentTask.votesPoint}"><br>

    <textarea id="task_description">${currentTask.description}</textarea><br>

    <button class="save_task_modify_button">SAVE CHANGES</button>
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