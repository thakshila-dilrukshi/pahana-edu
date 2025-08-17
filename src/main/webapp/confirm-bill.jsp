<%@ page import="com.icbt.model.Bill" %>
<%
    Bill bill = (Bill) request.getAttribute("bill");
    if (bill == null) {
        response.sendRedirect("BillServlet");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Confirm Bill</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, sans-serif;
            background: #ffffff; /* White background */
            color: #000; /* Black text for better readability */
            margin: 0;
            padding: 0;
        }
        .container {
            max-width: 600px;
            background: #ffffff; /* White container */
            margin: 60px auto;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.2);
            border: 1px solid #ccc; /* Light border for better print look */
        }
        h2 {
            text-align: center;
            color: #333;
            margin-bottom: 25px;
            font-size: 28px;
        }
        p {
            font-size: 18px;
            margin: 10px 0;
            padding: 10px;
            background: #f9f9f9; /* Light gray for section separation */
            border-radius: 6px;
            color: #000;
        }
        .btn-container {
            text-align: center;
            margin-top: 25px;
        }
        .btn {
            display: inline-block;
            padding: 12px 25px;
            font-size: 16px;
            border-radius: 6px;
            border: none;
            cursor: pointer;
            margin: 5px;
            transition: 0.3s ease;
        }
        .btn-confirm {
            background: #a569bd;
            color: white;
        }
        .btn-confirm:hover {
            background: #8e44ad;
        }
        .btn-back {
            background: rgba(155, 104, 209, 0.95);
            color: rgb(255, 255, 255);
        }
        .btn-back:hover {
            background: #bb8fce;
        }
        .btn-print {
            background: rgba(155, 104, 209, 0.95);
            color: rgb(255, 255, 255);
        }
        .btn-print:hover {
            background: rgba(155, 104, 209, 0.95);
        }
    </style>
</head>
<body>
<div class="container" id="billContainer">
    <h2>Confirm Bill</h2>
    <p><strong>Bill ID:</strong> <%= bill.getBillId() %></p>
    <p><strong>Account Number:</strong> <%= bill.getAccountNumber() %></p>
    <p><strong>Total Amount:</strong> Rs. <%= bill.getTotalAmount() %></p>
    <p><strong>Date:</strong> <%= bill.getBillDate() %></p>

    <form id="confirmForm" action="BillServlet" method="post">
        <input type="hidden" name="action" value="confirm" />
        <input type="hidden" name="billId" value="<%= bill.getBillId() %>" />
        <div class="btn-container">
            <a href="BillServlet" class="btn btn-back">Back to Main Menu</a>
            <button type="button" class="btn btn-print" onclick="printBill()">Print Bill</button>
        </div>
    </form>
</div>
<script>
    function confirmSave() {
        if (confirm('Are you sure you want to confirm and save this bill?')) {
            document.getElementById('confirmForm').submit();
        }
    }


    function printBill() {
        window.print();
    }
</script>
</body>
</html>