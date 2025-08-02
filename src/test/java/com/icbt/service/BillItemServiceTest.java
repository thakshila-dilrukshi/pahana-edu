package com.icbt.service;

import com.icbt.model.BillItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BillItemServiceTest {

    private BillItemService billItemService;

    @BeforeEach
    public void setUp() {
        billItemService = new BillItemService();
    }

    @Test
    public void testSaveBillItems() {
        List<BillItem> items = new ArrayList<>();

        BillItem item1 = new BillItem();
        item1.setBillId(1); // Replace with a valid bill ID
        item1.setItemId(1); // Replace with a valid item ID
        item1.setQuantity(2);
        item1.setPrice(500.00);

        BillItem item2 = new BillItem();
        item2.setBillId(1); // Same bill ID
        item2.setItemId(2); // Replace with another valid item ID
        item2.setQuantity(1);
        item2.setPrice(750.00);

        items.add(item1);
        items.add(item2);

        assertDoesNotThrow(() -> billItemService.saveBillItems(items), "Saving bill items should not throw exception");
    }

    @Test
    public void testUpdateBillItem() {
        BillItem item = new BillItem();
        item.setBillItemId(1); // Replace with existing bill item ID
        item.setBillId(1);
        item.setItemId(1);
        item.setQuantity(3);
        item.setPrice(600.00);

        boolean result = billItemService.updateBillItem(item);
        assertTrue(result, "Bill item should be updated successfully");
    }

    @Test
    public void testDeleteBillItem() {
        int billItemIdToDelete = 2; // Replace with a bill item ID that exists
        boolean deleted = billItemService.deleteBillItem(billItemIdToDelete);

        assertTrue(deleted, "Bill item should be deleted successfully");
    }

    @Test
    public void testGetAllBillItems() {
        List<BillItem> items = billItemService.getAllBillItems();

        assertNotNull(items, "Returned list should not be null");
        assertTrue(items.size() >= 0, "Returned list size should be >= 0");
    }

    @Test
    public void testGetBillItemsByBillId() {
        int billId = 1; // Replace with a valid bill ID
        List<BillItem> items = billItemService.getBillItemsByBillId(billId);

        assertNotNull(items, "Returned list should not be null");
        for (BillItem item : items) {
            assertEquals(billId, item.getBillId(), "Each item's bill ID should match requested bill ID");
        }
    }
}
