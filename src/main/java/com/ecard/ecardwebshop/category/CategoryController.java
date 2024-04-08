package com.ecard.ecardwebshop.category;

import com.ecard.ecardwebshop.product.ResultStatus;
import com.ecard.ecardwebshop.product.ResultStatusEnum;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {

    private CategoryService categoryService;

    private CategoryValidator categoryValidator;

    public CategoryController(CategoryService categoryService, CategoryValidator categoryValidator) {
        this.categoryService = categoryService;
        this.categoryValidator = categoryValidator;
    }

    @GetMapping("/categories")
    public List<Category> listCategories() {
        return categoryService.listCategories();
    }

    @PostMapping("/categories")
    public ResultStatus createCategoryAndGetId(@RequestBody Category category) {
        if (categoryValidator.isEmpty(category.getName())) {
            return new ResultStatus(ResultStatusEnum.NOT_OK, "Név megadása kötelező");
        }
        if (categoryValidator.isExistingCategoryName(category)) {
            return new ResultStatus(ResultStatusEnum.NOT_OK, "Ilyen kategória már létezik, adjon meg egyedi nevet");
        }
        return categoryService.createCategoryAndGetId(category);
    }

    @DeleteMapping("/categories/{id}")
    public ResultStatus deleteCategory(@PathVariable long id) {
        return categoryService.deleteCategory(id);
    }

    @PostMapping("categories/{id}")
    public ResultStatus updateCategory(@PathVariable long id, @RequestBody Category category) {
        if (categoryValidator.isEmpty(category.getName())) {
            return new ResultStatus(ResultStatusEnum.NOT_OK, "Név megadása kötelező");
        }
        return categoryService.updateCategory(id, category);
    }
}