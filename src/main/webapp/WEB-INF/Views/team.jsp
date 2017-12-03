<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="../../resources/css/menu.css">
    <link rel="stylesheet" type="text/css" href="../../resources/css/team.css">

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
<div class="add_member">
    <select class="users">
        <option value="" disabled selected style="display: none">Select user to add...</option>
    </select>
    <button id="add_member_button" type="submit">ADD MEMBER</button>
</div>
<div class="members">
    <h2 id="members">Members</h2>
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