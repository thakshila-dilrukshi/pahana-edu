package com.icbt.service;

import com.icbt.dao.CustomerDAO;
import com.icbt.dao.ItemDAO;
import com.icbt.model.Customer;
import com.icbt.model.Item;

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
}