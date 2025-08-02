package com.icbt.service;

import com.icbt.model.Item;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ItemServiceTest {

    private ItemService itemService;
    private static int testItemId; // Will hold ID of the item created for testing

    @BeforeEach
    public void setUp() {
        itemService = new ItemService();

        // Insert test item
        Item item = new Item();
        item.setItem_name("Test Item");
        item.setItem_description("Test Description");
        item.setUnit_price(100.0);
        item.setStock_quantity(5.0);

        itemService.registerItem(item);

        // Get last inserted item's ID
        List<Item> items = itemService.getAllItems();
        testItemId = items.get(items.size() - 1).getItem_id();
    }

    @Test
    @Order(1)
    public void testRegisterItem() {
        Item newItem = new Item();
        newItem.setItem_name("New Item");
        newItem.setItem_description("New item for test");
        newItem.setUnit_price(300.0);
        newItem.setStock_quantity(7.0);

        boolean result = itemService.registerItem(newItem);
        assertTrue(result, "Item should be registered successfully");
    }

    @Test
    @Order(2)
    public void testUpdateItem() {
        Item itemToUpdate = new Item();
        itemToUpdate.setItem_id(testItemId);
        itemToUpdate.setItem_name("Updated Item");
        itemToUpdate.setItem_description("Updated description");
        itemToUpdate.setUnit_price(200.0);
        itemToUpdate.setStock_quantity(10.0);

        boolean result = itemService.updateItem(itemToUpdate);
        assertTrue(result, "Item should be updated successfully");
    }

    @Test
    @Order(3)
    public void testGetAllItems() {
        List<Item> items = itemService.getAllItems();
        assertNotNull(items, "Items list should not be null");
        assertTrue(items.size() > 0, "There should be at least one item");
    }

    @Test
    @Order(4)
    public void testGetItemById() {
        Item item = itemService.getItemById(testItemId);
        assertNotNull(item, "Item should not be null for valid ID");
        assertEquals(testItemId, item.getItem_id(), "Item ID should match");
    }

    @Test
    @Order(5)
    public void testDeleteItem() {
        boolean deleted = itemService.deleteItem(testItemId);
        assertTrue(deleted, "Item should be deleted successfully");

        // Verify deletion
        Item deletedItem = itemService.getItemById(testItemId);
        assertNull(deletedItem, "Deleted item should not be found in DB");
    }
}
