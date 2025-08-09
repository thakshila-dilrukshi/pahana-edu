<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.icbt.model.Customer" %>
<%@ page import="com.icbt.dao.CustomerDAO" %>
<%
    String accountNumberStr = request.getParameter("accountNumber");
    Customer customer = null;
    if (accountNumberStr != null && !accountNumberStr.trim().isEmpty()) {
        try {
            int accountNumber = Integer.parseInt(accountNumberStr);
            CustomerDAO dao = new CustomerDAO();
            customer = dao.getCustomerById(accountNumber);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid account number format.");
        }
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Display Account Details</title>
    <style>
        /* ==== RESET ==== */
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        /* ==== BODY ==== */
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #e3f2fd, #ffffff);
            color: #222;
            padding: 40px 10px;
        }

        /* ==== CONTAINER ==== */
        .container {
            max-width: 800px;
            margin: auto;
        }

        /* ==== CARD STYLE ==== */
        .card {
            background: #ffffff;
            padding: 40px 30px;
            border-radius: 10px;
            box-shadow: 0px 10px 25px rgba(0,0,0,0.1);
            border: 1px solid #f0f0f0;
        }

        /* ==== HEADING ==== */
        h2 {
            text-align: left;
            font-size: 26px;
            font-weight: 700;
            color: #1976d2;
            border-bottom: 2px solid #1976d2;
            padding-bottom: 8px;
            margin-bottom: 25px;
        }

        /* ==== FORM ==== */
        form {
            display: grid;
            grid-template-columns: 1fr auto;
            gap: 12px;
            margin-bottom: 25px;
        }

        input[type="text"] {
            padding: 14px 12px;
            border-radius: 5px;
            border: 1px solid #bbb;
            font-size: 15px;
            transition: all 0.3s ease;
        }

        input[type="text"]:focus {
            border-color: #1976d2;
            box-shadow: 0 0 8px rgba(25,118,210,0.3);
            outline: none;
        }

        input[type="submit"] {
            padding: 14px 20px;
            background: #1976d2;
            color: white;
            border: none;
            border-radius: 5px;
            font-weight: bold;
            cursor: pointer;
            transition: background 0.3s ease;
        }

        input[type="submit"]:hover {
            background: #0d47a1;
        }

        /* ==== RESULT BOX ==== */
        .result {
            background: #f9fbfd;
            padding: 20px;
            border-left: 5px solid #1976d2;
            border-radius: 6px;
        }

        .result p {
            margin: 6px 0;
            font-size: 15px;
            line-height: 1.4;
        }

        /* ==== ERROR MESSAGE ==== */
        .error {
            text-align: left;
            background: #ffebee;
            color: #c62828;
            padding: 10px;
            border-left: 5px solid #c62828;
            border-radius: 6px;
            margin-bottom: 15px;
        }

        /* ==== BACK BUTTON ==== */
        .back-home {
            margin-top: 25px;
            text-align: right;
        }

        .back-home a {
            display: inline-block;
            padding: 10px 15px;
            background: #455a64;
            color: white;
            border-radius: 5px;
            text-decoration: none;
            transition: background 0.3s ease;
        }

        .back-home a:hover {
            background: #263238;
        }

    </style>
</head>
<body>

<div class="container">
    <div class="card">
        <h2>🔍 Search Customer Account</h2>

        <form method="get" action="display-account.jsp">
            <input type="text" name="accountNumber" id="accountNumber" placeholder="Enter Account Number" required>
            <input type="submit" value="Search">
        </form>

        <%
            String error = (String) request.getAttribute("error");
            if (error != null) {
        %>
        <p class="error"><%= error %></p>
        <%
            }
            if (customer != null) {
        %>
        <div class="result">
            <p><strong>Account Number:</strong> <%= customer.getAccountNumber() %></p>
            <p><strong>Name:</strong> <%= customer.getName() %></p>
            <p><strong>Address:</strong> <%= customer.getAddress() %></p>
            <p><strong>Telephone:</strong> <%= customer.getTelephone() %></p>
        </div>
        <%
        } else if (accountNumberStr != null && error == null) {
        %>
        <p class="error">No customer found with the given account number.</p>
        <%
            }
        %>

        <div class="back-home">
            <a href="main-menu.jsp">← Back to Home</a>
        </div>
    </div>
</div>

</body>
</html>
