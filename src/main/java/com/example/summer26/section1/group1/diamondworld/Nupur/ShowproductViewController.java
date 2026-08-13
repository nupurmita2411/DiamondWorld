package com.example.summer26.section1.group1.diamondworld.Nupur;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class ShowproductViewController {

    @FXML
    private TableView<Product> productTV;

    @FXML
    private TableColumn<Product, String> productIdTC;

    @FXML
    private TableColumn<Product, String> productNameTC;

    @FXML
    private TableColumn<Product, Integer> stockQuantityTC;

    @FXML
    private TableColumn<Product, Double> priceTC;

    @FXML
    private Label messageLabel;

    @FXML
    private AnchorPane mainPane;

    @FXML
    public void initialize() {
        productIdTC.setCellValueFactory(new PropertyValueFactory<>("productId"));
        productNameTC.setCellValueFactory(new PropertyValueFactory<>("productName"));
        stockQuantityTC.setCellValueFactory(new PropertyValueFactory<>("stockQuantity"));
        priceTC.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    @FXML
    public void loadButtonOA(ActionEvent actionEvent) {

        File f = new File("product.bin");

        if (!f.exists()) {
            messageLabel.setText("No product file found!");
            return;
        }

        productTV.getItems().clear();

        try (FileInputStream fis = new FileInputStream(f);
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            while (true) {
                try {
                    Product p = (Product) ois.readObject();
                    productTV.getItems().add(p);
                } catch (EOFException e) {
                    break;
                }
            }

            if (productTV.getItems().isEmpty()) {
                messageLabel.setText("Product list is empty.");
            } else {
                messageLabel.setText("Products loaded successfully.");
            }

        } catch (Exception e) {
            messageLabel.setText("Could not load products.");
            e.printStackTrace();
        }
    }

    @FXML
    public void previousButtonOA(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("user7goal1.fxml"));
            Node node = loader.load();
            mainPane.getChildren().setAll(node);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}