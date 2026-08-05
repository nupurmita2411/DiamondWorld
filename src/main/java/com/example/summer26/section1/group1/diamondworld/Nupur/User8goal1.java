package com.example.summer26.section1.group1.diamondworld.Nupur;


import com.example.summer26.section1.group1.diamondworld.ScenceSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

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
    public void initialize() {

        membershipComboBox.getItems().addAll("Regular", "Silver", "Gold", "Platinum");
    }

    @FXML
    void handleSaveProfile(ActionEvent event) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("feedback.bin"))) {

            confirmationLabel.setText("Objects have been written to file successfully!");
        } catch (IOException e) {
            confirmationLabel.setText("Could not save to file!");
        }
    }

    @FXML
    void handleReturnHome(ActionEvent actionEvent) throws IOException {
        ScenceSwitcher.switchTo("/com/example/summer26/section1/group1/diamondworld/Nupur/User8Dashboard.fxml", actionEvent);
    }

}