package com.example.summer26.section1.group1.diamondworld.Nupur;

import java.io.Serializable;

public class customerFeedback  {
    private String feedbackId;
    private String customerName;
    private String rating;
    private String date;
    private String details;

    public customerFeedback(String feedbackId, String customerName, String date, String rating, String details) {
        this.feedbackId = feedbackId;
        this.customerName = customerName;
        this.date = date;
        this.rating = rating;
        this.details = details;
    }

    public String getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(String feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "customerFeedback{" +
                "feedbackId='" + feedbackId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", rating='" + rating + '\'' +
                ", date='" + date + '\'' +
                ", details='" + details + '\'' +
                '}';
    }
}
