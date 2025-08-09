package com.icbt.servlet;

import com.icbt.model.Customer;
import com.icbt.service.CustomerService;
import com.icbt.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/CustomerServlet")
public class CustomerServlet extends HttpServlet {
    private final CustomerService customerService = new CustomerService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accountNumberStr = request.getParameter("accountNumber");
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String telephone = request.getParameter("telephone");


        // Basic validation
        if (name == null || name.trim().isEmpty() ||
                address == null || address.trim().isEmpty() ||
                telephone == null || telephone.trim().isEmpty()) {
            response.sendRedirect("error.jsp");
            return;
        }

        Customer customer = new Customer();
        customer.setName(name.trim());
        customer.setAddress(address.trim());
        customer.setTelephone(telephone.trim());

        boolean success;

        try {
            if (accountNumberStr == null || accountNumberStr.isEmpty()) {
                // ADD mode
                success = customerService.registerCustomer(customer);
            } else {
                // EDIT mode
                int accountNumber = Integer.parseInt(accountNumberStr);
                customer.setAccountNumber(accountNumber);
                success = customerService.updateCustomer(customer);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            success = false;
        }

        if (success) {
            response.sendRedirect("CustomerServlet");
        } else {
            response.sendRedirect("error.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String mode = request.getParameter("mode");

        try {
            if ("delete".equals(mode)) {
                String accNumStr = request.getParameter("accountNumber");
                int accountNumber = Integer.parseInt(accNumStr);
                customerService.deleteCustomer(accountNumber);
                response.sendRedirect("CustomerServlet");
                return;
            } else if ("search".equals(mode)) {
                String accNumStr = request.getParameter("accountNumber");
                int accountNumber = Integer.parseInt(accNumStr);
                Customer customer = customerService.getCustomyIerBd(accountNumber);
                if (customer != null) {
                    request.setAttribute("customer", customer);
                    request.getRequestDispatcher("display-account.jsp").forward(request, response);
                } else {
                    response.sendRedirect("error.jsp");
                }
                return;
            }

            // Default: show customer list
            List<Customer> customerList = customerService.getAllCustomers();
            request.setAttribute("customers", customerList);
            request.getRequestDispatcher("show-customer.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}

