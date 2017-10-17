<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" type="text/css" href="../../resources/menu.css">
<link rel="stylesheet" type="text/css" href="../../resources/login.css">

<html>
<head>
    <title>RFT Jira</title>
</head>
<body>

<ul>
    <li><a class="signup" href="signup">SIGN UP</a></li>
</ul>

<div class="login_body">
    <p id="welcome">Welcome to our SDT project!</p>
    <p id="loginto">Log in to start.</p>
    <input type="text" id="username" placeholder="username"><br>
    <input type="password" id="password" placeholder="password">
    <p id="remember"><input type="checkbox" id="password_checkbox">Remember my login on this computer.</p>
    <p id="notmember">Not a member? <a class="signup_text" href="signup">Sign up</a> for an account.</p>
    <button id="login_button">Log in</button>
</div>


</body>
</html>
