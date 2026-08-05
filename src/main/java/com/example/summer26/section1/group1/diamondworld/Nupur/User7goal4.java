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
    private TableView<Product> tblProducts;

    static ArrayList<Product> Productlist= new ArrayList<>();
    @FXML
    private TextField productnameTF;
    @FXML
    private TextField wuantitytF;
    @FXML
    private TextField roductidTF;
    @FXML
    private TextField costtf;

    @FXML
    public void initialize() {
        // TableView Columns Configuration
        colProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("stockQuantity"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("price"));


        tblProducts.getItems().addAll(Productlist);
    }

    @FXML
    void btnAddToOrderOnClick(ActionEvent event) {

            String id = roductidTF.getText();
            String name = productnameTF.getText();
            String quantityText = wuantitytF.getText();
            String priceText = costtf.getText();

            if (id.isEmpty() || name.isEmpty() || quantityText.isEmpty() || priceText.isEmpty()) {

                lblOrderConfirmation.setText("Please fill all fields.");

            } else {

                int quantity = Integer.parseInt(quantityText);
                double price = Double.parseDouble(priceText);

                Product p = new Product(id, name, quantity, price);

                Productlist.add(p);



                lblOrderConfirmation.setText("Product added successfully!");

                roductidTF.clear();
                productnameTF.clear();
                wuantitytF.clear();
                costtf.clear();
            }
        }


    @FXML
    void btnReturnHomeOnClick(ActionEvent actionEvent) throws IOException {
        ScenceSwitcher.switchTo("/com/example/summer26/section1/group1/diamondworld/Nupur/User7Dashboard.fxml", actionEvent);
    }


    @FXML
    public void delete(ActionEvent actionEvent) {
        Product selected = tblProducts.getSelectionModel().getSelectedItem();

        if (selected != null) {

            Productlist.remove(selected);
            tblProducts.getItems().remove(selected);

            lblOrderConfirmation.setText("Record deleted.");

        } else {

            lblOrderConfirmation.setText("Please select a row to delete.");  }
    }

    @FXML
    public void filteringOA(ActionEvent actionEvent) {

        if (costtf.getText().isEmpty()) {
            lblOrderConfirmation.setText("Enter a price.");
            return;
        }

        double price = Double.parseDouble(costtf.getText());

        tblProducts.getItems().clear();

        for (Product p : Productlist) {
            if (p.getPrice() == price) {
                tblProducts.getItems().add(p);
            }
        }

        if (tblProducts.getItems().isEmpty()) {
            lblOrderConfirmation.setText("No product found.");
        } else {
            lblOrderConfirmation.setText("Product found.");
        }
    }
}

