package com.example.summer26.section1.group1.diamondworld.Turjo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SalesExecutiveDashboardController {

    @FXML
    private Label welcomeLabel;

    @FXML
    private void initialize() {
        Employee user = Session.getCurrentUser();
        if (user != null) {
            welcomeLabel.setText("Welcome, " + user.getName() + " (Sales Executive)");
        }
    }

    @FXML private void openGoal1() { open("/com/example/summer26/section1/group1/diamondworld/turjo/sales/goal1-pos-sale.fxml", "Goal 1 - Retail Sale"); }
    @FXML private void openGoal2() { open("/com/example/summer26/section1/group1/diamondworld/turjo/sales/goal2-stock-search.fxml", "Goal 2 - Stock Search"); }
    @FXML private void openGoal3() { open("/com/example/summer26/section1/group1/diamondworld/turjo/sales/goal3-customer.fxml", "Goal 3 - Customer Profile"); }
    @FXML private void openGoal4() { open("/com/example/summer26/section1/group1/diamondworld/turjo/sales/goal4-custom-order.fxml", "Goal 4 - Custom Order"); }
    @FXML private void openGoal5() { open("/com/example/summer26/section1/group1/diamondworld/turjo/sales/goal5-gold-exchange.fxml", "Goal 5 - Gold Exchange"); }
    @FXML private void openGoal6() { open("/com/example/summer26/section1/group1/diamondworld/turjo/sales/goal6-reservation.fxml", "Goal 6 - VIP Reservation"); }
    @FXML private void openGoal7() { open("/com/example/summer26/section1/group1/diamondworld/turjo/sales/goal7-repair-track.fxml", "Goal 7 - Repair Tracking"); }
    @FXML private void openGoal8() { open("/com/example/summer26/section1/group1/diamondworld/turjo/sales/goal8-stock-checklist.fxml", "Goal 8 - Stock Checklist"); }

    @FXML
    private void onSignOut() {
        try {
            Navigation.signOut();
        } catch (Exception ignored) {
        }
    }

    private void open(String path, String title) {
        try {
            Navigation.openGoal(path, title);
        } catch (Exception e) {
            welcomeLabel.setText("Error: " + e.getMessage());
        }
    }
}




