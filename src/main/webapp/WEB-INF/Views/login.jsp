<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>s
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="../../resources/menu.css">
    <link rel="stylesheet" type="text/css" href="../../resources/login.css">

    <title>RFT Jira</title>
</head>
<body>

<ul>
    <li><a class="signup" href="signup">SIGN UP</a></li>
</ul>

<div class="container">
    <form method="POST" action="login" class="login-body">
        <p id="welcome">Welcome to our SDT project!</p>
        <p id="loginto">Log in to start.</p>
        <div class="form-group ${error != null ? 'has-error' : ''}">
            <span>${message}</span>
            <input name="username" type="text" class="form-control" placeholder="Username"
                   autofocus="true"/>
            <input name="password" type="password" class="form-control" placeholder="Password"/>
            <span>${error}</span>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <h4 class="text-center"><a href="signup">Create an account</a></h4>
        </div>
        <p id="notmember">Not a member? <a class="signup_text" href="signup">Sign up</a> for an account.</p>
        <button id="login_button" type="submit">Log in</button>
    </form>
</div>
<!-- /container -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
</body>
</html>
