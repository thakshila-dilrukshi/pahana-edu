<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add New Item</title>
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
            background-color: #007bff;
            color: #ffffff;
            border: none;
            border-radius: 6px;
            font-size: 16px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        input[type="submit"]:hover {
            background-color: #0056b3;
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
    <h2>Add New Item</h2>
    <form action="ItemServlet" method="post">
        <label for="name">Item Name:</label>
        <input type="text" id="name" name="item_name" required>

        <label for="description">Item Description:</label>
        <input type="text" id="description" name="item_description" required>

        <label for="price">Price (LKR):</label>
        <input type="number" id="price" name="unit_price" step="0.01" required>

        <label for="stock">Stock Quantity:</label>
        <input type="number" id="stock" name="stock_quantity" required>

        <input type="submit" value="Add Item">
    </form>

    <a class="back-link" href="ItemServlet">← Back to Item List</a>
</div>

</body>
</html>
