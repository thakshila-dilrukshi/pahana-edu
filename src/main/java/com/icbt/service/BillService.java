package com.icbt.service;

import com.icbt.dao.BillDAO;
import com.icbt.model.Bill;

import java.util.List;

public class BillService {
    private final BillDAO billDAO = new BillDAO();

    // ✅ New method that accepts a full Bill object
    public int addBill(Bill bill) {
        return billDAO.addBill(bill);
    }

    // Original method for quick bill creation (still optional)
    public int generateBill(int accountNumber, double totalAmount) {
        Bill bill = new Bill();
        bill.setAccountNumber(accountNumber);
        bill.setTotalAmount(totalAmount);
        return billDAO.addBill(bill);
    }

    public boolean updateBill(Bill bill) {
        return billDAO.updateBill(bill);
    }

    public boolean deleteBill(int billId) {
        return billDAO.deleteBill(billId);
    }

    public Bill getBillById(int billId) {
        return billDAO.getBillById(billId);
    }

    public List<Bill> getAllBills() {
        return billDAO.getAllBills();
    }
}