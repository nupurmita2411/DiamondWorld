package com.example.summer26.section1.group1.diamondworld.Turjo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class BranchManagerDashboardController {

    @FXML
    private Label welcomeLabel;

    @FXML
    private void initialize() {
        Employee user = Session.getCurrentUser();
        if (user != null) {
            welcomeLabel.setText("Welcome, " + user.getName() + " (Branch Manager)");
        }
    }

    @FXML private void openGoal1() { open("/com/example/summer26/section1/group1/diamondworld/turjo/manager/goal1-custom-design.fxml", "Goal 1 - Custom Design Approval"); }
    @FXML private void openGoal2() { open("/com/example/summer26/section1/group1/diamondworld/turjo/manager/goal2-sales-report.fxml", "Goal 2 - Sales Revenue Report"); }
    @FXML private void openGoal3() { open("/com/example/summer26/section1/group1/diamondworld/turjo/manager/goal3-replenishment.fxml", "Goal 3 - Stock Replenishment"); }
    @FXML private void openGoal4() { open("/com/example/summer26/section1/group1/diamondworld/turjo/manager/goal4-gold-price.fxml", "Goal 4 - Gold Price Update"); }
    @FXML private void openGoal5() { open("/com/example/summer26/section1/group1/diamondworld/turjo/manager/goal5-sales-target.fxml", "Goal 5 - Sales Targets"); }
    @FXML private void openGoal6() { open("/com/example/summer26/section1/group1/diamondworld/turjo/manager/goal6-dispute.fxml", "Goal 6 - Dispute Escalation"); }
    @FXML private void openGoal7() { open("/com/example/summer26/section1/group1/diamondworld/turjo/manager/goal7-vault-audit.fxml", "Goal 7 - Vault Audit"); }
    @FXML private void openGoal8() { open("/com/example/summer26/section1/group1/diamondworld/turjo/manager/goal8-expense.fxml", "Goal 8 - Expense Approval"); }

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




