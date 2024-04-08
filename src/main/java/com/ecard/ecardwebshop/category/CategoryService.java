package com.ecard.ecardwebshop.category;

import com.ecard.ecardwebshop.product.ResultStatus;
import com.ecard.ecardwebshop.product.ResultStatusEnum;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private CategoryDao categoryDao;
    private CategoryValidator categoryValidator;

    public CategoryService(CategoryDao categoryDao, CategoryValidator categoryValidator) {
        this.categoryDao = categoryDao;
        this.categoryValidator = categoryValidator;
    }

    public List<Category> listCategories() {
        return categoryDao.listCategories();
    }

    public ResultStatus createCategoryAndGetId(Category category) {
        long max = categoryDao.getMaxOrdinal();
        if (max + 1 < category.getOrdinal() || category.getOrdinal() < 0) {
            return new ResultStatus(ResultStatusEnum.NOT_OK, "Helytelen sorszám, állítsa be a soron következőt vagy egy már meglévőt");
        }
        if (category.getOrdinal() == 0) {
            category.setOrdinal(max + 1);
        } else if (category.getOrdinal() <= max) {
            categoryDao.increaseOrdinalFrom(category.getOrdinal());
        }
        try {
            long id = categoryDao.createCategoryAndGetId(category);
            return new ResultStatus(ResultStatusEnum.OK, "Kategória sikeresen hozzáadva! id: " + id);
        } catch (DataAccessException dae) {
            return new ResultStatus(ResultStatusEnum.NOT_OK, "Hiba történt a mentéskor");
        }
    }

    public ResultStatus deleteCategory(long id) {

        long originalOrdinal = categoryDao.getCategoryById(id).getOrdinal();
        categoryDao.decreaseOrdinalFrom(originalOrdinal);

        try {
            int rowsAffected = categoryDao.deleteCategory(id);
            if (rowsAffected == 0) {
                return new ResultStatus(ResultStatusEnum.NOT_OK, "Sikertelen törlés!");
            }
            return new ResultStatus(ResultStatusEnum.OK, "Sikeres törlés!");
        } catch (DataAccessException dae) {
            return new ResultStatus(ResultStatusEnum.NOT_OK, "Hiba történt törléskor");
        }
    }

    public ResultStatus updateCategory(long id, Category category) {
        if (!categoryDao.getCategoryById(id).getName().equals(category.getName()) && categoryValidator.isExistingCategoryName(category)) {
            return new ResultStatus(ResultStatusEnum.NOT_OK, "Ilyen kategórianév már létezik, adjon meg egyedi nevet");
        }

        long max = categoryDao.getMaxOrdinal();
        if (max < category.getOrdinal() || category.getOrdinal() <= 0) {
            return new ResultStatus(ResultStatusEnum.NOT_OK, "Az adott sorszámnak a meglévők között kell lennie!");
        }

        long originalOrdinal = categoryDao.getCategoryById(id).getOrdinal();
        if (originalOrdinal > category.getOrdinal()) {
            categoryDao.increaseOrdinalFromTo(category.getOrdinal(), originalOrdinal);
        } else if (category.getOrdinal() > originalOrdinal) {
            categoryDao.decreaseOrdinalFromTo(category.getOrdinal(), originalOrdinal);
        }

        try {
            int rowsAffected = categoryDao.updateCategory(id, category);
            if (rowsAffected == 0) {
                return new ResultStatus(ResultStatusEnum.NOT_OK, "Nem sikerült a mentés");
            }
            return new ResultStatus(ResultStatusEnum.OK, "Kategória sikeresen módosítva");
        } catch (DataAccessException dae) {
            return new ResultStatus(ResultStatusEnum.NOT_OK, "Hiba történt a mentéskor");
        }
    }


}
