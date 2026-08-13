package com.example.summer26.section1.group1.diamondworld.Turjo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;

public class CustomerProfileController extends BaseController {

    @FXML private TextField nameField;
    @FXML private TextField phoneField;
    @FXML private TextField emailField;
    @FXML private TextField nidField;

    @FXML private TableView<Customer> customerTable;
    @FXML private TableColumn<Customer, String> idCol;
    @FXML private TableColumn<Customer, String> nameCol;
    @FXML private TableColumn<Customer, String> phoneCol;
    @FXML private TableColumn<Customer, String> emailCol;
    @FXML private TableColumn<Customer, Integer> pointsCol;

    private final DataStore store = DataStore.getInstance();
    private ObservableList<Customer> customerList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        if (customerTable != null) {
            customerTable.setEditable(true);

            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
            emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
            pointsCol.setCellValueFactory(new PropertyValueFactory<>("loyaltyPoints"));

            idCol.setCellFactory(TextFieldTableCell.forTableColumn());
            idCol.setOnEditCommit(e -> { if (e.getRowValue() != null) e.getRowValue().setId(e.getNewValue()); });

            nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
            nameCol.setOnEditCommit(e -> { if (e.getRowValue() != null) e.getRowValue().setName(e.getNewValue()); });

            phoneCol.setCellFactory(TextFieldTableCell.forTableColumn());
            phoneCol.setOnEditCommit(e -> { if (e.getRowValue() != null) e.getRowValue().setPhone(e.getNewValue()); });

            emailCol.setCellFactory(TextFieldTableCell.forTableColumn());
            emailCol.setOnEditCommit(e -> { if (e.getRowValue() != null) e.getRowValue().setEmail(e.getNewValue()); });

            pointsCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
            pointsCol.setOnEditCommit(e -> { if (e.getRowValue() != null && e.getNewValue() != null) e.getRowValue().setLoyaltyPoints(e.getNewValue()); });

            loadCustomers();

            customerTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
                if (newSel != null) {
                    if (nameField != null) nameField.setText(newSel.getName());
                    if (phoneField != null) phoneField.setText(newSel.getPhone());
                    if (emailField != null) emailField.setText(newSel.getEmail());
                    if (nidField != null) nidField.setText(newSel.getNid() != null ? newSel.getNid() : "");
                }
            });
        }
    }

    private void loadCustomers() {
        var all = store.getAllCustomers();
        if (all != null && !all.isEmpty()) {
            customerList = FXCollections.observableArrayList(all);
        } else {
            Customer sample = new Customer();
            sample.setId("C-101");
            sample.setName("Karim Ahmed");
            sample.setPhone("01712345678");
            sample.setEmail("karim@email.com");
            sample.setNid("1990123456789");
            sample.setLoyaltyPoints(100);
            customerList.add(sample);
        }
        customerTable.setItems(customerList);
    }

    @FXML
    private void onAddRow() {
        onRegister();
    }

    @FXML
    private void onUpdateRow() {
        Customer sel = customerTable.getSelectionModel().getSelectedItem();
        if (sel == null) {
            setStatus("Select a customer row in the table to update.");
            return;
        }
        if (nameField != null && !nameField.getText().isBlank()) sel.setName(nameField.getText().trim());
        if (phoneField != null && !phoneField.getText().isBlank()) sel.setPhone(phoneField.getText().trim());
        if (emailField != null && !emailField.getText().isBlank()) sel.setEmail(emailField.getText().trim());
        if (nidField != null && !nidField.getText().isBlank()) sel.setNid(nidField.getText().trim());
        customerTable.refresh();
        setStatus("Customer row updated.");
    }

    @FXML
    private void onDeleteRow() {
        Customer sel = customerTable.getSelectionModel().getSelectedItem();
        if (sel == null) {
            setStatus("Select a customer row in the table to delete.");
            return;
        }
        customerList.remove(sel);
        setStatus("Customer row deleted.");
    }

    @FXML
    private void onRegister() {
        if (nameField.getText().isBlank()) {
            setStatus("Name is required.");
            return;
        }
        if (!ValidationUtil.isValidPhone(phoneField.getText())) {
            setStatus("Invalid phone number format. (VL)");
            return;
        }
        if (!ValidationUtil.isValidEmail(emailField.getText())) {
            setStatus("Invalid email format. (VL)");
            return;
        }
        try {
            Customer c = store.registerCustomer(
                    nameField.getText(), phoneField.getText(),
                    emailField.getText(), nidField.getText());
            customerList.add(c);
            customerTable.getSelectionModel().select(c);
            setStatus(String.format("Customer registered! ID: %s | Loyalty activated with 100 points. (DP, OP)",
                    c.getId()));
            nameField.clear();
            phoneField.clear();
            emailField.clear();
            nidField.clear();
        } catch (Exception e) {
            setStatus("Registration failed: " + e.getMessage());
        }
    }
}
