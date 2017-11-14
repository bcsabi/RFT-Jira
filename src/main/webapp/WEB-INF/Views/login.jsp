<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>s
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="../../resources/css/menu.css">
    <link rel="stylesheet" type="text/css" href="../../resources/css/login.css">

    <title>RFT Jira</title>
</head>
<body>

<ul>
    <li><a class="signup" href="signup">SIGN UP</a></li>
</ul>

<div class="container">
    <form method="POST" action="login"  >
        <div class="login_body">
            <p id="welcome">Welcome to our project for System Development Technologies!</p>
            <p id="loginto">Log in to start.</p>
            <div class="form-group ${error != null ? 'has-error' : ''}">
                <input name="username" type="text" class="form-control" placeholder="Username"
                       autofocus="true"/><br>
                <input name="password" type="password" class="form-control" placeholder="Password"/>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                <span id="logged_out">${message}</span>
                <span id="error">${error}</span>
            </div>
            <button id="login_button" type="submit">Log in</button><br>
            <p id="notmember">Not a member? <a class="signup_text" href="signup">Sign up</a> for an account.</p>
        </div>
    </form>
</div>
<!-- /container -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
</body>
</html>
