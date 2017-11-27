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
    <input id="task_type" type="text" value="${currentTask.type}"><br>

    <label for="task_priority">Priority</label>
    <input id="task_priority" type="text" value="${currentTask.priority}"><br>

    <label for="task_status">Status:</label>
    <input id="task_status" type="text" value="${currentTask.status}"><br>

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