package com.ecard.ecardwebshop.product;

import com.ecard.ecardwebshop.category.Category;

public class Product {

    private long id;
    private String code;
    private String address;
    private String name;
    private String manufacture;
    private int price;
    private String productStatus;
    private Category category;

    public Product() {
    }

    public Product(String address, String name, String manufacture, int price) {
        this.address = address;
        this.name = name;
        this.manufacture = manufacture;
        this.price = price;
    }

    public Product(String address, String name, String manufacture, int price, String productStatus) {
        this(address, name, manufacture, price);
        this.productStatus = productStatus;
    }

    public Product(long id, String code, String address, String name, String manufacture, int price, String productStatus, Category category) {
        this(address, name, manufacture, price);
        this.id = id;
        this.code = code;
        this.productStatus = productStatus;
        this.category = category;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacture() {
        return manufacture;
    }

    public void setManufacture(String manufacture) {
        this.manufacture = manufacture;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(String productStatus) {
        this.productStatus = productStatus;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", productStatus='" + productStatus + '\'' +
                '}';
    }
}
