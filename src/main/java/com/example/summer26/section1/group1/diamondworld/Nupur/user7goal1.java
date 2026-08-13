package com.example.summer26.section1.group1.diamondworld.Nupur;

import com.example.summer26.section1.group1.diamondworld.ScenceSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class user7goal1 {

    @FXML
    private TextField txtProductId;
    @FXML
    private TextField txtProductName;
    @FXML
    private ComboBox<String> cmbCategory;
    @FXML
    private ComboBox<String> cmbBranch;
    @FXML
    private TextField TFWeight;
    @FXML
    private TextField TFPrice;
    @FXML
    private TextField txtStockQuantity;
    @FXML
    private Label confirmationMessage;
    @FXML
    private AnchorPane mainPane;

    @FXML
    public void initialize() {
        cmbBranch.getItems().addAll("Dhaka", "Chattogram", "Khulna", "Rajshahi");
        cmbCategory.getItems().addAll("Ring", "Necklace", "Bracelet", "Earring");
    }

    @FXML
    public void btnsaveStock(ActionEvent actionEvent) {

        String id = txtProductId.getText();
        String name = txtProductName.getText();
        String qtyText = txtStockQuantity.getText();
        String priceText = TFPrice.getText();


        if (id.isEmpty() || name.isEmpty() || qtyText.isEmpty() || priceText.isEmpty()) {
            confirmationMessage.setText("Please fill in all required fields!");
            return;
        }

        try {
            int quantity = Integer.parseInt(qtyText);
            double price = Double.parseDouble(priceText);

            if (quantity <= 0 || price <= 0) {
                confirmationMessage.setText("Quantity and Price must be greater than 0!");
                return;
            }

            File f = new File("product.bin");

            if (isProductIdDuplicate(f, id)) {
                confirmationMessage.setText("Product ID already exists! Enter a unique ID.");
                return;
            }


            Product p = new Product(id, name, quantity, price);

            FileOutputStream fos;
            ObjectOutputStream oos;

            if (f.exists()) {
                fos = new FileOutputStream(f, true);
                oos = new AppendableObjectOutputStream(fos);
            } else {
                fos = new FileOutputStream(f);
                oos = new ObjectOutputStream(fos);
            }

            oos.writeObject(p);
            oos.close();

            confirmationMessage.setText("Product saved successfully!");


            txtProductId.clear();
            txtProductName.clear();
            txtStockQuantity.clear();
            TFPrice.clear();
            TFWeight.clear();
            cmbBranch.setValue(null);
            cmbCategory.setValue(null);

        } catch (NumberFormatException e) {
            confirmationMessage.setText("Quantity and Price must be valid numbers!");
        } catch (Exception e) {
            confirmationMessage.setText("Could not save product!");
            e.printStackTrace();
        }
    }


    private boolean isProductIdDuplicate(File f, String id) {
        if (!f.exists()) {
            return false;
        }

        try (FileInputStream fis = new FileInputStream(f);
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            while (true) {
                try {
                    Product p = (Product) ois.readObject();
                    if (p.getProductId() != null && p.getProductId().equals(id)) {
                        return true;
                    }
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @FXML
    public void nextButtonOA(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ShowproductView.fxml"));
            Node node = loader.load();
            mainPane.getChildren().setAll(node);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void btnReturnHome(ActionEvent actionEvent) throws IOException {
        ScenceSwitcher.switchTo(
                "/com/example/summer26/section1/group1/diamondworld/Nupur/User7Dashboard.fxml",
                actionEvent
        );
    }
}