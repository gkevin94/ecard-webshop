package com.ecard.ecardwebshop.report;

public class OrderReport {

    private int year;
    private int month;
    private String orderStatus;
    private int total;
    private int count;

    public OrderReport(int year, int month, String orderStatus, int total, int count) {
        this.year = year;
        this.month = month;
        this.orderStatus = orderStatus;
        this.total = total;
        this.count = count;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public int getTotal() {
        return total;
    }

    public int getCount() {
        return count;
    }
}
