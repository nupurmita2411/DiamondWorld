package com.example.summer26.section1.group1.diamondworld.Turjo;

public class StockChecklistEntry {
    private String caseName;
    private int openingBalance;
    private int soldToday;
    private int physicalCount;
    private int expectedCount;
    private int discrepancy;
    private String date;

    public StockChecklistEntry() {
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public int getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(int openingBalance) {
        this.openingBalance = openingBalance;
    }

    public int getSoldToday() {
        return soldToday;
    }

    public void setSoldToday(int soldToday) {
        this.soldToday = soldToday;
    }

    public int getPhysicalCount() {
        return physicalCount;
    }

    public void setPhysicalCount(int physicalCount) {
        this.physicalCount = physicalCount;
    }

    public int getExpectedCount() {
        return expectedCount;
    }

    public void setExpectedCount(int expectedCount) {
        this.expectedCount = expectedCount;
    }

    public int getDiscrepancy() {
        return discrepancy;
    }

    public void setDiscrepancy(int discrepancy) {
        this.discrepancy = discrepancy;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}




