<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Login Page</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      background-color: #f0f0f0;
    }
    .login-container {
      width: 300px;
      margin: 100px auto;
      padding: 30px;
      background-color: white;
      box-shadow: 0px 0px 10px #ccc;
    }
    input[type="text"], input[type="password"] {
      width: 100%;
      padding: 10px;
      margin: 10px 0;
      box-sizing: border-box;
    }
    input[type="submit"] {
      width: 100%;
      padding: 10px;
      background-color: #4CAF50;
      color: white;
      border: none;
    }
    .error {
      color: red;
    }
  </style>
</head>
<body>

<div class="login-container">
  <h2>Login</h2>

  <%
    String error = request.getParameter("error");
    if (error != null) {
  %>
  <p class="error">Invalid username or password.</p>
  <%
    }
  %>

  <form action="LoginServlet" method="post">
    <label>Username:</label>
    <label>
      <input type="text" name="username" required>
    </label>

    <label>Password:</label>
    <label>
      <input type="password" name="password" required>
    </label>

    <input type="submit" value="Login">
  </form>
</div>

</body>
</html>