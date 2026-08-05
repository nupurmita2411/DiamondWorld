package com.example.summer26.section1.group1.diamondworld.Nupur;


import com.example.summer26.section1.group1.diamondworld.ScenceSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class User7goal5 {

    @FXML
    private ComboBox<String> cmbFilter;

    @FXML
    private TableColumn<Product, Integer> colCurrentStock;

    @FXML
    private TableColumn<Product, Integer> colMinStock;

    @FXML
    private TableColumn<Product, String> colProductId;

    @FXML
    private TableColumn<Product, String> colProductName;

    @FXML
    private Label lblConfirmation;

    @FXML
    private TableView<Product> tblLowStock;

    @FXML
    public void initialize() {

        colProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        colCurrentStock.setCellValueFactory(new PropertyValueFactory<>("stockQuantity"));
        colMinStock.setCellValueFactory(new PropertyValueFactory<>("price"));


        cmbFilter.getItems().addAll("All Categories", "Ring", "Necklace", "Dhaka Branch", "Chattogram Branch");
    }


    @FXML
    void btnRequestRestockOnClick(ActionEvent event) {
        if (tblLowStock.getItems().isEmpty()) {
            lblConfirmation.setText("No low-stock items to request restock!");
        } else {
            lblConfirmation.setText("Replenishment request sent successfully!");
        }
    }

    @FXML
    void btnReturnHomeOnClick(ActionEvent ActionEvent) throws IOException {
        ScenceSwitcher.switchTo("/com/example/summer26/section1/group1/diamondworld/Nupur/User7Dashboard.fxml", ActionEvent);
    }

    @FXML
    public void btnGenerateOA(ActionEvent actionEvent) {
        String selected = cmbFilter.getValue();
        System.out.println("Generating report for: " + selected);
    }
}