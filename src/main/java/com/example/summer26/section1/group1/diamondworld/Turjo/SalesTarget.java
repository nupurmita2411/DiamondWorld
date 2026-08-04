package com.diamondworld.model;

public class SalesTarget {
    private String employeeId;
    private String employeeName;
    private double previousSales;
    private double targetAmount;
    private int month;
    private int year;

    public SalesTarget() {
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public double getPreviousSales() {
        return previousSales;
    }

    public void setPreviousSales(double previousSales) {
        this.previousSales = previousSales;
    }

    public double getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(double targetAmount) {
        this.targetAmount = targetAmount;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
