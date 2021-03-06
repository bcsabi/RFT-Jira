<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="../../resources/css/menu.css">
    <link rel="stylesheet" type="text/css" href="../../resources/css/backlog.css">
    <link rel="stylesheet" href="backlog.jsp">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="../../resources/js/backlog.js"></script>

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

<h1 id="project_name_header">${projectName}</h1>
<button class="new_task_button" type="button">NEW TASK</button>
<div class="new_task">
    <form:form method="POST" modelAttribute="taskForm" class="create_task_body">
        <spring:bind path="taskName">
            <form:input id="task_name" type="text" path="taskName" placeholder="Task name" autofocus="true"></form:input>
            <form:errors id="error" path="taskName"></form:errors>
        </spring:bind>
        <spring:bind path="description" >
            <form:textarea id="task_description" type="text" path="description" placeholder="Task description" autofocus="true"></form:textarea>
            <form:errors id="error" path="description"></form:errors>
        </spring:bind>
        <spring:bind path="type">
            <form:select id="task_type" path="type">
                <form:option value="Bug">Bug</form:option>
                <form:option value="Improvement">Improvement</form:option>
                <form:option value="New feature">New feature</form:option>
                <form:option value="Content">Content</form:option>
            </form:select>
            <form:errors id="error" path="type"></form:errors>
        </spring:bind>
        <spring:bind path="priority">
            <form:select id="task_priority" path="priority">
                <form:option value="Highest">Highest</form:option>
                <form:option value="High">High</form:option>
                <form:option value="Medium">Medium</form:option>
                <form:option value="Low">Low</form:option>
                <form:option value="Lowest">Lowest</form:option>
            </form:select>
            <form:errors id="error" path="priority"></form:errors>
        </spring:bind>
        <spring:bind path="taskName" >
            <form:input id="votes" type="text" path="votesPoint" placeholder="Point" autofocus="true"></form:input>
            <form:errors id="error" path="votesPoint"></form:errors>
        </spring:bind>
        <button class="create_task_button" type="submit" name="create">CREATE TASK</button>
    </form:form>
</div>

<div class="progress" id="myProgress">
    <div class="progress-bar progress-bar-success" id="myBar" style="width: ${percentage}%">${percentage}%</div>
</div>

<div id="info">
    <p id="left">${all_task} number of tasks \ ${donec} done tasks</p> <p id="right">${allpoints} defined points \ ${donepoints} closed points</p>
</div><br>

<div class="tasks">
    <h2>Tasks</h2>
    <table class="task_table">
        <tr>
            <th class="column_name" id="id_column">ID</th>
            <th class="column_name" id="name_column">NAME</th>
            <th class="column_name" id="description_column">DESCRIPTION</th>
            <th class="column_name" id="type_column">TYPE</th>
            <th class="column_name" id="priority_column">PRIORITY</th>
            <th class="column_name" id="status_column">STATUS</th>
            <th class="column_name" id="votes_column">VOTES</th>
        </tr>
        <c:forEach items="${tasks}" var="task" varStatus="loop">
        <tr class="task" onclick="getTask(${loop.index})" id="${loop.index}" data-href="manage_task?pid=${pid}&taskID=${task.id}" >
            <td>${loop.index + 1}</td>
            <td><div id="task_name_data">${task.taskName}</div></td>
            <td><div id="task_description_data">${task.description}</div></td>
            <td>${task.type}</td>
            <td>${task.priority}</td>
            <td>${task.status}</td>
            <td>${task.votesPoint}</td>
        </tr>
        </c:forEach>
    </table>
</div>

<div class="modify_task">
    <form:form method="POST" modelAttribute="taskForm">
        <h2 id="modify_task">Modify task</h2>
        <input id="current_task_name" type="text" name="taskName" placeholder="Task Name">
        <textarea id="current_task_description" name="taskDescription" placeholder="Task Description"></textarea>
        <select id="current_task_type" name="taskType">
            <option value="bug">Bug</option>
            <option value="improvement">Improvement</option>
            <option value="new feature">New Feature</option>
            <option value="content">Content</option>
        </select>
        <select id="current_task_priority" name="taskPriority">
            <option value="highest">Highest</option>
            <option value="high">High</option>
            <option value="medium">Medium</option>
            <option value="low">Low</option>
            <option value="lowest">Lowest</option>
        </select>
        <select id="current_task_status" name="taskStatus">
            <option value="ToDo">To Do</option>
            <option value="Ready">Ready</option>
            <option value="In Progress">In Progress</option>
            <option value="Ready for test">Ready for Test</option>
            <option value="Done">Done</option>
        </select>
        <input id="current_task_votes" type="text" name="taskVotes" placeholder="Votes">
        <input id="current_task_assigned_to" type="text" placeholder="Assign to ... ">
        <input type="text" id="current_task_index" name="taskIndex" style="display: none">
        <button id="save_modify_button" type="submit" name="modify">SAVE CHANGES</button>
        <button id="delete_task_button" type="submit" name="delete">DELETE TASK</button>
    </form:form>
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