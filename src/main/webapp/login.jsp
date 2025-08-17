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
      padding: 8px;
      border-radius: 4px;
      background-color: #fdf2f2;
      border: 1px solid #fecaca;
    }

    .validation-error {
      color: #f39c12;
      font-size: 13px;
      margin-top: 10px;
      text-align: center;
      padding: 8px;
      border-radius: 4px;
      background-color: #fef9e7;
      border: 1px solid #fdeaa7;
    }

    .system-error {
      color: #8e44ad;
      font-size: 13px;
      margin-top: 10px;
      text-align: center;
      padding: 8px;
      border-radius: 4px;
      background-color: #f4f1f8;
      border: 1px solid #d5b8e7;
    }

    .auth-error {
      color: #e74c3c;
      font-size: 13px;
      margin-top: 10px;
      text-align: center;
      padding: 8px;
      border-radius: 4px;
      background-color: #fdf2f2;
      border: 1px solid #fecaca;
    }

    .error-icon {
      margin-right: 5px;
    }
  </style>

</head>
<body>

<div class="login-container">
  <h2>Login</h2>

  <%
    String errorType = (String) request.getAttribute("error_type");
    String errorMessage = (String) request.getAttribute("error_message");
    String preservedUsername = (String) request.getAttribute("preserved_username");
    
    if (errorMessage != null && !errorMessage.trim().isEmpty()) {
      String cssClass = "error";
      String icon = "⚠️";
      
      if ("validation_error".equals(errorType)) {
        cssClass = "validation-error";
        icon = "⚠️";
      } else if ("system_error".equals(errorType)) {
        cssClass = "system-error";
        icon = "🔧";
      } else if ("auth_error".equals(errorType)) {
        cssClass = "auth-error";
        icon = "🔒";
      }
  %>
  <div class="<%= cssClass %>">
    <span class="error-icon"><%= icon %></span>
    <%= errorMessage %>
  </div>
  <%
    }
  %>

  <form action="LoginServlet" method="post" onsubmit="return validateForm()">
    <label for="username">Username:</label>
    <input type="text" id="username" name="username" 
           value="<%= preservedUsername != null ? preservedUsername : "" %>" 
           required>

    <label for="password">Password:</label>
    <input type="password" id="password" name="password" required>

    <input type="submit" value="Login">
  </form>
</div>

<script>
function validateForm() {
  var username = document.getElementById('username').value.trim();
  var password = document.getElementById('password').value.trim();
  
  if (username === '') {
    alert('Please enter a username');
    document.getElementById('username').focus();
    return false;
  }
  
  if (password === '') {
    alert('Please enter a password');
    document.getElementById('password').focus();
    return false;
  }
  
  return true;
}

// Clear error messages when user starts typing
document.getElementById('username').addEventListener('input', function() {
  var errorDiv = document.querySelector('.error, .validation-error, .system-error, .auth-error');
  if (errorDiv) {
    errorDiv.style.display = 'none';
  }
});

document.getElementById('password').addEventListener('input', function() {
  var errorDiv = document.querySelector('.error, .validation-error, .system-error, .auth-error');
  if (errorDiv) {
    errorDiv.style.display = 'none';
  }
});
</script>

</body>
</html>