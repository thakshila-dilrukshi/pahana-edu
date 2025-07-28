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
import java.util.*;

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

        try {
            if (action != null) {
                switch (action) {
                    case "new":
                        List<Item> items = itemService.getAllItems();
                        List<Customer> customers = customerService.getAllCustomers();
                        request.setAttribute("items", items);
                        request.setAttribute("customers", customers);
                        request.getRequestDispatcher("add-bill.jsp").forward(request, response);
                        break;

                    case "edit":
                        int editId = Integer.parseInt(request.getParameter("id"));
                        Bill billToEdit = billService.getBillById(editId);
                        List<BillItem> billItems = billItemService.getBillItemsByBillId(editId);
                        List<Item> allItems = itemService.getAllItems();
                        List<Customer> allCustomers = customerService.getAllCustomers();

                        request.setAttribute("bill", billToEdit);
                        request.setAttribute("billItems", billItems);
                        request.setAttribute("items", allItems);
                        request.setAttribute("customers", allCustomers);
                        request.getRequestDispatcher("edit-bill.jsp").forward(request, response);
                        break;


                    case "delete":
                        int deleteId = Integer.parseInt(request.getParameter("id"));
                        billItemService.getBillItemsByBillId(deleteId).forEach(item -> billItemService.deleteBillItem(item.getBillItemId()));
                        billService.deleteBill(deleteId);
                        response.sendRedirect("BillServlet");
                        break;

                    default:
                        response.sendRedirect("error.jsp");
                }
            } else {
                List<Bill> bills = billService.getAllBills();
                Map<Integer, List<BillItem>> billItemsMap = new HashMap<>();

                for (Bill bill : bills) {
                    List<BillItem> items = billItemService.getBillItemsByBillId(bill.getBillId());
                    billItemsMap.put(bill.getBillId(), items);
                }
                List<Item>  allItems = itemService.getAllItems();
                request.setAttribute("items", allItems);
                request.setAttribute("bills", bills);
                request.setAttribute("billItemsMap", billItemsMap);
                request.getRequestDispatcher("list-bill.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        try {
            String formAction = request.getParameter("formAction");
            String customerIdParam = request.getParameter("customer_id");
            String[] itemIds = request.getParameterValues("itemId[]");
            String[] quantities = request.getParameterValues("quantity[]");
            String[] prices = request.getParameterValues("price[]");

            if (customerIdParam == null || itemIds == null || quantities == null || prices == null
                    || itemIds.length != quantities.length || itemIds.length != prices.length) {
                response.sendRedirect("error.jsp");
                return;
            }

            int accountNumber = Integer.parseInt(customerIdParam);
            List<BillItem> billItems = new ArrayList<>();
            double totalAmount = 0;

            for (int i = 0; i < itemIds.length; i++) {
                int itemId = Integer.parseInt(itemIds[i]);
                int quantity = Integer.parseInt(quantities[i]);
                double price = Double.parseDouble(prices[i]);

                BillItem item = new BillItem();
                item.setItemId(itemId);
                item.setQuantity(quantity);
                item.setPrice(price);
                billItems.add(item);

                totalAmount += quantity * price;
            }

            if ("update".equalsIgnoreCase(formAction)) {
                int billId = Integer.parseInt(request.getParameter("billId"));
                Bill bill = new Bill();
                bill.setBillId(billId);
                bill.setAccountNumber(accountNumber);
                bill.setTotalAmount(totalAmount);
                bill.setBillDate(new Date());

                boolean updated = billService.updateBill(bill);
                if (updated) {
                    billItemService.getBillItemsByBillId(billId).forEach(item -> billItemService.deleteBillItem(item.getBillItemId()));
                    for (BillItem item : billItems) {
                        item.setBillId(billId);
                    }
                    billItemService.saveBillItems(billItems);

                    request.setAttribute("bill", bill);
                    request.setAttribute("billItems", billItems);
                    request.getRequestDispatcher("confirm-bill.jsp").forward(request, response);
                } else {
                    response.sendRedirect("error.jsp");
                }

            } else {
                // Add new bill
                Bill bill = new Bill();
                bill.setAccountNumber(accountNumber);
                bill.setTotalAmount(totalAmount);
                bill.setBillDate(new Date());

                int billId = billService.addBill(bill);
                if (billId > 0) {
                    for (BillItem item : billItems) {
                        item.setBillId(billId);
                    }
                    billItemService.saveBillItems(billItems);
                    bill.setBillId(billId);

                    request.setAttribute("bill", bill);
                    request.setAttribute("billItems", billItems);
                    request.getRequestDispatcher("confirm-bill.jsp").forward(request, response);
                } else {
                    response.sendRedirect("error.jsp");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}
