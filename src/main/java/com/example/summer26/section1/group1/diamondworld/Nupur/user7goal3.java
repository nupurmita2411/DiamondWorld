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

    @FXML
    public void initialize() {

        colId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("emailAddress"));
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


            tblSuppliers.getItems().add(s);

            Status.setText("Status: Supplier added successfully!");
            clearFields();
        }
    }

    @FXML
    void btnUpdateSupplier(ActionEvent event) {
        supplier selectedSupplier = tblSuppliers.getSelectionModel().getSelectedItem();

        if (selectedSupplier != null) {
            selectedSupplier.setSupplierName(txtSupplierName.getText());
            selectedSupplier.setContactNumber(txtContactNumber.getText());
            selectedSupplier.setEmailAddress(txtEmailAddress.getText());
            selectedSupplier.setAddress(txtSupplierAddress.getText());

            tblSuppliers.refresh();
            Status.setText("Status: Supplier updated successfully!");
            clearFields();
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
            clearFields();
        } else {
            Status.setText("Status: Select a supplier from table to delete!");
        }
    }

    @FXML
    void btnReturnHome(ActionEvent event) throws IOException {
        ScenceSwitcher.switchTo("/com/example/summer26/section1/group1/diamondworld/Nupur/Use7Dashboard.fxml", event);
    }

    private void clearFields() {
        txtSupplierId.setText("");
        txtSupplierName.setText("");
        txtContactNumber.setText("");
        txtEmailAddress.setText("");
        txtSupplierAddress.setText("");
    }
}