<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, com.icbt.model.Customer" %>

<!DOCTYPE html>
<html>
<head>
    <title>Customer List</title>
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            background-color: #eef2f7;
            padding: 40px;
            color: #2c3e50;
        }

        h2 {
            text-align: center;
            margin-bottom: 30px;
        }

        .add-button {
            display: block;
            width: fit-content;
            margin: 0 auto 30px auto;
            padding: 12px 24px;
            background-color: #3498db;
            color: white;
            font-weight: bold;
            text-decoration: none;
            border-radius: 6px;
            font-size: 15px;
            transition: background-color 0.3s;
        }

        .add-button:hover {
            background-color: #2980b9;
        }

        table {
            width: 90%;
            margin: auto;
            border-collapse: collapse;
            background-color: #fff;
            border-radius: 8px;
            overflow: hidden;
            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
        }

        th, td {
            padding: 16px;
            text-align: left;
            border-bottom: 1px solid #ecf0f1;
        }

        th {
            background-color: #34495e;
            color: #ecf0f1;
        }

        tr:hover {
            background-color: #f9fbfd;
        }

        .actions a {
            padding: 6px 12px;
            margin-right: 8px;
            border-radius: 4px;
            font-size: 14px;
            text-decoration: none;
            transition: background-color 0.3s;
        }

        .edit-link {
            background-color: #2ecc71;
            color: white;
        }

        .edit-link:hover {
            background-color: #27ae60;
        }

        .delete-link {
            background-color: #e74c3c;
            color: white;
        }

        .delete-link:hover {
            background-color: #c0392b;
        }

        .back-link {
            display: block;
            text-align: center;
            margin-top: 40px;
            color: #2980b9;
            text-decoration: none;
            font-weight: bold;
        }

        .back-link:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>

<h2>Customer List</h2>

<a href="add-new-customer.jsp" class="add-button">+ Add New Customer</a>

<table>
    <tr>
        <th>Account Number</th>
        <th>Name</th>
        <th>Address</th>
        <th>Telephone</th>
        <th>Actions</th>
    </tr>
    <%
        List<Customer> customers = (List<Customer>) request.getAttribute("customers");
        if (customers != null) {
            for (Customer c : customers) {
    %>
    <tr>
        <td><%= c.getAccountNumber() %></td>
        <td><%= c.getName() %></td>
        <td><%= c.getAddress() %></td>
        <td><%= c.getTelephone() %></td>
        <td class="actions">
            <a class="edit-link" href="edit-customer.jsp?accountNumber=<%= c.getAccountNumber() %>&name=<%= c.getName() %>&address=<%= c.getAddress() %>&telephone=<%= c.getTelephone() %>">Edit</a>
            <a class="delete-link" href="CustomerServlet?mode=delete&accountNumber=<%= c.getAccountNumber() %>" onclick="return confirm('Are you sure you want to delete this customer?');">Delete</a>
        </td>
    </tr>
    <%
            }
        }
    %>
</table>

<a class="back-link" href="main-menu.jsp">← Back to Home</a>

</body>
</html>
