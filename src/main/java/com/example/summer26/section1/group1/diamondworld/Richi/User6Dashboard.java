package com.example.summer26.section1.group1.diamondworld.Richi;

import com.example.summer26.section1.group1.diamondworld.ScenceSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class User6Dashboard {

    @FXML
    public void initialize() {
        // Initialize logic (if any)
    }

    @FXML
    public void handleCampaignManagement(ActionEvent actionEvent) throws IOException {
        ScenceSwitcher.switchTo("/com/example/summer26/section1/group1/diamondworld/Richi/user6goal1.fxml", actionEvent);
    }

    @FXML
    public void handleSeasonalPromotions(ActionEvent actionEvent) throws IOException {
        ScenceSwitcher.switchTo("/com/example/summer26/section1/group1/diamondworld/Richi/User6Goal2.fxml", actionEvent);
    }

    @FXML
    public void handleCustomerOutreach(ActionEvent actionEvent) throws IOException {
        ScenceSwitcher.switchTo("/com/example/summer26/section1/group1/diamondworld/Richi/User6Goal3.fxml", actionEvent);
    }

    @FXML
    public void handleCampaignAnalytics(ActionEvent actionEvent) throws IOException {
        ScenceSwitcher.switchTo("/com/example/summer26/section1/group1/diamondworld/Richi/User6Goal4.fxml", actionEvent);
    }

    @FXML
    public void handleCouponManagement(ActionEvent actionEvent) throws IOException {
        ScenceSwitcher.switchTo("/com/example/summer26/section1/group1/diamondworld/Richi/User6Goal5.fxml", actionEvent);
    }

    @FXML
    public void handleCustomerEngagement(ActionEvent actionEvent) throws IOException {
        ScenceSwitcher.switchTo("/com/example/summer26/section1/group1/diamondworld/Richi/User6Goal6.fxml", actionEvent);
    }

    @FXML
    public void handleLoyaltyPromotion(ActionEvent actionEvent) throws IOException {
        ScenceSwitcher.switchTo("/com/example/summer26/section1/group1/diamondworld/Richi/User6Goal7.fxml", actionEvent);
    }

    @FXML
    public void handleMarketingReports(ActionEvent actionEvent) throws IOException {
        ScenceSwitcher.switchTo("/com/example/summer26/section1/group1/diamondworld/Richi/User6Goal8.fxml", actionEvent);
    }}
