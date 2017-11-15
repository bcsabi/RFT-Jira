<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="../../resources/css/menu.css">
    <link rel="stylesheet" type="text/css" href="../../resources/css/manage_projects.css">
    <link rel="shortcut icon" href="">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="../../resources/js/manage_projects.js"></script>

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

<div class="my_projects">
    <h2 id="my_projects">My projects</h2>
    <c:forEach items="${projects}" var="project" varStatus="loop">
        <div class="project" onclick="getProject(${loop.index})" id="${loop.index}"><h4 id="projectNameText">${project.projectName}</h4><p id="descriptionText">${project.projectDescription}</p></div>
    </c:forEach>
</div>


<div class="new_project">
    <h2 id="new_project">New project</h2>
    <form:form method="POST" modelAttribute="projectForm" class="create_project_body">
        <spring:bind path="projectName">
            <form:input id="project_name" type="text" path="projectName"  placeholder="Project Name"
                        autofocus="true"></form:input>
            <form:errors id="error" path="projectName"></form:errors>
        </spring:bind>
        <spring:bind path="projectDescription">
            <form:textarea id="project_description" type="text" path="projectDescription"  placeholder="Project Description"
                        autofocus="true"></form:textarea>
            <form:errors id="error" path="projectDescription"></form:errors>
        </spring:bind>
        <button id="create_project_button" type="submit" name="create">CREATE PROJECT</button>


    </form:form>
</div>

<div class="modify_project">
    <form:form method="POST" modelAttribute="projectForm">
    <h2 id="modify_project">Modify project</h2>
    <input id="current_project_name" type="text"  placeholder="Project Name"
                autofocus="true" name="projectNamee"  path="projectName" >
    <textarea id="current_project_description" type="text"  placeholder="Project Description"
                   autofocus="true"></textarea>
    <div class="team">
        <!--<p>${firstName} ${lastName} (owner)</p>-->
    </div>

    <button id="delete_member_button" type="submit">DELETE MEMBER</button>
    <button id="add_member_button" type="submit">ADD MEMBER</button>
    <input id="username" type="text" placeholder="username" autofocus="true">
    <button id="plus" type="submit"></button>
    <button id="save_modify_button" type="submit">SAVE CHANGES</button>
    <button id="delete_project_button" type="submit" name="delete">DELETE PROJECT</button>
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