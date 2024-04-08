package com.ecard.ecardwebshop.report;

public class ShippedProductReport {

    private int year;
    private int month;
    private String productname;
    private int price;
    private int count;
    private int total;

    public ShippedProductReport(int year, int month, String productname, int price, int count, int total) {
        this.year = year;
        this.month = month;
        this.productname = productname;
        this.price = price;
        this.count = count;
        this.total = total;
    }

    public int getCount() {
        return count;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public String getProductname() {
        return productname;
    }

    public int getPrice() {
        return price;
    }

    public int getTotal() {
        return total;
    }
}
