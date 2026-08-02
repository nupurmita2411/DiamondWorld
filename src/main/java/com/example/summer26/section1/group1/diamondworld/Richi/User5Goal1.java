package com.example.summer26.section1.group1.diamondworld.Richi;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class User5Goal1 {

    @FXML private Button btnAddProduct;
    @FXML private Button btnGenerateInvoice;
    @FXML private Button btnReturnHome;

    @FXML private TableView<BillingItem> billingTableV;
    @FXML private TableColumn<BillingItem, String> colProductCode;
    @FXML private TableColumn<BillingItem, String> colProductName;
    @FXML private TableColumn<BillingItem, Integer> colQuantity;
    @FXML private TableColumn<BillingItem, Double> colUnitPrice;
    @FXML private TableColumn<BillingItem, Double> colSubtotal;

    @FXML private Label lblFinalTotal;
    @FXML private Label lblSubTotal;
    @FXML private Label lblTaxDiscount;

    @FXML private TextField txtCustomerID;
    @FXML private TextField txtCustomerPhone;
    @FXML private TextField txtProductCode;
    @FXML private TextField txtProductQuantity;

    private final ObservableList<BillingItem> itemList = FXCollections.observableArrayList();
    private double totalAmount = 0.0;
    @FXML
    private Button signOutTextField;

    @FXML
    public void initialize() {
        // Table Columns Binding with BillingItem Model
        colProductCode.setCellValueFactory(new PropertyValueFactory<>("productCode"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colSubtotal.setCellValueFactory(new PropertyValueFactory<>("subtotal"));

        billingTableV.setItems(itemList);
    }

    @FXML
    void handleAddProduct(ActionEvent event) {
        String code = txtProductCode.getText().trim();
        String qtyStr = txtProductQuantity.getText().trim();

        if (code.isEmpty() || qtyStr.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Please enter Product Code and Quantity!");
            return;
        }

        try {
            int qty = Integer.parseInt(qtyStr);


            String name = "Diamond Item (" + code + ")";
            double unitPrice = 1500.0;
            double subtotal = unitPrice * qty;


            BillingItem newItem = new BillingItem(code, name, unitPrice, qty, subtotal);
            itemList.add(newItem);

            totalAmount += subtotal;
            updateTotals();

            txtProductCode.clear();
            txtProductQuantity.clear();

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Quantity must be a valid number!");
        }
    }

    @FXML
    void handleGenerateInvoice(ActionEvent event) {
        if (itemList.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Warning", "No products added to generate invoice!");
            return;
        }

        String customerId = txtCustomerID.getText().trim();
        String phone = txtCustomerPhone.getText().trim();

        showAlert(Alert.AlertType.INFORMATION, "Success",
                "Invoice Generated Successfully!\nCustomer ID: " + customerId +
                        "\nPhone: " + phone +
                        "\nTotal Amount: " + totalAmount + " BDT");


        itemList.clear();
        txtCustomerID.clear();
        txtCustomerPhone.clear();
        totalAmount = 0.0;
        updateTotals();
    }

    @FXML
    void handleReturnHome(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("User5Dashboard.fxml")));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Could not load Home Screen!");
        }
    }

    private void updateTotals() {
        lblSubTotal.setText("Subtotal: " + totalAmount + " BDT");
        lblTaxDiscount.setText("Tax/Discount: 0.00 BDT");
        lblFinalTotal.setText("Final Total: " + totalAmount + " BDT");
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    public void signOutOnButtonClick(ActionEvent actionEvent) {
    }
}