<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, com.icbt.model.*" %>
<%
  Bill bill = (Bill) request.getAttribute("bill");
  List<BillItem> billItems = (List<BillItem>) request.getAttribute("billItems");
  List<Item> items = (List<Item>) request.getAttribute("items");
  List<Customer> customers = (List<Customer>) request.getAttribute("customers");
%>
<!DOCTYPE html>
<html>
<head>
  <title>Edit Bill</title>
  <style>
    body {
      background-color: #f0f2f5;
      font-family: 'Segoe UI', sans-serif;
    }

    h2 {
      text-align: center;
      margin-top: 25px;
    }

    .form-container {
      background-color: #fff;
      max-width: 700px;
      margin: 30px auto;
      padding: 30px;
      border-radius: 10px;
      box-shadow: 0 0 15px rgba(0,0,0,0.1);
    }

    label {
      font-weight: 600;
      display: block;
      margin-bottom: 5px;
    }

    select, input {
      width: 100%;
      padding: 10px;
      margin-bottom: 15px;
      border-radius: 5px;
      border: 1px solid #ccc;
      font-size: 15px;
    }

    .item-row {
      border: 1px solid #ddd;
      padding: 15px;
      margin-bottom: 15px;
      border-radius: 6px;
      background-color: #fafafa;
    }

    .remove-btn {
      background-color: #103aa7;
      color: white;
      padding: 8px 14px;
      border: none;
      border-radius: 6px;
      cursor: pointer;
    }

    .remove-btn:hover {
      background-color: #c82333;
    }

    .add-btn, .submit-btn {
      background-color: #007bff;
      color: white;
      padding: 12px 20px;
      border: none;
      border-radius: 6px;
      cursor: pointer;
      font-size: 15px;
      margin-right: 10px;
    }

    .add-btn:hover, .submit-btn:hover {
      background-color: #0056b3;
    }

    .btn-group {
      margin-top: 20px;
      text-align: right;
    }
  </style>
</head>
<body>

<h2>Edit Bill - ID: <%= bill.getBillId() %></h2>

<div class="form-container">
  <form action="BillServlet" method="post" onsubmit="return prepareFormBeforeSubmit()">
    <input type="hidden" name="formAction" value="update" />
    <input type="hidden" name="billId" value="<%= bill.getBillId() %>" />

    <label for="customer_id">Select Customer:</label>
    <select name="customer_id" id="customer_id" required>
      <option value="">-- Select Customer --</option>
      <% for (Customer customer : customers) { %>
      <option value="<%= customer.getAccountNumber() %>"
              <%= customer.getAccountNumber() == bill.getAccountNumber() ? "selected" : "" %>>
        <%= customer.getName() %>
      </option>
      <% } %>
    </select>

    <h3>Items</h3>
    <div id="itemContainer">
      <% for (BillItem billItem : billItems) { %>
      <div class="item-row">
        <label>Select Item:</label>
        <select name="itemId" onchange="setPriceAndTotal(this)" required>
          <option value="">-- Select Item --</option>
          <% for (Item item : items) { %>
          <option value="<%= item.getItem_id() %>" data-price="<%= item.getUnit_price() %>"
                  <%= item.getItem_id() == billItem.getItemId() ? "selected" : "" %>>
            <%= item.getItem_name() %>
          </option>
          <% } %>
        </select>

        <label>Quantity:</label>
        <input type="number" name="quantity" min="1" value="<%= billItem.getQuantity() %>" oninput="updateTotal(this)" required>

        <label>Unit Price:</label>
        <input type="number" name="price" step="0.01" min="0" value="<%= billItem.getPrice() %>" required>

        <label>Total:</label>
        <input type="number" name="total" step="0.01" min="0" value="<%= billItem.getQuantity() * billItem.getPrice() %>" readonly>

        <button type="button" class="remove-btn" onclick="removeRow(this)">Remove</button>
      </div>
      <% } %>
    </div>

    <div class="btn-group">
      <button type="button" class="add-btn" onclick="addRow()">Add Another Item</button>
      <button type="submit" class="submit-btn">Update Bill</button>
    </div>
  </form>
</div>

<!-- JavaScript -->
<script>
  function addRow() {
    const template = document.getElementById("item-template");
    const clone = template.content.cloneNode(true);
    document.getElementById("itemContainer").appendChild(clone);
  }

  function removeRow(button) {
    button.closest(".item-row").remove();
  }

  function setPriceAndTotal(selectElement) {
    const option = selectElement.options[selectElement.selectedIndex];
    const price = option.getAttribute("data-price");
    const row = selectElement.closest(".item-row");
    const priceInput = row.querySelector('input[name="price"]');
    const quantityInput = row.querySelector('input[name="quantity"]');
    const totalInput = row.querySelector('input[name="total"]');

    if (price) {
      priceInput.value = parseFloat(price).toFixed(2);
      const qty = parseInt(quantityInput.value) || 0;
      totalInput.value = (qty * parseFloat(price)).toFixed(2);
    }
  }

  function updateTotal(quantityInput) {
    const row = quantityInput.closest(".item-row");
    const price = parseFloat(row.querySelector('input[name="price"]').value) || 0;
    const qty = parseInt(quantityInput.value) || 0;
    row.querySelector('input[name="total"]').value = (qty * price).toFixed(2);
  }

  function prepareFormBeforeSubmit() {
    const itemIds = document.getElementsByName("itemId");
    const quantities = document.getElementsByName("quantity");
    const prices = document.getElementsByName("price");

    for (let i = 0; i < itemIds.length; i++) {
      itemIds[i].name = "itemId[]";
      quantities[i].name = "quantity[]";
      prices[i].name = "price[]";
    }
    return true;
  }
</script>

<!-- Template for New Item Row -->
<template id="item-template">
  <div class="item-row">
    <label>Select Item:</label>
    <select name="itemId" onchange="setPriceAndTotal(this)" required>
      <option value="">-- Select Item --</option>
      <% for (Item item : items) { %>
      <option value="<%= item.getItem_id() %>" data-price="<%= item.getUnit_price() %>">
        <%= item.getItem_name() %>
      </option>
      <% } %>
    </select>

    <label>Quantity:</label>
    <input type="number" name="quantity" min="1" value="1" oninput="updateTotal(this)" required>

    <label>Unit Price:</label>
    <input type="number" name="price" step="0.01" min="0" required>

    <label>Total:</label>
    <input type="number" name="total" step="0.01" min="0" readonly>

    <button type="button" class="remove-btn" onclick="removeRow(this)">Remove</button>
  </div>
</template>

</body>
</html>
