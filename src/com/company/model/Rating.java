package com.company.model;
import java.sql.Date;

public class Rating {
    private int ratingId;
    private int productId;
    private int ratingValue;
    private Date ratingDate;

    // Constructor
    public Rating(int ratingId, int productId, int ratingValue, Date ratingDate) {
        this.ratingId = ratingId;
        this.productId = productId;
        this.ratingValue = ratingValue;
        this.ratingDate = ratingDate;
    }

    // Getters
    public int getRatingId() {
        return ratingId;
    }

    public int getProductId() {
        return productId;
    }

    public int getRatingValue() {
        return ratingValue;
    }

    public Date getRatingDate() {
        return ratingDate;
    }

    // Setters
    public void setRatingId(int ratingId) {
        this.ratingId = ratingId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setRatingValue(int ratingValue) {
        this.ratingValue = ratingValue;
    }

    public void setRatingDate(Date ratingDate) {
        this.ratingDate = ratingDate;
    }
}
