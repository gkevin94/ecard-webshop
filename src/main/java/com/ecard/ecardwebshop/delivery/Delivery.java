package com.ecard.ecardwebshop.delivery;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Delivery {

    private long deliveryId;
    private String deliveryAddress;
    private long userId;
    private String paymentType;

    @JsonCreator
    public Delivery(@JsonProperty("deliveryAddress") String deliveryAddress,
                    @JsonProperty("paymentType") String paymentType) {
        this.deliveryAddress = deliveryAddress;
        this.paymentType = paymentType;
    }
    public Delivery(long deliveryId, String deliveryAddress, long userId) {
        this.deliveryId = deliveryId;
        this.deliveryAddress = deliveryAddress;
        this.userId = userId;
    }

    public Delivery(long deliveryId, String deliveryAddress, long userId, String paymentType) {
        this.deliveryId = deliveryId;
        this.deliveryAddress = deliveryAddress;
        this.userId = userId;
        this.paymentType = paymentType;
    }


    public long getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(int deliveryId) {
        this.deliveryId = deliveryId;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

}
