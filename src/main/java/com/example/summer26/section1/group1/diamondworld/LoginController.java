package com.example.summer26.section1.group1.diamondworld;

import com.example.summer26.section1.group1.diamondworld.Turjo.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.Optional;

public class LoginController {

    @FXML
    private TextField employeeIdField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    @FXML
    private void onLogin() {
        String id = employeeIdField.getText();
        String password = passwordField.getText();

        Optional<String> validation = AuthService.validateLoginInput(id, password);
        if (validation.isPresent()) {
            errorLabel.setText(validation.get());
            return;
        }

        Optional<Employee> employee = AuthService.authenticate(id, password);
        if (employee.isEmpty()) {
            errorLabel.setText("Invalid credentials. Verification failed. (VR)");
            return;
        }

        Session.setCurrentUser(employee.get());
        errorLabel.setText("");
        try {
            if (employee.get().getRole() == UserRole.BRANCH_MANAGER) {
                Navigation.loadScene(
                        "/com/example/summer26/section1/group1/diamondworld/turjo/branch-manager-dashboard.fxml",
                        "Diamond World - Branch Manager Dashboard");
            } else if (employee.get().getRole() == UserRole.SALES_EXECUTIVE) {
                Navigation.loadScene(
                        "/com/example/summer26/section1/group1/diamondworld/turjo/sales-executive-dashboard.fxml",
                        "Diamond World - Sales Executive Dashboard");


            }

        } catch (Exception e) {
            errorLabel.setText("Failed to load dashboard: " + e.getMessage());
        }
    }
}
