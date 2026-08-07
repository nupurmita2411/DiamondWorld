package com.example.summer26.section1.group1.diamondworld.Nupur;


import com.example.summer26.section1.group1.diamondworld.ScenceSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.File;
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
    private Button saveProfileButton;
    @FXML
    private Button returnHomeButton;
    @FXML
    private AnchorPane mainPane;


    @FXML
    public void initialize() {

        membershipComboBox.getItems().addAll("Regular", "Silver", "Gold", "Platinum");
    }

    @FXML
    void handleSaveProfile(ActionEvent event) {

        Customer c = new Customer(
                nameTextField.getText(),
                phoneTextField.getText(),
                emailTextField.getText(),
                addressTextField.getText(),
                membershipComboBox.getValue(),
                preferencesTextArea.getText()
        );

        File f = new File("customer.bin");

        try {
            FileOutputStream fos;
            ObjectOutputStream oos;

            if (f.exists()) {
                fos = new FileOutputStream(f, true);
                oos = new AppendableObjectOutputStream(fos);
            } else {
                fos = new FileOutputStream(f);
                oos = new ObjectOutputStream(fos);
            }

            oos.writeObject(c);
            oos.close();

            confirmationLabel.setText("Customer saved successfully!");

            nameTextField.clear();
            phoneTextField.clear();
            emailTextField.clear();
            addressTextField.clear();
            preferencesTextArea.clear();
            membershipComboBox.setValue(null);

        } catch (Exception e) {
            confirmationLabel.setText("Could not save customer!");
            e.printStackTrace();
        }
    }

    @FXML
    void handleReturnHome(ActionEvent actionEvent) throws IOException {
        ScenceSwitcher.switchTo("/com/example/summer26/section1/group1/diamondworld/Nupur/User8Dashboard.fxml", actionEvent);
    }

    @FXML
    public void nextButtonOA(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ShowcustomerdetailsView.fxml"));
            Node node = loader.load();
            mainPane.getChildren().setAll(node);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    }
