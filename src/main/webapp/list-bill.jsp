<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, com.icbt.model.Bill" %>
<%
  List<Bill> bills = (List<Bill>) request.getAttribute("bills");
%>
<!DOCTYPE html>
<html>
<head>
  <title>All Bills</title>
  <style>
    body {
      font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
      background-color: #f5f7fa;
      padding: 40px;
      color: #333;
    }

    h2 {
      text-align: center;
      color: #444;
      margin-bottom: 30px;
      font-size: 28px;
    }

    .btn {
      display: inline-block;
      padding: 10px 16px;
      background-color: #3a7bd5;
      color: #fff;
      text-decoration: none;
      border-radius: 5px;
      font-size: 14px;
      transition: background-color 0.3s ease;
    }

    .btn:hover {
      background-color: #285ea8;
    }

    .btn-danger {
      background-color: #e74c3c;
    }

    .btn-danger:hover {
      background-color: #c0392b;
    }

    table {
      width: 95%;
      margin: auto;
      border-collapse: collapse;
      background-color: #ffffff;
      border-radius: 8px;
      overflow: hidden;
      box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
    }

    th, td {
      padding: 14px 18px;
      text-align: center;
      border-bottom: 1px solid #e0e0e0;
    }

    th {
      background-color: #3a7bd5;
      color: #fff;
      font-weight: normal;
      text-transform: uppercase;
      letter-spacing: 0.05em;
    }

    tr:hover {
      background-color: #f1f1f1;
    }

    .actions a {
      margin-right: 8px;
    }

    .actions form {
      display: inline;
    }

    .top-bar {
      text-align: center;
      margin-bottom: 25px;
    }

  </style>
</head>
<body>

<h2>Bill List</h2>

<div class="top-bar">
  <a class="btn" href="BillServlet?action=new">+ Create New Bill</a>
</div>

<table>
  <tr>
    <th>Bill ID</th>
    <th>Account Number</th>
    <th>Total Amount</th>
    <th>Bill Date</th>
    <th>Actions</th>
  </tr>

  <% if (bills != null && !bills.isEmpty()) {
    for (Bill bill : bills) {
  %>
  <tr>
    <td><%= bill.getBillId() %></td>
    <td><%= bill.getAccountNumber() %></td>
    <td><%= bill.getTotalAmount() %></td>
    <td><%= bill.getBillDate() %></td>
    <td class="actions">
      <a class="btn"
         href="BillServlet?action=edit&id=<%= bill.getBillId() %>&accountNumber=<%= bill.getAccountNumber() %>&totalAmount=<%= bill.getTotalAmount() %>&billDate=<%= bill.getBillDate() %>">
        Edit
      </a>
      <a class="btn btn-danger"
         href="BillServlet?action=delete&id=<%= bill.getBillId() %>"
         onclick="return confirm('Are you sure you want to delete this bill?');">
        Delete
      </a>
    </td>
  </tr>
  <%
    }
  } else {
  %>
  <tr>
    <td colspan="5" style="text-align:center;">No bills available.</td>
  </tr>
  <% } %>
</table>

</body>
</html>