package com.icbt.service;

import com.icbt.dao.CustomerDAO;
import com.icbt.model.Customer;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerServiceTest {

    private final CustomerDAO customerDAO = new CustomerDAO();

    @Test
    public void testRegisterCustomer() {
        // Since you don't want static data, you could retrieve an existing customer or skip insert testing.
        // Here's an example assuming a new customer registration is okay:
        Customer customer = new Customer(); // Fill this in with actual data or fetch from elsewhere
        customer.setName("Test User");
        customer.setAddress("115,koswatta");
        customer.setTelephone("0712345678");

        boolean result = customerDAO.addCustomer(customer);
        assertTrue(result, "Customer registration should return true");
    }

    @Test
    public void testUpdateCustomer() {
        // You must provide an existing ID or fetch a customer from the database
        List<Customer> customers = customerDAO.getAllCustomers();
        assertFalse(customers.isEmpty(), "Customer list should not be empty for update test");

        Customer existingCustomer = customers.get(0); // pick the first one for update
        existingCustomer.setName("Updated Name");

        boolean result = customerDAO.updateCustomer(existingCustomer);
        assertTrue(result, "Customer update should return true");
    }

    @Test
    public void testGetAllCustomers() {
        List<Customer> customers = customerDAO.getAllCustomers();
        assertNotNull(customers, "Customer list should not be null");
        assertFalse(customers.isEmpty(), "Customer list should not be empty");
    }

}
