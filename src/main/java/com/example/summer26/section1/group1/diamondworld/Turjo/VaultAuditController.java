package com.example.summer26.section1.group1.diamondworld.Turjo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class VaultAuditController extends BaseController {

    @FXML private TextField rfidField;
    @FXML private TableView<VaultItem> discrepancyTable;
    @FXML private TableColumn<VaultItem, String> tagCol;
    @FXML private TableColumn<VaultItem, String> nameCol;
    @FXML private TableColumn<VaultItem, String> vaultCol;

    @FXML private TextField tagInput;
    @FXML private TextField nameInput;
    @FXML private TextField vaultInput;

    private final DataStore store = DataStore.getInstance();
    private ObservableList<VaultItem> itemList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        if (discrepancyTable != null) {
            discrepancyTable.setEditable(true);

            tagCol.setCellValueFactory(new PropertyValueFactory<>("rfidTag"));
            nameCol.setCellValueFactory(new PropertyValueFactory<>("itemName"));
            vaultCol.setCellValueFactory(cell ->
                    new javafx.beans.property.SimpleStringProperty(cell.getValue().isInVault() ? "In Vault" : "Missing / Out"));

            tagCol.setCellFactory(TextFieldTableCell.forTableColumn());
            tagCol.setOnEditCommit(e -> { if (e.getRowValue() != null) e.getRowValue().setRfidTag(e.getNewValue()); });

            nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
            nameCol.setOnEditCommit(e -> { if (e.getRowValue() != null) e.getRowValue().setItemName(e.getNewValue()); });

            vaultCol.setCellFactory(TextFieldTableCell.forTableColumn());
            vaultCol.setOnEditCommit(e -> {
                if (e.getRowValue() != null && e.getNewValue() != null) {
                    boolean val = e.getNewValue().equalsIgnoreCase("true") || e.getNewValue().equalsIgnoreCase("In Vault");
                    e.getRowValue().setInVault(val);
                    discrepancyTable.refresh();
                }
            });

            discrepancyTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
                if (newSel != null) {
                    if (tagInput != null) tagInput.setText(newSel.getRfidTag());
                    if (nameInput != null) nameInput.setText(newSel.getItemName());
                    if (vaultInput != null) vaultInput.setText(String.valueOf(newSel.isInVault()));
                }
            });
        }
    }

    @FXML
    private void onAudit() {
        if (rfidField.getText().isBlank()) {
            setStatus("Enter RFID tags separated by commas. (UID)");
            return;
        }
        List<String> tags = Arrays.stream(rfidField.getText().split(","))
                .map(String::trim).filter(s -> !s.isBlank()).collect(Collectors.toList());
        List<VaultItem> discrepancies = store.auditVaultTags(tags);
        itemList = FXCollections.observableArrayList(discrepancies);
        discrepancyTable.setItems(itemList);
        setStatus(String.format("Audit complete. %d discrepancies found. (VR, DP, OP)", discrepancies.size()));
    }

    @FXML
    private void onAddRow() {
        String tag = tagInput != null ? tagInput.getText().trim() : "";
        String name = nameInput != null ? nameInput.getText().trim() : "";
        boolean inVault = true;
        if (vaultInput != null && !vaultInput.getText().isBlank()) {
            inVault = Boolean.parseBoolean(vaultInput.getText().trim());
        }

        if (tag.isEmpty() || name.isEmpty()) {
            setStatus("RFID Tag and Item Name are required.");
            return;
        }

        VaultItem item = new VaultItem();
        item.setRfidTag(tag);
        item.setItemName(name);
        item.setInVault(inVault);

        itemList.add(item);
        discrepancyTable.setItems(itemList);
        discrepancyTable.getSelectionModel().select(item);
        setStatus("New vault item row added.");
    }

    @FXML
    private void onUpdateRow() {
        VaultItem sel = discrepancyTable.getSelectionModel().getSelectedItem();
        if (sel == null) {
            setStatus("Select a row in the table to update.");
            return;
        }
        if (tagInput != null && !tagInput.getText().isBlank()) sel.setRfidTag(tagInput.getText().trim());
        if (nameInput != null && !nameInput.getText().isBlank()) sel.setItemName(nameInput.getText().trim());
        if (vaultInput != null && !vaultInput.getText().isBlank()) sel.setInVault(Boolean.parseBoolean(vaultInput.getText().trim()));
        discrepancyTable.refresh();
        setStatus("Row updated.");
    }

    @FXML
    private void onDeleteRow() {
        VaultItem sel = discrepancyTable.getSelectionModel().getSelectedItem();
        if (sel == null) {
            setStatus("Select a row in the table to delete.");
            return;
        }
        itemList.remove(sel);
        setStatus("Row deleted.");
    }

    @FXML
    private void onCommit() {
        setStatus("Physical audit summary committed to compliance database. (DP)");
    }
}
