<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, com.icbt.model.Item" %>
<%
    List<Item> items = (List<Item>) request.getAttribute("items");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Item Catalog</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #eef2f7;
            margin: 0;
            padding: 0;
        }

        header {
            background-color: #343a40;
            padding: 20px;
            text-align: center;
            color: #fff;
        }

        .container {
            max-width: 1200px;
            margin: 30px auto;
            padding: 0 20px;
        }

        .top-actions {
            text-align: right;
            margin-bottom: 20px;
        }

        .top-actions .btn {
            padding: 10px 18px;
            background-color: #865baa;
            color: white;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            font-weight: bold;
            text-decoration: none;
            transition: background-color 0.3s;
        }

        .top-actions .btn:hover {
            background-color: #3b2c67;
        }

        .cards {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
            gap: 20px;
        }

        .card {
            background-color: white;
            border-radius: 10px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.05);
            padding: 20px;
            transition: transform 0.2s ease-in-out;
        }

        .card:hover {
            transform: translateY(-5px);
        }

        .card h3 {
            margin: 0 0 10px;
            color: #865baa;
        }

        .card p {
            margin: 5px 0;
            color: #555;
        }

        .actions {
            margin-top: 15px;
        }

        .actions a {
            display: inline-block;
            padding: 8px 14px;
            border-radius: 5px;
            text-decoration: none;
            font-size: 14px;
            font-weight: bold;
            margin-right: 8px;
        }

        .edit-btn {
            background-color: rgba(155, 104, 209, 0.95);
            color: white;
        }

        .edit-btn:hover {
            background-color: #865baa;
        }

        .delete-btn {
            background-color: #dc3545;
            color: white;
        }

        .delete-btn:hover {
            background-color: #b52a37;
        }

        .no-items {
            text-align: center;
            color: #666;
            margin-top: 60px;
        }
    </style>
</head>
<body>

<header>
    <h1>Item Catalog</h1>
</header>

<div class="container">

    <div class="top-actions">
        <a class="btn" href="add-item.jsp">+ Add New Item</a>
    </div>

    <% if (items != null && !items.isEmpty()) { %>
    <div class="cards">
        <% for (Item item : items) { %>
        <div class="card">
            <h3><%= item.getItem_name() %></h3>
            <p><strong>Item Code:</strong> <%= item.getItem_id() %></p>
            <p><strong>Description:</strong> <%= item.getItem_description() %></p>
            <p><strong>Price:</strong> Rs. <%= item.getUnit_price() %></p>
            <p><strong>Stock:</strong> <%= item.getStock_quantity() %> units</p>
            <div class="actions">
                <a class="edit-btn" href="edit-item.jsp?item_id=<%= item.getItem_id() %>&item_name=<%= item.getItem_name() %>&item_description=<%= item.getItem_description() %>&unit_price=<%= item.getUnit_price() %>&stock_quantity=<%= item.getStock_quantity() %>">Edit</a>
                <a class="delete-btn" href="ItemServlet?action=delete&id=<%= item.getItem_id() %>" onclick="return confirm('Are you sure you want to delete this item?');">Delete</a>
            </div>
        </div>
        <% } %>
    </div>
    <% } else { %>
    <div class="no-items">
        <h3>No items available in the inventory.</h3>
    </div>

    <% } %>
    <a class="logout-link" href="main-menu.jsp">Back to main menu</a>
</div>

</body>
</html>
