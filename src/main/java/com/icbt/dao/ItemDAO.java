package com.icbt.dao;

import com.icbt.model.Customer;
import com.icbt.model.Item;
import com.icbt.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO {
    public boolean addItem(Item item) {
        String sql = "INSERT INTO items (item_name, item_description, unit_price, stock_quantity ) VALUES (?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, item.getItem_name());
            stmt.setString(2, item.getItem_description());
            stmt.setString(3, item.getUnit_price());
            stmt.setString(4, item.getStock_quantity());


            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean updateItem(Item item) {
        String sql = "UPDATE items SET item_name = ?, item_description = ?, unit_price = ?, stock_quantity = ? WHERE item_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, item.getItem_name());
            stmt.setString(2, item.getItem_description());
            stmt.setString(3, item.getUnit_price());
            stmt.setString(4, item.getStock_quantity());
            stmt.setInt(5, item.getItem_id());

            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM items";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Item i = new Item();
                i.setItem_id(rs.getInt("item_id"));
                i.setItem_name(rs.getString("item_name"));
                i.setItem_description(rs.getString("item_description"));
                i.setUnit_price(rs.getString("unit_price"));
                i.setStock_quantity(rs.getString("stock_quantity"));
                items.add(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return items;
    }

    public boolean deleteItem(int itemId) {
        String sql = "DELETE FROM items WHERE item_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, itemId);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}