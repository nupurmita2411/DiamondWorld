package com.example.summer26.section1.group1.diamondworld.Turjo;

public class MonthlySalesReport {
    private int month;
    private int year;
    private double grossSales;
    private double tax;
    private double netProfit;
    private double registerTotal;

    public MonthlySalesReport() {
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

    public double getGrossSales() {
        return grossSales;
    }

    public void setGrossSales(double grossSales) {
        this.grossSales = grossSales;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getNetProfit() {
        return netProfit;
    }

    public void setNetProfit(double netProfit) {
        this.netProfit = netProfit;
    }

    public double getRegisterTotal() {
        return registerTotal;
    }

    public void setRegisterTotal(double registerTotal) {
        this.registerTotal = registerTotal;
    }
}
