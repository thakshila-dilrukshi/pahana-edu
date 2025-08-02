package com.icbt.service;

import com.icbt.dao.BillItemDAO;
import com.icbt.model.BillItem;

import java.util.List;

public class BillItemService {
    private final BillItemDAO billItemDAO = new BillItemDAO();

    // Add multiple bill items
    public void saveBillItems(List<BillItem> items) {
        billItemDAO.addBillItems(items);
    }

    // Update a single bill item
    public boolean updateBillItem(BillItem item) {
        return billItemDAO.updateBillItem(item);
    }

    // Delete a bill item by its ID
    public boolean deleteBillItem(int billItemId) {
        return billItemDAO.deleteBillItem(billItemId);
    }

    // Retrieve all bill items
    public List<BillItem> getAllBillItems() {
        return billItemDAO.getAllBillItems();
    }

    // Optional: Retrieve items by billId
    public List<BillItem> getBillItemsByBillId(int billId) {
        return billItemDAO.getBillItemsByBillId(billId);
    }
}