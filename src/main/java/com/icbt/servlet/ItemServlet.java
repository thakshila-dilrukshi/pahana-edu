package com.icbt.servlet;

import com.icbt.model.Customer;
import com.icbt.model.Item;
import com.icbt.service.CustomerService;
import com.icbt.service.ItemService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/ItemServlet")

public class ItemServlet extends HttpServlet  {
    private ItemService itemService = new ItemService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String item_idStr = request.getParameter("item_id");
        String item_name = request.getParameter("item_name");
        String item_description = request.getParameter("item_description");
        Double unit_price = Double.valueOf(request.getParameter("unit_price"));
        Double stock_quantity = Double.valueOf(request.getParameter("stock_quantity"));

        Item item = new Item();
        item.setItem_name(item_name);
        item.setItem_description(item_description);
        item.setUnit_price(unit_price);
        item.setStock_quantity(stock_quantity);

        boolean success;

        if (item_idStr == null || item_idStr.isEmpty()) {
            // ADD mode
            success = itemService.registerItem(item);
        } else {
            // EDIT mode
            try {
                int item_id = Integer.parseInt(item_idStr);
                item.setItem_id(item_id);
                success = itemService.updateItem(item);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                success = false;
            }
        }

        if (success) {
            response.sendRedirect("ItemServlet");
        } else {
            response.sendRedirect("error.jsp");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("delete".equals(action)) {
            String idStr = request.getParameter("id");
            if (idStr != null && !idStr.isEmpty()) {
                try {
                    int itemId = Integer.parseInt(idStr);
                    itemService.deleteItem(itemId);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
            response.sendRedirect("ItemServlet"); // Redirect to refresh the list
        } else {
            // Default: Show all items
            List<Item> itemList = itemService.getAllItems();
            request.setAttribute("items", itemList);
            request.getRequestDispatcher("manage-items.jsp").forward(request, response);
        }
    }
}