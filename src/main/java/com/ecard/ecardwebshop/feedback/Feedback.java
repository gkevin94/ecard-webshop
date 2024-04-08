package com.ecard.ecardwebshop.feedback;

import com.ecard.ecardwebshop.product.Product;
import com.ecard.ecardwebshop.user.User;

import java.time.LocalDateTime;

public class Feedback {

    private long id;
    private LocalDateTime feedbackDate;
    private String feedback;
    private int rating;
    private User user;
    private Product product;

    public Feedback() {
    }

    public Feedback(String feedback, int rating, User user, Product product) {
        this.feedbackDate = LocalDateTime.now();
        this.feedback = feedback;
        this.rating = rating;
        this.user = user;
        this.product = product;
    }

    public Feedback(long id, LocalDateTime feedbackDate, String feedback, int rating, User user, Product product) {
        this.id = id;
        this.feedbackDate = feedbackDate;
        this.feedback = feedback;
        this.rating = rating;
        this.user = user;
        this.product = product;
    }

    public LocalDateTime getFeedbackDate() {
        return feedbackDate;
    }

    public long getId() {
        return id;
    }

    public String getFeedback() {
        return feedback;
    }

    public int getRating() {
        return rating;
    }

    public User getUser() {
        return user;
    }

    public Product getProduct() {
        return product;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "feedbackDate=" + feedbackDate +
                ", feedback='" + feedback + '\'' +
                '}';
    }
}
