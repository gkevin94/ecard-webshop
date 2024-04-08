package com.ecard.ecardwebshop.basket;

public class BasketItem {
    private long productId;
    private long basketId;
    private String name;
    private String address;
    private int price;
    private int pieces;
    private String username;

    public BasketItem(long productId, long basketId, String name, String address, int price, int pieces) {
        this.productId = productId;
        this.basketId = basketId;
        this.name = name;
        this.address = address;
        this.price = price;
        this.pieces = pieces;
    }

    public long getBasketId() {
        return basketId;
    }

    public void setBasketId(long basketId) {
        this.basketId = basketId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPieces() {
        return pieces;
    }

    public void setPieces(int pieces) {
        this.pieces = pieces;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
