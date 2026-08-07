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

public class ShowcustomerdetailsViewController {

    @FXML
    private TableView<Customer> customerTV;

    @FXML
    private TableColumn<Customer, String> nameTC;

    @FXML
    private TableColumn<Customer, String> phoneTC;

    @FXML
    private TableColumn<Customer, String> emailTC;

    @FXML
    private TableColumn<Customer, String> addressTC;

    @FXML
    private TableColumn<Customer, String> membershipTC;

    @FXML
    private TableColumn<Customer, String> preferencesTC;

    @FXML
    private Label messageLabel;

    @FXML
    private AnchorPane mainPane;


    @FXML
    public void initialize() {

        nameTC.setCellValueFactory(new PropertyValueFactory<>("name"));
        phoneTC.setCellValueFactory(new PropertyValueFactory<>("phone"));
        emailTC.setCellValueFactory(new PropertyValueFactory<>("email"));
        addressTC.setCellValueFactory(new PropertyValueFactory<>("address"));
        membershipTC.setCellValueFactory(new PropertyValueFactory<>("membership"));
        preferencesTC.setCellValueFactory(new PropertyValueFactory<>("preferences"));

    }


    @FXML
    public void loadButtonOA(ActionEvent actionEvent) {

        File f = new File("customer.bin");

        try {

            FileInputStream fis = new FileInputStream(f);
            ObjectInputStream ois = new ObjectInputStream(fis);

            customerTV.getItems().clear();

            while (true) {

                try {

                    Customer c = (Customer) ois.readObject();


                } catch (EOFException e) {

                    messageLabel.setText("Customer details loaded successfully.");
                    ois.close();
                    break;

                }
            }

        } catch (Exception e) {

            messageLabel.setText("Could not load customer details.");
            e.printStackTrace();

        }
    }


    @FXML
    public void previousButtonOA(ActionEvent actionEvent) {

        try {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("User8goal1.fxml")
            );

            Node node = loader.load();

            mainPane.getChildren().setAll(node);

        } catch (Exception e) {

            e.printStackTrace();

        }
    }
}