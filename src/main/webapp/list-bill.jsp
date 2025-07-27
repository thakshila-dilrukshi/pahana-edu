<%@ page import="java.util.List" %>
<%@ page import="com.icbt.model.Bill" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  List<Bill> bills = (List<Bill>) request.getAttribute("bills");
%>
<!DOCTYPE html>
<html>
<head>
  <title>Bill List</title>
</head>
<body>

<h2>All Bills</h2>
<a href="BillServlet?action=new">+ Create New Bill</a><br><br>

<table border="1" cellpadding="10">
  <tr>
    <th>ID</th>
    <th>Account Number</th>
    <th>Total (Rs.)</th>
    <th>Date</th>
    <th>Actions</th>
  </tr>
  <% for (Bill bill : bills) { %>
  <tr>
    <td><%= bill.getBillId() %></td>
    <td><%= bill.getAccountNumber() %></td>
    <td><%= bill.getTotalAmount() %></td>
    <td><%= bill.getBillDate() %></td>
    <td>
      <a href="BillServlet?action=edit&id=<%= bill.getBillId() %>">Edit</a> |
      <a href="BillServlet?action=delete&id=<%= bill.getBillId() %>" onclick="return confirm('Are you sure?')">Delete</a>
    </td>
  </tr>
  <% } %>
</table>

</body>
</html>
