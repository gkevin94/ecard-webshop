package com.ecard.ecardwebshop;

import com.ecard.ecardwebshop.category.Category;
import com.ecard.ecardwebshop.category.CategoryController;
import com.ecard.ecardwebshop.product.ResultStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(scripts = "/init.sql")
public class CategoryTest {

    @Autowired
    private CategoryController categoryController;

    @Test
    public void testListCategories() {
        // Given
        // When
        List<Category> categoryList = categoryController.listCategories();
        // Then
        assertEquals(6, categoryList.size());
        assertEquals("Nincs kategória", categoryList.get(0).getName());
        assertEquals(3, categoryList.get(2).getOrdinal());
    }

    @Test
    public void testCreate() {
        // Given
        List<Category> categoryList = categoryController.listCategories();
        // When
        ResultStatus status1 = categoryController.createCategoryAndGetId(new Category(4, "insertToEnd", 7));
        ResultStatus status2 = categoryController.createCategoryAndGetId(new Category(5, "insertFirst", 1));
        ResultStatus status3 = categoryController.createCategoryAndGetId(new Category(6, "insertZero", 0));
        ResultStatus status4 = categoryController.createCategoryAndGetId(new Category(7, "insertToSecond", 2));
        List<Category> categoryListNew = categoryController.listCategories();
        List<String> names = categoryListNew.stream().map(Category::getName).collect(Collectors.toList());
        List<Long> ordinals = categoryListNew.stream().map(Category::getOrdinal).collect(Collectors.toList());
        List<String> results = List.of(status1.getStatus().toString(), status2.getStatus().toString(), status3.getStatus().toString(), status4.getStatus().toString());
        // Then
        assertEquals(categoryList.size()+4, categoryListNew.size());
        assertEquals(results, new ArrayList<>(List.of("OK","OK","OK","OK")));
    }

    @Test
    public void testCreateError() {
        // Given
        // When
        ResultStatus status1 = categoryController.createCategoryAndGetId(new Category(4, "", 4));
        ResultStatus status2 = categoryController.createCategoryAndGetId(new Category(8, "imABadBoy", 8));
        ResultStatus status3 = categoryController.createCategoryAndGetId(new Category(9, "insertNegative", -5));
        ResultStatus status4 = categoryController.createCategoryAndGetId(new Category(7, "Nincs kategória", 2));
        // Then
        System.out.println(status1.getMessage() + status2.getMessage() + status3.getMessage() + status4.getMessage());
        assertEquals("Név megadása kötelező", status1.getMessage());
        assertEquals("Helytelen sorszám, állítsa be a soron következőt vagy egy már meglévőt", status2.getMessage());
        assertEquals("Helytelen sorszám, állítsa be a soron következőt vagy egy már meglévőt", status3.getMessage());
        assertEquals("Ilyen kategória már létezik, adjon meg egyedi nevet", status4.getMessage());
    }

    @Test
    public void testDelete(){
        //Given
         //When
        categoryController.deleteCategory(2L);
        List<Category> categories = categoryController.listCategories();

        //Then
        assertEquals(5, categoryController.listCategories().size());
    }

    @Test
    public void testProductChanges() {
        // Given
        // When
        ResultStatus status1 = categoryController.updateCategory(2L, new Category(1L, "fast", 2L));
        ResultStatus status2 = categoryController.updateCategory(2L, new Category(2L, "fast", 2L));
        ResultStatus status3 = categoryController.updateCategory(3L, new Category(3L, "smart", 9L));
        ResultStatus status4 = categoryController.updateCategory(3L, new Category(3L, "Nincs kategória", 3L));
        ResultStatus status5 = categoryController.updateCategory(3L, new Category(3L, "", 3L));

        // Then
        assertEquals("Kategória sikeresen módosítva", status1.getMessage());
        assertEquals("Kategória sikeresen módosítva", status2.getMessage());
        assertEquals("Az adott sorszámnak a meglévők között kell lennie!", status3.getMessage());
        assertEquals("Ilyen kategórianév már létezik, adjon meg egyedi nevet", status4.getMessage());
        assertEquals("Név megadása kötelező", status5.getMessage());

    }
}
