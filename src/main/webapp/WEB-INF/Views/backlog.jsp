<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <c:forEach items="${projects}" var="project">
            <a>${project.projectName}</a>
        </c:forEach>
    </div>
</div>

<button class="new_task_button" type="button">NEW TASK</button>
<div class="new_task">
    <form:form method="POST" modelAttribute="taskForm" class="create_task_body">
        <spring:bind path="taskName">
            <form:input id="task_name" type="text" path="taskName" placeholder="Task name" autofocus="true"></form:input>
            <form:errors id="error" path="taskName"></form:errors>
        </spring:bind>
        <spring:bind path="description" >
            <form:textarea id="task_description" type="text" path="description" placeholder="Task description" autofocus="true"></form:textarea>
            <form:errors id="error" path="taskName"></form:errors>
        </spring:bind>
        <spring:bind path="type">
            <form:select id="task_type" path="type">
                <form:option value="bug">Bug</form:option>
                <form:option value="improvement">Improvement</form:option>
                <form:option value="new_feature">New Feature</form:option>
                <form:option value="content">Content</form:option>
            </form:select>
        </spring:bind>
        <spring:bind path="priority">
            <form:select id="task_priority" path="priority">
                <form:option value="highest">Highest</form:option>
                <form:option value="high">High</form:option>
                <form:option value="medium">Medium</form:option>
                <form:option value="low">Low</form:option>
                <form:option value="lowest">Lowest</form:option>
            </form:select>
        </spring:bind>
        <spring:bind path="taskName" >
            <form:input id="votes" type="text" path="votesPoint" placeholder="Votes" autofocus="true"></form:input>
            <form:errors id="error" path="votesPoint"></form:errors>
        </spring:bind>
        <spring:bind path="project_name">
            <form:select id="task_project" path="project_name">
                <c:forEach items="${projects}" var="projectt">
                    <form:option value="${projectt.projectName}">${projectt.projectName}</form:option>
                </c:forEach>
            </form:select>
        </spring:bind>
        <button class="create_task_button" type="submit">CREATE TASK</button>
    </form:form>
</div>

<div class="tasks">
    <h2>Tasks</h2>
    <td>
        <c:forEach items="${tasks}" var="task">
            <br/>
            <c:out value="${task}"></c:out>
        </c:forEach>
    </td>
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