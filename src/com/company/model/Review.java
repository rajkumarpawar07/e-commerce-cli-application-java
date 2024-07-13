package com.company.model;

public class Review {
    private int reviewId;
    private int productId;
    private int customerId;
    private String reviewText;
    private int rating;

    // Constructor
    public Review(int reviewId, int productId, int customerId, String reviewText, int rating) {
        this.reviewId = reviewId;
        this.productId = productId;
        this.customerId = customerId;
        this.reviewText = reviewText;
        this.rating = rating;
    }

    // Getters
    public int getReviewId() {
        return reviewId;
    }

    public int getProductId() {
        return productId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getReviewText() {
        return reviewText;
    }

    public int getRating() {
        return rating;
    }

    // Setters
    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}

