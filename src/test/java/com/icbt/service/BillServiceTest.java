package com.icbt.service;

import com.icbt.model.Bill;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BillServiceTest {

    private BillService billService;
    private static int testBillId;

    @BeforeEach
    public void setUp() {
        billService = new BillService();

        // Insert a dummy bill to test with
        Bill bill = new Bill();
        bill.setAccountNumber(1001);
        bill.setTotalAmount(2500.00);

        testBillId = billService.addBill(bill); // This should return the inserted bill ID
    }

    @Test
    @Order(1)
    public void testAddBill() {
        Bill bill = new Bill();
        bill.setAccountNumber(1002);
        bill.setTotalAmount(1200.50);

        int billId = billService.addBill(bill);
        assertTrue(billId > 0, "Bill should be added and return a valid ID");
    }

    @Test
    @Order(2)
    public void testUpdateBill() {
        Bill updatedBill = new Bill();
        updatedBill.setBillId(testBillId);
        updatedBill.setAccountNumber(1001);
        updatedBill.setTotalAmount(3000.00);

        boolean updated = billService.updateBill(updatedBill);
        assertTrue(updated, "Bill should be updated successfully");
    }

    @Test
    @Order(3)
    public void testGetBillById() {
        Bill bill = billService.getBillById(testBillId);
        assertNotNull(bill, "Bill should not be null for valid ID");
        assertEquals(testBillId, bill.getBillId(), "Returned bill ID should match");
    }

    @Test
    @Order(4)
    public void testGetAllBills() {
        List<Bill> bills = billService.getAllBills();
        assertNotNull(bills, "Bills list should not be null");
        assertTrue(bills.size() > 0, "There should be at least one bill in DB");
    }

    @Test
    @Order(5)
    public void testDeleteBill() {
        boolean deleted = billService.deleteBill(testBillId);
        assertTrue(deleted, "Bill should be deleted successfully");

        // Verify deletion
        Bill deletedBill = billService.getBillById(testBillId);
        assertNull(deletedBill, "Deleted bill should no longer exist");
    }
}
