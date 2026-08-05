package com.example.summer26.section1.group1.diamondworld.Nupur;
import com.example.summer26.section1.group1.diamondworld.ScenceSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.ArrayList;

public class user7goal3 {

    @FXML
    private Label Status;

    @FXML
    private TableColumn<supplier, String> colContact;

    @FXML
    private TableColumn<supplier, String> colId;

    @FXML
    private TableColumn<supplier, String> colName;

    @FXML
    private TableColumn<supplier, String> colStatus;

    @FXML
    private TableView<supplier> tblSuppliers;

    @FXML
    private TextField txtContactNumber;

    @FXML
    private TextField txtEmailAddress;

    @FXML
    private TextArea txtSupplierAddress;

    @FXML
    private TextField txtSupplierId;

    @FXML
    private TextField txtSupplierName;

    static ArrayList<supplier> supplierlist= new ArrayList<>();

    @FXML
    public void initialize() {

        colId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("emailAddress"));
        tblSuppliers.getItems().addAll(supplierlist);
    }

    @FXML
    void btnAddSupplier(ActionEvent event) {
        String id = txtSupplierId.getText();
        String name = txtSupplierName.getText();
        String contact = txtContactNumber.getText();
        String email = txtEmailAddress.getText();
        String address = txtSupplierAddress.getText();

        if (id.isEmpty() || name.isEmpty()) {
            Status.setText("Status: Please enter Supplier ID and Name!");
        } else {

            supplier s = new supplier(id, name, contact, email, address);


            Status.setText("Status: Supplier added successfully!");
            txtSupplierId.clear();
        txtSupplierName.clear();
        txtContactNumber.clear();
        txtEmailAddress.clear();
        txtSupplierAddress.clear();
        }
    }



    @FXML
    void btnUpdateSupplier(ActionEvent actionEvent) {
        supplier selectedSupplier = tblSuppliers.getSelectionModel().getSelectedItem();

        if (selectedSupplier != null) {
            selectedSupplier.setSupplierName(txtSupplierName.getText());
            selectedSupplier.setContactNumber(txtContactNumber.getText());
            selectedSupplier.setEmailAddress(txtEmailAddress.getText());
            selectedSupplier.setAddress(txtSupplierAddress.getText());

            tblSuppliers.refresh();
            Status.setText("Status: Supplier updated successfully!");

        } else if (!txtSupplierId.getText().isEmpty()) {
            Status.setText("Status: Select a row from table to update!");
        } else {
            Status.setText("Status: Enter Supplier ID / Select row!");
        }
    }

    @FXML
    void btnDeleteSupplier(ActionEvent event) {
        supplier selectedSupplier = tblSuppliers.getSelectionModel().getSelectedItem();

        if (selectedSupplier != null) {
            tblSuppliers.getItems().remove(selectedSupplier);
            Status.setText("Status: Supplier deleted successfully!");

        } else {
            Status.setText("Status: Select a supplier from table to delete!");
        }
    }

    @FXML
    void btnReturnHome(ActionEvent actionEvent) throws IOException {
        ScenceSwitcher.switchTo("/com/example/summer26/section1/group1/diamondworld/Nupur/User7Dashboard.fxml",actionEvent);
    }


}
