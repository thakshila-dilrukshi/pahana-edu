<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Login Page</title>
  <style>
    body {
      font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
      background-color: #e6ecf0;
      display: flex;
      justify-content: center;
      align-items: center;
      height: 100vh;
      margin: 0;
    }

    .login-container {
      background-color: #ffffff;
      padding: 35px 30px;
      border-radius: 8px;
      box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
      width: 340px;
    }

    .login-container h2 {
      margin-bottom: 20px;
      text-align: center;
      color: #333;
      font-size: 22px;
      font-weight: 600;
    }

    label {
      font-size: 14px;
      color: #333;
      display: block;
      margin-top: 10px;
      margin-bottom: 6px;
    }

    input[type="text"],
    input[type="password"] {
      width: 100%;
      padding: 10px 12px;
      border: 1px solid #ccc;
      border-radius: 4px;
      box-sizing: border-box;
      transition: border 0.3s ease;
    }

    input[type="text"]:focus,
    input[type="password"]:focus {
      border-color: rgba(155, 104, 209, 0.95);
      outline: none;
    }

    input[type="submit"] {
      width: 100%;
      padding: 10px;
      margin-top: 20px;
      background-color: #6c5b9e;
      color: white;
      border: none;
      border-radius: 4px;
      font-size: 15px;
      cursor: pointer;
      transition: background-color 0.3s ease;
    }

    input[type="submit"]:hover {
      background-color: #3b78c6;
    }

    .error {
      color: #e74c3c;
      font-size: 13px;
      margin-top: 10px;
      text-align: center;
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