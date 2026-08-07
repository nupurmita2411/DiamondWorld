package com.example.summer26.section1.group1.diamondworld.Turjo;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class VaultAuditController extends BaseController {

    @FXML private TextField rfidField;
    @FXML private TableView<VaultItem> discrepancyTable;
    @FXML private TableColumn<VaultItem, String> tagCol;
    @FXML private TableColumn<VaultItem, String> nameCol;
    @FXML private TableColumn<VaultItem, Boolean> vaultCol;

    private final DataStore store = DataStore.getInstance();

    @FXML
    private void initialize() {
        tagCol.setCellValueFactory(new PropertyValueFactory<>("rfidTag"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        vaultCol.setCellValueFactory(new PropertyValueFactory<>("inVault"));
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
        discrepancyTable.setItems(FXCollections.observableArrayList(discrepancies));
        setStatus(String.format("Audit complete. %d discrepancies found. (VR, DP, OP)", discrepancies.size()));
    }

    @FXML
    private void onCommit() {
        setStatus("Physical audit summary committed to compliance database. (DP)");
    }
}




