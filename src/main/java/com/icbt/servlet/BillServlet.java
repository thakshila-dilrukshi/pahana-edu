package com.icbt.servlet;

import com.icbt.model.Bill;
import com.icbt.model.BillItem;
import com.icbt.model.Customer;
import com.icbt.model.Item;
import com.icbt.service.BillItemService;
import com.icbt.service.BillService;
import com.icbt.service.CustomerService;
import com.icbt.service.ItemService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet("/BillServlet")
public class BillServlet extends HttpServlet {
    private final BillService billService = new BillService();
    private final BillItemService billItemService = new BillItemService();
    private final CustomerService customerService = new CustomerService();
    private final ItemService itemService = new ItemService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("new".equals(action)) {
            // Load new bill form
            List<Item> items = itemService.getAllItems();
            List<Customer> customers = customerService.getAllCustomers();
            request.setAttribute("items", items);
            request.setAttribute("customers", customers);
            request.getRequestDispatcher("add-bill.jsp").forward(request, response);
        } else {
            // Default: list all bills
            List<Bill> bills = billService.getAllBills();
            request.setAttribute("bills", bills);
            request.getRequestDispatcher("list-bill.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");

        String action = request.getParameter("action");

        try {
            if ("confirm".equals(action)) {
                System.out.println("Thakshila moday");
                // Action from confirm-bill.jsp - save the bill and items

                String customerIdStr = request.getParameter("customerId");
                String itemIdStr = request.getParameter("itemId");
                String quantityStr = request.getParameter("quantity");

                if (customerIdStr == null || itemIdStr == null || quantityStr == null
                        || customerIdStr.isEmpty() || itemIdStr.isEmpty() || quantityStr.isEmpty()) {
                    response.sendRedirect("error.jsp");
                    return;
                }

                int customerId = Integer.parseInt(customerIdStr);
                int itemId = Integer.parseInt(itemIdStr);
                int quantity = Integer.parseInt(quantityStr);

                // Get item price from DB to calculate total
                Item item = itemService.getItemById(itemId);
                if (item == null) {
                    response.sendRedirect("error.jsp");
                    return;
                }
                double price = item.getUnit_price();
                double totalAmount = price * quantity;

                // Create Bill object
                Bill bill = new Bill();
                bill.setAccountNumber(customerId);
                bill.setBillDate(new Date());
                bill.setTotalAmount(totalAmount);
                bill.setConfirmed(false);

                // Save Bill
                int savedBillId = billService.addBill(bill);
                if (savedBillId <= 0) {
                    response.sendRedirect("error.jsp");
                    return;
                }

                // Create BillItem and save
                BillItem billItem = new BillItem();
                billItem.setBillId(savedBillId);
                billItem.setItemId(itemId);
                billItem.setQuantity(quantity);
                billItem.setPrice(price);

                List<BillItem> items = new ArrayList<>();
                items.add(billItem);

                billItemService.saveBillItems(items);

                // Redirect to confirmation page with saved bill id
                response.sendRedirect("BillServlet?action=show&id=" + savedBillId);

            } else if ("show".equals(action)) {
                // Show single bill confirmation or details (optional)

                String idParam = request.getParameter("id");
                if (idParam == null || idParam.isEmpty()) {
                    response.sendRedirect("BillServlet");
                    return;
                }
                int billId = Integer.parseInt(idParam);

                Bill bill = billService.getBillById(billId);
                if (bill == null) {
                    response.sendRedirect("BillServlet");
                    return;
                }

                request.setAttribute("bill", bill);
                // You can load bill items if needed and pass as attribute
                request.getRequestDispatcher("confirm-bill.jsp").forward(request, response);

            } else {
                System.out.println("Uvindu MOday");
                // Unknown action: redirect to bill list
                response.sendRedirect("BillServlet");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}
