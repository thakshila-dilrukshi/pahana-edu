<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Help - Pahana Edu</title>
    <style>
        body {
            font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
            margin: 0;
            padding: 0;
            background: linear-gradient(to right, #e0f7fa, #f1f8e9);
        }

        .header {
            background-color: #865baa;
            color: white;
            padding: 20px 0;
            text-align: center;
            font-size: 26px;
            font-weight: bold;
        }

        .container {
            max-width: 1000px;
            margin: 30px auto;
            padding: 40px;
            background-color: #fff;
            border-radius: 12px;
            box-shadow: 0 5px 20px rgba(0, 0, 0, 0.1);
        }

        .section {
            margin-bottom: 35px;
        }

        h2 {
            color: #865baa;
            border-bottom: 2px solid #e0e0e0;
            padding-bottom: 5px;
            margin-bottom: 15px;
        }

        ul {
            margin-left: 25px;
            line-height: 1.8;
        }

        .section p {
            font-size: 15.5px;
            color: #333;
        }

        .faq {
            background-color: #f9f9f9;
            padding: 20px;
            border-left: 5px solid rgba(155, 104, 209, 0.95);
            margin-top: 15px;
            border-radius: 8px;
        }

        .back-button {
            display: inline-block;
            background-color: #865baa;
            color: #fff;
            padding: 12px 22px;
            border-radius: 6px;
            text-decoration: none;
            font-weight: bold;
            margin-top: 20px;
            transition: background-color 0.3s;
        }

        .back-button:hover {
            background-color: #3b2c67;
        }

        .note {
            background-color: #fff9c4;
            padding: 15px;
            border-left: 5px solid #b5c80b;
            margin-bottom: 20px;
            border-radius: 8px;
        }
    </style>
</head>
<body>

<div class="header">
    Pahana Edu - Admin Help Center
</div>

<div class="container">

    <div class="section">
        <h2>Overview</h2>
        <p>
            Welcome to the Pahana Edu Admin Help Center. This guide will assist you in using the system effectively,
            whether you're managing customers, items, or printing bills.
        </p>
    </div>

    <div class="section">
        <h2>Main Menu Features</h2>
        <ul>
            <li><strong>Add New Customer:</strong> Use this option to register new customers with their name, address, and contact info.</li>
            <li><strong>Customer Details:</strong> View the list of registered customers. You can also update or delete their data here.</li>
            <li><strong>Manage Items:</strong> Add new products, change prices, or delete items no longer available.</li>
            <li><strong>Calculate & Print Bill:</strong> Create a new bill by selecting the customer and adding purchased items. Total will be calculated and can be printed directly.</li>
            <li><strong>Help:</strong> You're here now! Use this page to get instructions and troubleshoot problems.</li>
            <li><strong>Logout:</strong> Securely end your session.</li>
        </ul>
    </div>

    <div class="section">
        <h2>Step-by-Step Instructions</h2>
        <div class="note"><strong>Tip:</strong> Always double-check your data before clicking "Submit" or "Print".</div>
        <ul>
            <li><strong>To Add a Customer:</strong> Click "Add New Customer", fill in all fields (name, phone, etc.), then click "Save".</li>
            <li><strong>To View/Edit Customers:</strong> Go to "Customer Details", find the customer from the list, then choose "Edit" or "Delete".</li>
            <li><strong>To Add an Item:</strong> Click "Manage Items", then "Add New Item". Fill in the item name and price.</li>
            <li><strong>To Create a Bill:</strong>
                <ul>
                    <li>Select "Calculate & Print Bill".</li>
                    <li>Choose a customer and items with quantities.</li>
                    <li>The total is auto-calculated.</li>
                    <li>Click "Print" to generate a printable bill.</li>
                </ul>
            </li>
        </ul>
    </div>

    <div class="section">
        <h2>Frequently Asked Questions (FAQ)</h2>
        <div class="faq">
            <strong>Q:</strong> I added a customer, but can’t find them in the list?<br>
            <strong>A:</strong> Make sure you saved the form properly. Refresh the page or try checking the search filter.

            <br><br>
            <strong>Q:</strong> How do I update an item price?<br>
            <strong>A:</strong> Go to "Manage Items", click on the item you want to update, and edit its price.

            <br><br>
            <strong>Q:</strong> Can I delete a bill?<br>
            <strong>A:</strong> No, bills are usually locked after printing for auditing purposes. You may contact admin if needed.
        </div>
    </div>

    <a href="main-menu.jsp" class="back-button">← Back to Main Menu</a>
</div>

</body>
</html>
