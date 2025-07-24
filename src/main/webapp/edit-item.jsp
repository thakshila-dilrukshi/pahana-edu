<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // Get current item values from URL parameters
    String item_id = request.getParameter("item_id");
    String item_name = request.getParameter("item_name");
    String item_description = request.getParameter("item_description");
    String unit_price = request.getParameter("unit_price");
    String stock_quantity = request.getParameter("stock_quantity");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Item</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #eef2f7;
            padding: 40px;
        }

        .form-container {
            width: 450px;
            margin: auto;
            background-color: #ffffff;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0px 0px 15px rgba(0,0,0,0.1);
        }

        h2 {
            text-align: center;
            color: #333333;
            margin-bottom: 25px;
        }

        label {
            display: block;
            margin-bottom: 6px;
            font-weight: bold;
            color: #444;
        }

        input[type="text"],
        input[type="number"] {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 6px;
            margin-bottom: 18px;
            font-size: 14px;
            box-sizing: border-box;
        }

        input[type="submit"] {
            width: 100%;
            padding: 12px;
            background-color: #28a745;
            color: #ffffff;
            border: none;
            border-radius: 6px;
            font-size: 16px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        input[type="submit"]:hover {
            background-color: #218838;
        }

        .back-link {
            display: block;
            text-align: center;
            margin-top: 20px;
            color: #007bff;
            text-decoration: none;
        }

        .back-link:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>

<div class="form-container">
    <h2>Edit Item</h2>
    <form action="ItemServlet" method="post">
        <input type="hidden" name="item_id" value="<%= item_id != null ? item_id : "" %>">

        <label for="name">Item Name:</label>
        <input type="text" id="name" name="item_name" value="<%= item_name != null ? item_name : "" %>" required>

        <label for="description">Item Description:</label>
        <input type="text" id="description" name="item_description" value="<%= item_description != null ? item_description : "" %>" required>

        <label for="price">Unit Price (LKR):</label>
        <input type="number" id="price" name="unit_price" step="0.01" value="<%= unit_price != null ? unit_price : "" %>" required>

        <label for="stock">Stock Quantity:</label>
        <input type="number" id="stock" name="stock_quantity" value="<%= stock_quantity != null ? stock_quantity : "" %>" required>

        <input type="submit" value="Update Item">
    </form>

    <a class="back-link" href="manage-items.jsp">← Back to Item List</a>
</div>

</body>
</html>
