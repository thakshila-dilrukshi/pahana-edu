package com.icbt.service;

import com.icbt.dao.CustomerDAO;
import com.icbt.model.Customer;

import java.util.List;

public class CustomerService {
    private CustomerDAO customerDAO = new CustomerDAO();

    public boolean registerCustomer(Customer customer) {
        return customerDAO.addCustomer(customer);
    }

    public boolean updateCustomer(Customer customer) {
        return customerDAO.updateCustomer(customer);
    }

    public boolean deleteCustomer(int accountNumber) {return CustomerDAO.deleteCustomer(accountNumber);}
    public List<Customer> getAllCustomers() {
        return customerDAO.getAllCustomers();
    }

    public Customer getCustomyIerBd(int accountNumber) {
        return customerDAO.getCustomerById(accountNumber);
    }

}
