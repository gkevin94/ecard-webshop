package com.ecard.ecardwebshop.category;

import org.springframework.stereotype.Component;

@Component
public class CategoryValidator {
    private CategoryDao categoryDao;

    public CategoryValidator(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    public boolean isEmpty(String str) {
         return str == null || str.trim().isEmpty();
    }

    public boolean isExistingCategoryName(Category category) {
        return !categoryDao.getCategoryByName(category.getName()).isEmpty();
    }
}
