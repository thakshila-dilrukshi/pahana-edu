package com.icbt.service;

import com.icbt.dao.CustomerDAO;
import com.icbt.dao.ItemDAO;
import com.icbt.model.Customer;
import com.icbt.model.Item;
import com.icbt.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class ItemService {
    private ItemDAO itemDAO = new ItemDAO();

    public boolean registerItem(Item item) {
        return itemDAO.addItem(item);
    }

    public boolean updateItem(Item item) {
        return itemDAO.updateItem(item);
    }


    public List<Item> getAllItems() {
        return itemDAO.getAllItems();
    }

    public boolean deleteItem(int itemId) {
        return itemDAO.deleteItem(itemId);
    }

    public Item getItemById(int id) {
        Item item = null;
        String sql = "SELECT * FROM items WHERE item_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                item = new Item();
                item.setItem_id(rs.getInt("item_id"));
                item.setItem_name(rs.getString("item_name"));
                item.setItem_description(rs.getString("item_description"));
                item.setUnit_price(rs.getDouble("unit_price"));
                item.setStock_quantity(rs.getDouble("stock_quantity"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return item;
    }

}