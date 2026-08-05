package com.example.summer26.section1.group1.diamondworld.Nupur;

import com.example.summer26.section1.group1.diamondworld.ScenceSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class user7goal1 {

    @FXML private TextField txtProductId;
    @FXML private TextField txtProductName;
    @FXML private ComboBox<String> cmbCategory;
    @FXML private ComboBox<String> cmbBranch;
    @FXML private TextField TFWeight;
    @FXML private TextField TFPrice;
    @FXML private TextField txtStockQuantity;
    @FXML private Label confirmationMessage;

    @FXML
    public void initialize() {
        cmbBranch.getItems().addAll("Dhaka", "Chattogram", "Khulna", "Rajshahi");
        cmbCategory.getItems().addAll("Ring", "Necklace", "Bracelet", "Earring");
    }

    @FXML
    public void btnsaveStock(ActionEvent actionEvent) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("product.bin"))) {

            confirmationMessage.setText("Objects have been written to file successfully!");
        } catch (IOException e) {
            confirmationMessage.setText("Could not save to file!");
        }
    }

    @FXML
    public void btnReturnHome(ActionEvent actionEvent) throws IOException {
        ScenceSwitcher.switchTo("/com/example/summer26/section1/group1/diamondworld/Nupur/User7Dashboard.fxml", actionEvent);
    }
}