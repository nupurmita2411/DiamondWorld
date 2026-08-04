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

    static ArrayList<Product> Productlist= new ArrayList<>();

    @FXML
    public void initialize() {
        // TableView Columns Configuration
        colProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("stockQuantity"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("price"));

        // ComboBox Items
        cmbSupplier.getItems().addAll("Supplier A", "Supplier B", "Supplier C");

        tblProducts.getItems().addAll(Productlist);
    }

    @FXML
    void btnAddToOrderOnClick(ActionEvent event) {

        Product selectedProduct = tblProducts.getSelectionModel().getSelectedItem();

        String quantityText = txtOrderQuantity.getText();

        if (selectedProduct == null || quantityText.isEmpty()) {
            lblOrderConfirmation.setText("Please select product and enter quantity.");
            return;
        }

        int quantity = Integer.parseInt(quantityText);

        if (quantity > selectedProduct.getStockQuantity()) {
            lblOrderConfirmation.setText("Not enough stock available.");
            return;
        }

        double totalCost = quantity * selectedProduct.getPrice();

        lblTotalCost.setText(String.valueOf(totalCost));

        lblOrderConfirmation.setText("Product added to order.");

        txtOrderQuantity.clear();
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
            List<Product> Productlist = (ArrayList<Product>) in.readObject();
            tblProducts.getItems().setAll(Productlist);
            lblOrderConfirmation.setText("Products loaded from file");
        } catch (IOException e) {
            lblOrderConfirmation.setText("Could not load from file");
        } catch (ClassNotFoundException e) {
            lblOrderConfirmation.setText("Invalid data in file");
        }
    }
}