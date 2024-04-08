package com.ecard.ecardwebshop.dashboard;

public class Dashboard {
    private int allUsers;
    private int allProducts;
    private int activeProducts;
    private int allOrders;
    private int activeOrders;

    public Dashboard(int allUsers, int allProducts, int activeProducts, int allOrders, int activeOrders) {
        this.allUsers = allUsers;
        this.allProducts = allProducts;
        this.activeProducts = activeProducts;
        this.allOrders = allOrders;
        this.activeOrders = activeOrders;
    }

    public int getAllUsers() {
        return allUsers;
    }

    public int getAllProducts() {
        return allProducts;
    }

    public int getActiveProducts() {
        return activeProducts;
    }

    public int getAllOrders() {
        return allOrders;
    }

    public int getActiveOrders() {
        return activeOrders;
    }
}
