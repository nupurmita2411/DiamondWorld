package com.example.summer26.section1.group1.diamondworld.Turjo;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class CustomerProfileController extends BaseController {

    @FXML private TextField nameField;
    @FXML private TextField phoneField;
    @FXML private TextField emailField;
    @FXML private TextField nidField;

    private final DataStore store = DataStore.getInstance();

    @FXML
    private void onRegister() {
        if (nameField.getText().isBlank()) {
            setStatus("Name is required.");
            return;
        }
        if (!ValidationUtil.isValidPhone(phoneField.getText())) {
            setStatus("Invalid phone number format. (VL)");
            return;
        }
        if (!ValidationUtil.isValidEmail(emailField.getText())) {
            setStatus("Invalid email format. (VL)");
            return;
        }
        if (store.customerExists(phoneField.getText(), nidField.getText())) {
            setStatus("Customer already exists in membership files. (VR)");
            return;
        }
        try {
            Customer c = store.registerCustomer(
                    nameField.getText(), phoneField.getText(),
                    emailField.getText(), nidField.getText());
            setStatus(String.format("Customer registered! ID: %s | Loyalty activated with 100 points. (DP, OP)",
                    c.getId()));
            nameField.clear();
            phoneField.clear();
            emailField.clear();
            nidField.clear();
        } catch (Exception e) {
            setStatus("Registration failed: " + e.getMessage());
        }
    }
}




