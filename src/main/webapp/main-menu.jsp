<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page session="true" %>
<%@ page import="com.icbt.model.User" %>
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
            margin: 0;
            padding: 0;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(to right, #e0eafc, #cfdef3);
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .menu-container {
            background-color: #ffffff;
            padding: 40px;
            border-radius: 15px;
            box-shadow: 0 10px 25px rgba(0, 0, 0, 0.1);
            max-width: 500px;
            width: 100%;
        }

        h2 {
            text-align: center;
            margin-bottom: 30px;
            color: #333;
        }

        .menu-grid {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 15px;
        }

        .menu-grid a {
            text-align: center;
            padding: 15px 10px;
            background-color: rgba(155, 104, 209, 0.95);
            color: white;
            text-decoration: none;
            border-radius: 8px;
            transition: background-color 0.3s ease;
            font-weight: bold;
        }

        .menu-grid a:hover {
            background-color: #865baa;
        }

        .logout-button {
            background-color: rgba(155, 104, 209, 0.95) !important;
        }

        .logout-button:hover {
            background-color: #3b2c67 !important;
        }
    </style>
</head>
<body>

<div class="menu-container">
    <h2>Welcome, <%= user.getUsername() %></h2>
    <div class="menu-grid">
        <a href="add-new-customer.jsp">Add Customer</a>
        <a href="CustomerServlet">Customer Details</a>
        <a href="ItemServlet">Manage Items</a>

        <a href="display-account.jsp">Display Account Details</a>


        <a href="BillServlet">Print Bill</a>
        <a href="help.jsp">Help</a>
        <a href="LogoutServlet" class="logout-button" onclick="return confirm('Are you sure you want to logout?')">Logout</a>
    </div>
</div>

</body>
</html>
