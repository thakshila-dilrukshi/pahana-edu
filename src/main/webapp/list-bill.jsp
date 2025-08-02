<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, com.icbt.model.Bill, com.icbt.model.BillItem" %>
<%@ page import="com.icbt.model.Item" %>
<%
  List<Bill> bills = (List<Bill>) request.getAttribute("bills");
  List<Item> allItems = (List<Item>) request.getAttribute("items");
  Map<Integer, List<BillItem>> billItemsMap = (Map<Integer, List<BillItem>>) request.getAttribute("billItemsMap");
%>
<!DOCTYPE html>
<html>
<head>
  <title>All Bills</title>
  <style>
    body {
      font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
      background-color: #f2f2f2;
      padding: 40px;
      color: #333;
    }

    h2 {
      text-align: center;
      color: #222;
      margin-bottom: 30px;
      font-size: 26px;
    }

    .top-bar {
      text-align: center;
      margin-bottom: 20px;
    }

    .btn {
      display: inline-block;
      padding: 8px 14px;
      background-color: rgba(155, 104, 209, 0.95);
      color: white;
      text-decoration: none;
      border-radius: 4px;
      font-size: 13px;
      transition: 0.2s ease;
      border: none;
    }

    .btn:hover {
      background-color: #865baa;
    }

    .btn-danger {
      background-color: #e74c3c;
    }

    .btn-danger:hover {
      background-color: #c0392b;
    }

    table {
      width: 100%;
      max-width: 1100px;
      margin: 0 auto;
      border-collapse: collapse;
      background-color: #fff;
      border-radius: 6px;
      box-shadow: 0 2px 8px rgba(0,0,0,0.05);
    }

    th {
      background-color: #745f9e;
      color: white;
      text-transform: uppercase;
      font-size: 12px;
      padding: 12px;
    }

    td {
      padding: 14px;
      border-bottom: 1px solid #eee;
      vertical-align: top;
      font-size: 14px;
    }

    tr:nth-child(even) {
      background-color: #fafafa;
    }

    .actions a {
      margin: 4px;
    }

    ul.item-list {
      padding: 0;
      margin: 0;
      list-style: none;
      display: grid;
      gap: 10px;
    }

    ul.item-list li.item-box {
      background-color: #f8f9fa;
      border: 1px solid #dcdcdc;
      padding: 10px 12px;
      border-radius: 5px;
      font-size: 13px;
      line-height: 1.5;
      text-align: left;
    }

    .item-box span {
      display: block;
      margin-bottom: 3px;
    }

    td[colspan] {
      text-align: center;
      font-style: italic;
      color: #888;
    }
  </style>
</head>
<body>

<h2>Bill List</h2>

<div class="top-bar">
  <a class="btn" href="BillServlet?action=new">+ Add New Bill</a>
</div>

<table>
  <tr>
    <th>Bill ID</th>
    <th>Account Number</th>
    <th>Total Amount</th>
    <th>Bill Date</th>
    <th>Items</th>
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
    <td>
      <%
        List<BillItem> items = billItemsMap.get(bill.getBillId());
        if (items != null && !items.isEmpty()) {
      %>
      <ul class="item-list">
        <% for (BillItem item : items) {
          String itemName = "";
          for (Item i : allItems) {
            if (i.getItem_id() == item.getItemId()) {
              itemName = i.getItemName();
              break;
            }
          }
        %>
        <li class="item-box">
          <span><strong>Item ID:</strong> <%= item.getItemId() %></span>
          <span><strong>Item Name:</strong> <%= itemName %></span>
          <span><strong>Quantity:</strong> <%= item.getQuantity() %></span>
          <span><strong>Price:</strong> <%= item.getPrice() %></span>
        </li>
        <% } %>
      </ul>
      <% } else { %>
      No items
      <% } %>
    </td>
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
  <% } } else { %>
  <tr>
    <td colspan="6">No bills available.</td>
  </tr>
  <% } %>
</table>
<a class="logout-link" href="main-menu.jsp">Back to main menu</a>

</body>
</html>
