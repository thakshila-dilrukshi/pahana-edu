
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page session="true" %>
<%@ page import ="com.icbt.model.User" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Pahana Edu - Main Menu</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #eef2f3;
            padding: 40px;
        }
        .menu {
            max-width: 400px;
            margin: auto;
            background-color: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 0 10px gray;
        }
        h2 {
            text-align: center;
            margin-bottom: 25px;
        }
        .menu a {
            display: block;
            padding: 12px;
            margin: 8px 0;
            text-decoration: none;
            background-color: #2e8b57;
            color: white;
            text-align: center;
            border-radius: 5px;
        }
        .menu a:hover {
            background-color: #246b46;
        }
    </style>
</head>
<body>

<div class="menu">
    <h2>Welcome, <%= user.getUsername() %></h2>
    <a href="add-new-customer.jsp">Add New Customer</a>
    <a href="CustomerServlet">Customer Details</a>
    <a href="ItemServlet">Manage Items</a>
    <a href="DisplayAccount.jsp">Display Account Details</a>
    <a href="GenerateBill.jsp">Calculate & Print Bill</a>
    <a href="Help.jsp">Help</a>
    <a href="LogoutServlet">Logout</a>
</div>

</body>
</html>
