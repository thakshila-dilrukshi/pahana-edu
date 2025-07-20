<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, com.icbt.model.Customer" %>

<!DOCTYPE html>
<html>
<head>
    <title>Customer List</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f3f6f9;
            padding: 40px;
        }

        h2 {
            text-align: center;
            color: #333;
        }

        table {
            width: 90%;
            margin: auto;
            border-collapse: collapse;
            background-color: #fff;
            border-radius: 8px;
            overflow: hidden;
            box-shadow: 0px 0px 10px rgba(0,0,0,0.05);
        }

        th, td {
            padding: 15px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }

        th {
            background-color: #007bff;
            color: white;
        }

        tr:hover {
            background-color: #f1f1f1;
        }

        .actions a {
            margin-right: 10px;
            text-decoration: none;
            color: #007bff;
        }

        .actions a:hover {
            text-decoration: underline;
        }

        .back-link {
            display: block;
            text-align: center;
            margin-top: 30px;
            color: #007bff;
            text-decoration: none;
        }

        .back-link:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>

<h2>Customer List</h2>

<table border="1">
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
            <a href="edit-customer.jsp?accountNumber=<%= c.getAccountNumber() %>&name=<%= c.getName() %>&address=<%= c.getAddress() %>&telephone=<%= c.getTelephone() %>">Edit</a>
            <a href="delete-customer.jsp?accountNumber=<%= c.getAccountNumber() %>">Delete</a>
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
