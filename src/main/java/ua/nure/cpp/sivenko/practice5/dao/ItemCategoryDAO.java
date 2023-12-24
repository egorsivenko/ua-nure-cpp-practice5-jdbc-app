package ua.nure.cpp.sivenko.practice5.dao;

import ua.nure.cpp.sivenko.practice5.model.ItemCategory;

import java.util.List;

public interface ItemCategoryDAO {
    ItemCategory getItemCategoryById(long itemCategoryId);
    List<ItemCategory> getAllItemCategories();

    void addItemCategory(String itemCategoryName);
    void updateItemCategoryName(long itemCategoryId, String itemCategoryName);
    void deleteItemCategory(long itemCategoryId);
}
