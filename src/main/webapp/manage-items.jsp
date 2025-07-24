<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, com.icbt.model.Item" %>
<%
    List<Item> items = (List<Item>) request.getAttribute("items");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Items List</title>
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

        .btn {
            display: inline-block;
            padding: 8px 14px;
            background-color: #007bff;
            color: #fff;
            text-decoration: none;
            border-radius: 5px;
            margin-bottom: 20px;
            transition: background-color 0.3s ease;
        }

        .btn:hover {
            background-color: #0056b3;
        }

        .btn-danger {
            background-color: #dc3545;
        }

        .btn-danger:hover {
            background-color: #b52a37;
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
            font-weight: bold;
        }

        .actions form {
            display: inline;
        }
    </style>
</head>
<body>

<h2>Item List</h2>

<div style="text-align: center;">
    <a class="btn" href="add-item.jsp">+ Add New Item</a>
</div>

<table>
    <tr>
        <th>Item Code</th>
        <th>Name</th>
        <th>Description</th>
        <th>Price</th>
        <th>Stock</th>
        <th>Actions</th>
    </tr>

    <% if (items != null) {
        for (Item item : items) {
    %>
    <tr>
        <td><%= item.getItem_id() %></td>
        <td><%= item.getItem_name() %></td>
        <td><%= item.getItem_description() %></td>
        <td><%= item.getUnit_price() %></td>
        <td><%= item.getStock_quantity() %></td>
        <td class="actions">
            <a class="btn" href="edit-item.jsp?item_id=<%= item.getItem_id() %>&item_name=<%= item.getItem_name() %>&item_description=<%= item.getItem_description() %>&unit_price=<%= item.getUnit_price() %>&stock_quantity=<%= item.getStock_quantity() %>">Edit</a>

            <a class="btn btn-danger"
               href="ItemServlet?action=delete&id=<%= item.getItem_id() %>"
               onclick="return confirm('Are you sure you want to delete this item?');">
                Delete
            </a>
        </td>

    </tr>
    <%
        }
    } else {
    %>
    <tr>
        <td colspan="5" style="text-align:center;">No items available.</td>
    </tr>
    <% } %>
</table>

</body>
</html>
