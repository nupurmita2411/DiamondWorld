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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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

        Product p = new Product(
                txtProductId.getText(),
                txtProductName.getText(),
                Integer.parseInt(txtStockQuantity.getText()),
                Double.parseDouble(TFPrice.getText())
        );

        File f = new File("product.bin");

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

        } catch (Exception e) {
            confirmationMessage.setText("Could not save product!");
            e.printStackTrace();
        }
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