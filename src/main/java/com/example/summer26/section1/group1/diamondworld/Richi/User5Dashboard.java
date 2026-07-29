package com.example.summer26.section1.group1.diamondworld.Richi;

import com.example.summer26.section1.group1.diamondworld.ScenceSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class User5Dashboard {

    @FXML
    public void initialize() {
        // Initialize logic (if any)
    }

    @FXML
    public void handleGenerateCustomerBill(ActionEvent actionEvent) throws IOException {
        ScenceSwitcher.switchTo("/com/example/summer26/section1/group1/diamondworld/Richi/user5goal1.fxml", actionEvent);
    }

    @FXML
    public void handleCustomerPayment(ActionEvent actionEvent) throws IOException {
        ScenceSwitcher.switchTo("/com/example/summer26/section1/group1/diamondworld/Richi/User5Goal2.fxml", actionEvent);
    }

    @FXML
    public void handlePrintPurchaseReceipt(ActionEvent actionEvent) throws IOException {
        ScenceSwitcher.switchTo("/com/example/summer26/section1/group1/diamondworld/Richi/User5Goal3.fxml", actionEvent);
    }

    @FXML
    public void handleViewDailySalesTransactions(ActionEvent actionEvent) throws IOException {
        ScenceSwitcher.switchTo("/com/example/summer26/section1/group1/diamondworld/Richi/User5Goal4.fxml", actionEvent);
    }

    @FXML
    public void handleApplyCustomerDiscount(ActionEvent actionEvent) throws IOException {
        ScenceSwitcher.switchTo("/com/example/summer26/section1/group1/diamondworld/Richi/User5Goal5.fxml", actionEvent);
    }

    @FXML
    public void handleSearchPurchaseHistory(ActionEvent actionEvent) throws IOException {
        ScenceSwitcher.switchTo("/com/example/summer26/section1/group1/diamondworld/Richi/User5Goal6.fxml", actionEvent);
    }

    @FXML
    public void handleCancelIncorrectInvoice(ActionEvent actionEvent) throws IOException {
        ScenceSwitcher.switchTo("/com/example/summer26/section1/group1/diamondworld/Richi/User5Goal7.fxml", actionEvent);
    }

    @FXML
    public void handleEndOfDaySalesReport(ActionEvent actionEvent) throws IOException {
        ScenceSwitcher.switchTo("/com/example/summer26/section1/group1/diamondworld/Richi/User5Goal8.fxml", actionEvent);
    }}
