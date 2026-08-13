package com.example.summer26.section1.group1.diamondworld.Nupur;



public class customerInquiry  {



    private String inquiryId;
    private String customerName;
    private String subject;
    private String details;
    private String status;

    public customerInquiry(String inquiryId, String customerName, String subject, String status, String details) {
        this.inquiryId = inquiryId;
        this.customerName = customerName;
        this.subject = subject;
        this.status = status;
        this.details = details;
    }

    public String getInquiryId() {
        return inquiryId;
    }

    public void setInquiryId(String inquiryId) {
        this.inquiryId = inquiryId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "customerInquiry{" +
                "inquiryId='" + inquiryId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", subject='" + subject + '\'' +
                ", details='" + details + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}