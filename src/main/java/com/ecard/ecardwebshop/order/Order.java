package com.ecard.ecardwebshop.order;

import com.ecard.ecardwebshop.delivery.Delivery;

import java.time.LocalDateTime;
import java.util.List;

public class Order {

    private long id;
    private LocalDateTime purchaseDate;
    private long userId;
    private long total;
    private long sumQuantity;
    private OrderStatus orderStatus;
    private Delivery delivery;
    private List<OrderedProduct> orderedProducts;

    public Order() {
    }

    public Order(long id, long userId, long total, long sumQuantity, String status) {
        this(id, LocalDateTime.now(), userId, total, sumQuantity, status);
    }

    public Order(long id, LocalDateTime purchaseDate, long userId, long total, long sumQuantity, String status) {
        this.id = id;
        this.purchaseDate = purchaseDate;
        this.userId = userId;
        this.total = total;
        this.sumQuantity = sumQuantity;
        this.orderStatus = OrderStatus.valueOf(status);
    }

    public Order(long id, LocalDateTime purchaseDate, long userId, long total, long sumQuantity, String orderStatus, Delivery delivery) {
        this(id, purchaseDate, userId, total, sumQuantity, orderStatus);
        this.delivery = delivery;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getSumQuantity() {
        return sumQuantity;
    }

    public void setSumQuantity(long sumQuantity) {
        this.sumQuantity = sumQuantity;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public List<OrderedProduct> getOrderedProducts() {
        return orderedProducts;
    }

    public void setOrderedProducts(List<OrderedProduct> orderedProducts) {
        this.orderedProducts = orderedProducts;
    }

    public String toString() {
        return id + " " + purchaseDate + " " + userId + " " + total + " " + sumQuantity + " " + orderStatus;
    }
}
