package com.ecard.ecardwebshop.product;

import com.ecard.ecardwebshop.category.Category;
import com.ecard.ecardwebshop.category.CategoryDao;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class ProductService {

    private ProductDao productDao;
    private CategoryDao categoryDao;

    public ProductService(ProductDao productDao, CategoryDao categoryDao) {
        this.productDao = productDao;
        this.categoryDao = categoryDao;
    }

    public Product getProduct(String address) {
        return productDao.getProduct(address);
    }

    public List<Product> getProducts() {
        return productDao.getProducts();
    }

    public List<Product> getAllProducts() {
        return productDao.getAllProducts();
    }

    public List<Product> getProductsWithStartAndSize(int start, int size) {
        return productDao.getProductsWithStartAndSize(start, size);
    }
    public List<Product> getProductsWithStartAndSizeAndCategory(int start, int size, Category category) {
        return productDao.getProductsWithStartAndSizeAndCategory(start, size, category);
    }

    public long saveProductAndGetId(Product product) {
        for (Category category : categoryDao.listCategories()) {
            if (category.getName().equals(product.getCategory().getName()))
                return productDao.saveProductAndGetId(product);
        }
        categoryDao.createCategoryAndGetId(product.getCategory());
        return productDao.saveProductAndGetId(product);
    }

    public void updateProducts(long id, Product product) {
        productDao.updateProduct(id, product);
    }

    public void deleteProduct(long id) {
        productDao.deleteProduct(id);
    }

    public Product getProductById(@PathVariable long id) {
        return productDao.getProductById(id);
    }

    public List<Product> listAdviceProducts() {
        return productDao.listAdviceProducts();
    }
}
