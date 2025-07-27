<%@ page import="java.util.List" %>
<%@ page import="com.icbt.model.Item" %>
<%@ page import="com.icbt.model.Customer" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  List<Item> items = (List<Item>) request.getAttribute("items");
  List<Customer> customers = (List<Customer>) request.getAttribute("customers");
%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Add Bill</title>
  <style>
    body {
      font-family: Arial, sans-serif;
    }
    .container {
      max-width: 600px;
      margin: 30px auto;
    }
    h2 {
      text-align: center;
    }
    .form-group {
      margin-bottom: 15px;
    }
    select, input[type="text"], input[type="number"] {
      width: 100%;
      padding: 8px;
      margin-top: 5px;
    }
    button {
      width: 100%;
      padding: 12px;
      background-color: #333;
      color: white;
      border: none;
      font-size: 16px;
    }
    .add-more {
      background-color: #007bff;
      margin-top: 10px;
    }
    .item-block {
      border-bottom: 1px solid #ccc;
      padding-bottom: 10px;
      margin-bottom: 15px;
    }
    .price-display {
      font-weight: bold;
      color: green;
    }
  </style>
  <script>
    function addItemBlock() {
      const template = document.getElementById("item-template");
      const clone = template.cloneNode(true);
      clone.style.display = "block";
      clone.removeAttribute("id");
      document.getElementById("item-container").appendChild(clone);
    }

    function updatePrice(selectElement) {
      const selectedOption = selectElement.options[selectElement.selectedIndex];
      const unitPrice = parseFloat(selectedOption.getAttribute("data-price"));
      const quantityInput = selectElement.closest('.item-block').querySelector('input[name="quantities"]');
      const priceDisplay = selectElement.closest('.item-block').querySelector('.price-display');

      quantityInput.oninput = function () {
        const quantity = parseInt(this.value) || 0;
        priceDisplay.innerText = 'Total: Rs. ' + (unitPrice * quantity).toFixed(2);
      };

      // Trigger calculation immediately if quantity already filled
      quantityInput.dispatchEvent(new Event('input'));
    }
  </script>
</head>
<body>
<div class="container">
  <h2>Add Bill</h2>
  <form action="BillServlet?action=confirm" method="post">
    <!-- Customer Dropdown -->
    <div class="form-group">
      <label for="customer">Select Customer:</label>
      <select name="customerId" required>
        <option value="">-- Select Customer --</option>
        <%
          for (Customer customer : customers) {
        %>
        <option value="<%= customer.getAccountNumber() %>"><%= customer.getName() %></option>
        <%
          }
        %>
      </select>
    </div>

    <!-- Item Selection Area -->
    <div id="item-container">
      <!-- First Item Block -->
      <div class="item-block">
        <div class="form-group">
          <label>Select Item:</label>
          <select name="itemIds" onchange="updatePrice(this)" required>
            <option value="">-- Select Item --</option>
            <%
              for (Item item : items) {
            %>
            <option value="<%= item.getItem_id() %>" data-price="<%= item.getUnitPrice() %>">
              <%= item.getItemName() %>
            </option>
            <%
              }
            %>
          </select>
        </div>
        <div class="form-group">
          <label>Quantity:</label>
          <input type="number" name="quantities" min="1" required>
        </div>
        <div class="form-group price-display">Total: Rs. 0.00</div>
      </div>
      <button type="button" class="add-more" onclick="addItemBlock()">+ Add Another Item</button>
      <br><br>
      <button type="submit">Add Bill</button>
    </div>

    <!-- Hidden Template -->
    <div class="item-block" id="item-template" style="display: none;">
      <div class="form-group">
        <label>Select Item:</label>
        <select name="itemIds" onchange="updatePrice(this)" required>
          <option value="">-- Select Item --</option>
          <%
            for (Item item : items) {
          %>
          <option value="<%= item.getItem_id() %>" data-price="<%= item.getUnitPrice() %>">
            <%= item.getItemName() %>
          </option>
          <%
            }
          %>
        </select>
      </div>
      <div class="form-group">
        <label>Quantity:</label>
        <input type="number" name="quantities" min="1" required>
      </div>
      <div class="form-group price-display">Total: Rs. 0.00</div>
    </div>
  </form>
</div>
</body>
</html>
