<%@ page import="com.icbt.model.Bill" %>
<%
    Bill bill = (Bill) request.getAttribute("bill");
    if (bill == null) {
        response.sendRedirect("BillServlet");
        return;
    }
%>
<h2>Confirm Bill</h2>
<p>Bill ID: <%= bill.getBillId() %></p>
<p>Account Number: <%= bill.getAccountNumber() %></p>
<p>Total Amount: <%= bill.getTotalAmount() %></p>
<p>Date: <%= bill.getBillDate() %></p>

<form id="confirmForm" action="BillServlet" method="post">
    <input type="hidden" name="action" value="confirm" />
    <input type="hidden" name="billId" value="<%= bill.getBillDate() %>" />
    <button type="button" onclick="confirmSave()">Confirm</button>
</form>

<script>
    function confirmSave() {
        if (confirm('Are you sure you want to confirm and save this bill?')) {
            document.getElementById('confirmForm').submit();
        }
    }
</script>

