package com.icbt.dao;

import com.icbt.model.Bill;
import com.icbt.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BillDAO {

    // Add a new bill and return generated ID
    public int addBill(Bill bill) {
        String sql = "INSERT INTO bills (account_number, total_amount) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, bill.getAccountNumber());
            stmt.setDouble(2, bill.getTotalAmount());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); // Return generated bill ID
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1; // Failure
    }

    // Update an existing bill
    public boolean updateBill(Bill bill) {
        String sql = "UPDATE bills SET account_number = ?, total_amount = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, bill.getAccountNumber());
            stmt.setDouble(2, bill.getTotalAmount());
            stmt.setInt(3, bill.getBillId());

            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Delete a bill by ID
    public boolean deleteBill(int billId) {
        String sql = "DELETE FROM bills WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, billId);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Retrieve all bills
    public List<Bill> getAllBills() {
        List<Bill> bills = new ArrayList<>();
        String sql = "SELECT * FROM bills";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Bill bill = new Bill();
                bill.setBillId(rs.getInt("id"));
                bill.setAccountNumber(rs.getInt("account_number"));
                bill.setTotalAmount(rs.getDouble("total_amount"));
                bill.setBillDate(rs.getTimestamp("bill_date"));
                bills.add(bill);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bills;
    }

    // Get a bill by its ID
    public Bill getBillById(int billId) {
        String sql = "SELECT * FROM bills WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, billId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Bill bill = new Bill();
                bill.setBillId(rs.getInt("id"));
                bill.setAccountNumber(rs.getInt("account_number"));
                bill.setTotalAmount(rs.getDouble("total_amount"));
                bill.setBillDate(rs.getTimestamp("bill_date"));
                return bill;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
