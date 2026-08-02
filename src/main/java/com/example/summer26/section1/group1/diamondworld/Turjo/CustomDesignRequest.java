package com.example.summer26.section1.group1.diamondworld.Turjo;

public class CustomDesignRequest {
    private String id;
    private String customerName;
    private String metalType;
    private String ringSize;
    private String diamondCut;
    private String status;
    private boolean feasible;
    private double gemstoneEstimate;
    private double metalWeight;
    private double laborCost;
    private double markupFactor;

    public CustomDesignRequest() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getMetalType() {
        return metalType;
    }

    public void setMetalType(String metalType) {
        this.metalType = metalType;
    }

    public String getRingSize() {
        return ringSize;
    }

    public void setRingSize(String ringSize) {
        this.ringSize = ringSize;
    }

    public String getDiamondCut() {
        return diamondCut;
    }

    public void setDiamondCut(String diamondCut) {
        this.diamondCut = diamondCut;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isFeasible() {
        return feasible;
    }

    public void setFeasible(boolean feasible) {
        this.feasible = feasible;
    }

    public double getGemstoneEstimate() {
        return gemstoneEstimate;
    }

    public void setGemstoneEstimate(double gemstoneEstimate) {
        this.gemstoneEstimate = gemstoneEstimate;
    }

    public double getMetalWeight() {
        return metalWeight;
    }

    public void setMetalWeight(double metalWeight) {
        this.metalWeight = metalWeight;
    }

    public double getLaborCost() {
        return laborCost;
    }

    public void setLaborCost(double laborCost) {
        this.laborCost = laborCost;
    }

    public double getMarkupFactor() {
        return markupFactor;
    }

    public void setMarkupFactor(double markupFactor) {
        this.markupFactor = markupFactor;
    }

    public double getTotalQuote() {
        return (gemstoneEstimate + metalWeight * 8500 + laborCost) * markupFactor;
    }

    @Override
    public String toString() {
        return id + " - " + customerName + " (" + status + ")";
    }
}
