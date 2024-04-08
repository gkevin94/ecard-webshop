package com.ecard.ecardwebshop.product;

import com.ecard.ecardwebshop.category.Category;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductController {

    private ProductService productService;
    private ProductValidator validator;

    public ProductController(ProductService productService) {
        this.productService = productService;
        this.validator = new ProductValidator();
    }

    @GetMapping("/product/{address}")
    public Object getProduct(@PathVariable String address) {
        List<String> addresses = productService.getProducts().stream().map(Product::getAddress).collect(Collectors.toList());
        if (validator.isValid(address) && addresses.contains(address)) {
            return productService.getProduct(address);
        } else {
            return new ResultStatus(ResultStatusEnum.NOT_OK, "Invalid address");
        }
    }

    @PostMapping("/products")
    public ResultStatus saveProductAndGetId(@RequestBody Product product) {
        validator = new ProductValidator();
        if (validator.isValidProduct(product)) {
            try {
                long id = productService.saveProductAndGetId(product);
                return new ResultStatus(ResultStatusEnum.OK, String.format("Termék sikeresen hozzáadva! (termék id: %d )", id));
            } catch (DataAccessException sql) {
                return new ResultStatus(ResultStatusEnum.NOT_OK, "Termék cím vagy kód már szerepel másik terméknél");
            }
        } else {
            return new ResultStatus(ResultStatusEnum.NOT_OK, "Minden adat kitöltendő, maximális ár: 2.000.000 Ft");
        }
    }

    @PostMapping("/products/{id}")
    public ResultStatus updateProducts(@PathVariable long id, @RequestBody Product product) {
        validator = new ProductValidator();
        if (validator.isValidProduct(product)) {
            try {
                productService.updateProducts(id, product);
                return new ResultStatus(ResultStatusEnum.OK, "Termék sikeresen módosítva!");
            } catch (DataAccessException sql) {
                return new ResultStatus(ResultStatusEnum.NOT_OK, "Termék cím vagy kód már szerepel másik terméknél");
            }
        } else {
            return new ResultStatus(ResultStatusEnum.NOT_OK, "Minden adat kitöltendő, maximális ár: 2.000.000 Ft");
        }
    }

    @GetMapping("/products")
    public List<Product> getProducts() {
        return productService.getProducts();
    }

    @GetMapping("/allProducts")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping("/products/{start}/{size}")
    public List<Product> getProductsWithStartAndSize(@PathVariable int start, @PathVariable int size, @RequestBody(required = false) Category category) {
        if (category==null)
            return productService.getProductsWithStartAndSize(start,size);
        return productService.getProductsWithStartAndSizeAndCategory(start, size, category);
    }

    @DeleteMapping("/products/{id}")
    public void deleteProduct(@PathVariable long id) {
        productService.deleteProduct(id);
    }


    @GetMapping("/products/{id}")
    public Product getProductById(@PathVariable long id) {
        return productService.getProductById(id);
    }

    @GetMapping("/advice")
    public List<Product> listAdvicedProducts(){
        return productService.listAdviceProducts();
    }
}