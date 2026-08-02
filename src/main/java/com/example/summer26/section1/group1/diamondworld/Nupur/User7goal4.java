package com.example.summer26.section1.group1.diamondworld.Nupur;



import com.example.summer26.section1.group1.diamondworld.ScenceSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class User7goal4 {

    @FXML
    private ComboBox<String> cmbSupplier;

    @FXML
    private TableColumn<Product, Double> colCost;

    @FXML
    private TableColumn<Product, String> colProductId;

    @FXML
    private TableColumn<Product, String> colProductName;

    @FXML
    private TableColumn<Product, Integer> colQuantity;

    @FXML
    private Label lblOrderConfirmation;

    @FXML
    private Label lblTotalCost;

    @FXML
    private TableView<Product> tblProducts;

    @FXML
    private TextField txtOrderQuantity;

    @FXML
    public void initialize() {
        // TableView Columns Configuration
        colProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("stockQuantity"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("price"));

        // ComboBox Items
        cmbSupplier.getItems().addAll("Supplier A", "Supplier B", "Supplier C");

        // Load Products from product.bin file
        loadProductsFromFile();
    }

    @FXML
    void btnAddToOrderOnClick(ActionEvent event) {
        Product selected = tblProducts.getSelectionModel().getSelectedItem();
        if (selected == null) {
            lblOrderConfirmation.setText("Please select a product!");
            return;
        }

        int quantity = 0;
        try {
            quantity = Integer.parseInt(txtOrderQuantity.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Quantity must be a whole number.");
            alert.showAndWait();
            return;
        }

        double total = selected.getPrice() * quantity;
        lblTotalCost.setText(String.valueOf(total));
        lblOrderConfirmation.setText("Added to order calculation!");

        txtOrderQuantity.setText("");
    }

    @FXML
    void btnSubmitOrderOnClick(ActionEvent event) {
        if (cmbSupplier.getValue() == null) {
            lblOrderConfirmation.setText("Please select a supplier!");
        } else if (lblTotalCost.getText().isEmpty() || lblTotalCost.getText().equals("0.0")) {
            lblOrderConfirmation.setText("Please calculate total cost first!");
        } else {
            lblOrderConfirmation.setText("Order submitted successfully!");
            lblTotalCost.setText("0.0");
            cmbSupplier.setValue(null);
        }
    }

    @FXML
    void btnReturnHomeOnClick(ActionEvent event) throws IOException {
        ScenceSwitcher.switchTo("/com/example/summer26/section1/group1/diamondworld/Nupur/Use7Dashboard.fxml", event);
    }

    @FXML
    private void loadProductsFromFile() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("product.bin"))) {
            tblProducts.getItems().clear();
            List<Product> tempList = (ArrayList<Product>) in.readObject();
            tblProducts.getItems().setAll(tempList);
            lblOrderConfirmation.setText("Products loaded from file");
        } catch (IOException e) {
            lblOrderConfirmation.setText("Could not load from file");
        } catch (ClassNotFoundException e) {
            lblOrderConfirmation.setText("Invalid data in file");
        }
    }
}