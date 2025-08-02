package com.icbt.dao;

import com.icbt.model.BillItem;
import com.icbt.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BillItemDAO {

    // Add multiple bill items (batch insert)
    public void addBillItems(List<BillItem> items) {
        String sql = "INSERT INTO bill_items (bill_id, item_id, quantity, price) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (BillItem item : items) {
                stmt.setInt(1, item.getBillId());
                stmt.setInt(2, item.getItemId());
                stmt.setInt(3, item.getQuantity());
                stmt.setDouble(4, item.getPrice());
                stmt.addBatch();
            }

            stmt.executeBatch();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Add this method to your BillItemDAO class
    public List<BillItem> getBillItemsByBillId(int billId) {
        List<BillItem> billItems = new ArrayList<>();
        String sql = "SELECT * FROM bill_items WHERE bill_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, billId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    BillItem item = new BillItem();
                    item.setBillItemId(rs.getInt("id"));
                    item.setBillId(rs.getInt("bill_id"));
                    item.setItemId(rs.getInt("item_id"));
                    item.setQuantity(rs.getInt("quantity"));
                    item.setPrice(rs.getDouble("price"));
                    billItems.add(item);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return billItems;
    }


    // Update a single bill item
    public boolean updateBillItem(BillItem item) {
        String sql = "UPDATE bill_items SET bill_id = ?, item_id = ?, quantity = ?, price = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, item.getBillId());
            stmt.setInt(2, item.getItemId());
            stmt.setInt(3, item.getQuantity());
            stmt.setDouble(4, item.getPrice());
            stmt.setInt(5, item.getBillItemId()); // assuming primary key is bill_item_id

            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Delete a bill item by bill_item_id
    public boolean deleteBillItem(int billItemId) {
        String sql = "DELETE FROM bill_items WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, billItemId);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get all bill items
    public List<BillItem> getAllBillItems() {
        List<BillItem> billItems = new ArrayList<>();
        String sql = "SELECT * FROM bill_items";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                BillItem item = new BillItem();
                item.setBillItemId(rs.getInt("id"));
                item.setBillId(rs.getInt("bill_id"));
                item.setItemId(rs.getInt("item_id"));
                item.setQuantity(rs.getInt("quantity"));
                item.setPrice(rs.getDouble("price"));
                billItems.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return billItems;
    }
}