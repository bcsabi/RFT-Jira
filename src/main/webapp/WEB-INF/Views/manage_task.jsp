<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="../../resources/css/menu.css">
    <link rel="stylesheet" type="text/css" href="../../resources/css/manage_task.css">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="../../resources/js/manage_task.js"></script>

    <title>RFT Jira</title>
</head>
<body>

<ul>
    <li class="logout"><a class="logout" onclick="document.forms['logoutForm'].submit()">LOGOUT</a></li>
    <li class="user"><a class="user" href="profile">${firstName} ${lastName}</a></li>
    <li class="projects"><a class="projects" href="manage_projects">MANAGE PROJECTS</a></li>
    <li class="backlog"><a class="backlog" href="backlog?pid=${pid}">BACKLOG</a></li>
    <li class="taskboard"><a class="taskboard" href="taskboard?pid=${pid}">TASK BOARD</a></li>
    <li class="team"><a class="team" href="team?pid=${pid}">TEAM</a></li>
</ul>

<h1 id="task_name_header">${currentTask.taskName}</h1>
<div class="task_details">
    <form:form method="POST" modelAttribute="taskForm">
        <input style="display: none" type="text" value="${taskID}" name="taskID">
        <h2>Details</h2>
        <label for="task_name">Task name:</label>
        <input id="task_name" name="taskName" type="text" value="${currentTask.taskName}"><br>

        <label for="task_assigned_to">Assigned to:</label>
        <select id="task_assigned_to"  name="assignedTo">
            <option selected="selected" value="${currentTask.assignedTo}" style="display: none">${currentTask.assignedTo}</option>
            <c:forEach items="${users}" var="user" varStatus="loop">
                <option value="${user.username}">${user.username}</option>
            </c:forEach>
        </select><br>

        <label for="task_type">Type:</label>
        <select id="task_type" name="taskType">
            <option selected = "selected" value="${currentTask.type}" style="display: none">${currentTask.type}</option>
            <option value="Bug">Bug</option>
            <option value="Improvement">Improvement</option>
            <option value="New feature">New feature</option>
            <option value="Content">Content</option>
        </select><br>

        <label for="task_priority">Priority</label>
        <select id="task_priority" name="taskPriority">
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
        <input id="task_votes" name="taskVotes" type="text" placeholder="Points" value="${currentTask.votesPoint}"><br>

        <textarea id="task_description" name="taskDescription">${currentTask.description}</textarea><br>

        <button class="save_task_modify_button" type="submit" name="modify">SAVE CHANGES</button><br>
        <button class="delete_task_button" id="delete_task_button" type="button">DELETE TASK</button>

        <div id="myModal" class="modal">
            <div class="modal-content">
                <span class="close">&times;</span>
                <p>Are you sure to delete the task?</p>
                <button id="yes_button" type="submit" name="delete">YES</button>
                <button id="no_button" type="button">NO</button>
            </div>
        </div>

    </form:form>
</div>

<div class="comment">
    <form:form method="POST" modelAttribute="commentForm">
        <spring:bind path="commentText">
            <form:textarea id="comment_text" type="text" path="commentText" placeholder="Write a comment..."></form:textarea>
            <form:errors id="error" path="commentText"></form:errors>
        </spring:bind>
        <button class="create_comment_button" type="submit" name="create_comment">CREATE COMMENT</button>
    </form:form>
    <td>
        <c:forEach items="${comments}" var="comment">
            <div class="loaded_comment">
                <p id="loaded_comment_username"><c:out value="${comment.username}"></c:out></p>
                <p id="loaded_comment_time"><c:out value="${comment.dateTime}"></c:out></p><br>
                <p id="loaded_comment_text"><c:out value="${comment.commentText}"></c:out></p>
            </div>
        </c:forEach>
    <td/>
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