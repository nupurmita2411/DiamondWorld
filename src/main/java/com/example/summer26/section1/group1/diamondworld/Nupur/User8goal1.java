package com.example.summer26.section1.group1.diamondworld.Nupur;


import com.example.summer26.section1.group1.diamondworld.ScenceSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;

public class User8goal1 {

    @FXML
    private TextField addressTextField;

    @FXML
    private Label confirmationLabel;

    @FXML
    private TextField emailTextField;

    @FXML
    private ComboBox<String> membershipComboBox;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField phoneTextField;

    @FXML
    private TextArea preferencesTextArea;
    @FXML
    private Button saveProfileButton;
    @FXML
    private Button returnHomeButton;


    @FXML
    public void initialize() {

        membershipComboBox.getItems().addAll("Regular", "Silver", "Gold", "Platinum");
    }

    @FXML
    void handleSaveProfile(ActionEvent event) {
        String name = nameTextField.getText();
        String phone = phoneTextField.getText();
        String email = emailTextField.getText();

        if (name.isEmpty() || phone.isEmpty() || email.isEmpty()) {
            confirmationLabel.setText("Please fill all required fields!");
        } else if (membershipComboBox.getValue() == null) {
            confirmationLabel.setText("Please select a Membership Type!");
        } else {
            confirmationLabel.setText("Profile saved successfully!");


            clearFields();
        }
    }

    @FXML
    void handleReturnHome(ActionEvent event) throws IOException {
        ScenceSwitcher.switchTo("/com/example/summer26/section1/group1/diamondworld/Nupur/Use8Dashboard.fxml", event);
    }

    private void clearFields() {
        nameTextField.setText("");
        phoneTextField.setText("");
        emailTextField.setText("");
        addressTextField.setText("");
        preferencesTextArea.setText("");
        membershipComboBox.setValue(null);
    }
}