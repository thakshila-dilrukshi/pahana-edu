<%@ page import="com.icbt.model.Bill" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  Bill bill = (Bill) request.getAttribute("bill");
%>
<!DOCTYPE html>
<html>
<head>
  <title>Edit Bill</title>
</head>
<body>

<h2>Edit Bill</h2>

<form action="BillServlet" method="post">
  <input type="hidden" name="billId" value="<%= bill.getBillId() %>">

  <label>Account Number:</label>
  <input type="number" name="accountNumber" value="<%= bill.getAccountNumber() %>" required><br><br>

  <label>Total Amount (Rs.):</label>
  <input type="number" step="0.01" name="totalAmount" value="<%= bill.getTotalAmount() %>" required><br><br>

  <input type="submit" value="Update">
</form>

</body>
</html>
