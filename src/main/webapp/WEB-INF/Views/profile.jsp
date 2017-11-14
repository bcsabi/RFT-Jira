<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="../../resources/css/menu.css">
    <link rel="stylesheet" type="text/css" href="../../resources/css/profile.css">

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

<div class="profile">
    <h2 id="username">${firstName} ${lastName}</h2>
    <p id="email">${email}</p>
    <h3>Change password</h3>

    <form:form method="POST" modelAttribute="changePassForm" class="change_pass">
        <spring:bind path="new_password">
        <form:input path="new_password" name="new_password" type="password" placeholder="New password"/>
            <form:errors id="error" path="new_password"></form:errors>
        </spring:bind>
        <spring:bind path="confirm_password">
            <form:input path="confirm_password" name="confirm_password" type="password" placeholder="Confirm password"/>
            <form:errors id="error" path="confirm_password"></form:errors>
        </spring:bind>
        <spring:bind path="current_password">
            <form:input path="current_password" name="current_password" type="password" placeholder="Current password"/>
            <form:errors id="error" path="current_password"></form:errors>
        </spring:bind>
    <button id="save_button" type="submit">SAVE CHANGES</button>
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