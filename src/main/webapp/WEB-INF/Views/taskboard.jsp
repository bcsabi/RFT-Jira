<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="../../resources/css/menu.css">
    <link rel="stylesheet" type="text/css" href="../../resources/css/taskboard.css">

    <title>RFT Jira</title>
</head>
<body>

<ul>
    <li class="logout"><a class="logout" onclick="document.forms['logoutForm'].submit()">LOGOUT</a></li>
    <li class="user"><a class="user" href="profile">${firstName} ${lastName}</a></li>
    <li class="projects"><a class="projects" href="manage_projects">MANAGE PROJECTS</a></li>
    <li class="backlog"><a class="backlog" href="backlog?id=${id}">BACKLOG</a></li>
    <li class="taskboard"><a class="taskboard" href="taskboard?id=${id}">TASK BOARD</a></li>
</ul>

<h1 id="project_name_header">${projectName} task board</h1>
<div class="board">
    <h2 id="h01">To Do</h2>
    <div class="todo_board">
        <c:forEach items="${taskByToDo}" var="todo" varStatus="loop">
            <div class="todo_content">
                <h4>#${loop.index + 1} ${todo.taskName}</h4>
                <p>Points: ${todo.votesPoint}</p>
                <p>Type: ${todo.type}</p>
                <p>Priority: ${todo.priority}</p>
                <p>Assigned to: ${todo.assignedTo}</p>
            </div>
        </c:forEach>
    </div>
    <h2 id="h02">Ready</h2>
    <div class="ready_board">
        <c:forEach items="${taskByReady}" var="ready" varStatus="loop">
            <div class="ready_content">
                <h4>#${fn:length(taskByToDo) + loop.index + 1} ${ready.taskName}</h4>
                <p>Points: ${ready.votesPoint}</p>
                <p>Type: ${ready.type}</p>
                <p>Priority: ${ready.priority}</p>
                <p>Assigned to: ${ready.assignedTo}</p>
            </div>
        </c:forEach>
    </div>
    <h2 id="h03">In Progress</h2>
    <div class="in_progress_board">
        <c:forEach items="${taskByInProgress}" var="inprogress" varStatus="loop">
            <div class="in_progress_content">
                <h4>#${fn:length(taskByToDo) + fn:length(taskByReady) + loop.index + 1} ${inprogress.taskName}</h4>
                <p>Points: ${inprogress.votesPoint}</p>
                <p>Type: ${inprogress.type}</p>
                <p>Priority: ${inprogress.priority}</p>
                <p>Assigned to: ${inprogress.assignedTo}</p>
            </div>
        </c:forEach>
    </div>
    <h2 id="h04">Ready for test</h2>
    <div class="ready_for_test_board">
        <c:forEach items="${taskByReadyForTest}" var="readyfortest" varStatus="loop">
            <div class="ready_for_test_content">
                <h4>#${fn:length(taskByToDo) + fn:length(taskByReady) + fn:length(taskByInProgress) + loop.index + 1} ${readyfortest.taskName}</h4>
                <p>Points: ${readyfortest.votesPoint}</p>
                <p>Type: ${readyfortest.type}</p>
                <p>Priority: ${readyfortest.priority}</p>
                <p>Assigned to: ${readyfortest.assignedTo}</p>
            </div>
        </c:forEach>
    </div>
    <h2 id="h05">Done</h2>
    <div class="done_board">
        <c:forEach items="${taskByDone}" var="done" varStatus="loop">
            <div class="done_content">
                <h4>#${fn:length(taskByToDo) + fn:length(taskByReady) + fn:length(taskByInProgress) +fn:length(taskByReadyForTest)+ loop.index + 1}
                        ${done.taskName}</h4>
                <p>Points: ${done.votesPoint}</p>
                <p>Type: ${done.type}</p>
                <p>Priority: ${done.priority}</p>
                <p>Assigned to: ${done.assignedTo}</p>
            </div>
        </c:forEach>
    </div>
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