package com.example.summer26.section1.group1.diamondworld.Nupur;
import com.example.summer26.section1.group1.diamondworld.ScenceSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class user7goal2 {

    @FXML
    private ComboBox<String> cmbDestinationBranch;

    @FXML
    private ComboBox<String> cmbSourceBranch;

    @FXML
    private Label sarchlvl;

    @FXML
    private Label statuslvl;

    @FXML
    private TextField txtProductId;

    @FXML
    private TextField txtTransferQuantity;

    @FXML
    public void initialize() {

        cmbSourceBranch.getItems().addAll("Dhaka", "Chattogram", "Khulna", "Rajshahi");
        cmbDestinationBranch.getItems().addAll("Dhaka", "Chattogram", "Khulna", "Rajshahi");
    }

    @FXML
    void btnSearchProduct(ActionEvent event) {
        String id = txtProductId.getText();

        if (id.isEmpty()) {
            sarchlvl.setText("Enter Product ID");
        } else {
            sarchlvl.setText("Product Found!");
        }
    }

    @FXML
    void btnTransferStock(ActionEvent event) {
        String source = cmbSourceBranch.getValue();
        String dest = cmbDestinationBranch.getValue();
        String qty = txtTransferQuantity.getText();

        if (source == null || dest == null) {
            statuslvl.setText("Status: Select Branches");
        } else if (qty.isEmpty()) {
            statuslvl.setText("Status: Enter Quantity");
        } else {
            statuslvl.setText("Status: Transferred Successfully!");


            txtProductId.setText("");
            txtTransferQuantity.setText("");
        }
    }

    @FXML
    void btnReturnHome(ActionEvent actionEvent) throws IOException {
        ScenceSwitcher.switchTo("/com/example/summer26/section1/group1/diamondworld/Nupur/User7Dashboard.fxml", actionEvent);
    }
}