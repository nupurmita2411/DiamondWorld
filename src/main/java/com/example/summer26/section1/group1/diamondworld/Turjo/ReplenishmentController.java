package com.example.summer26.section1.group1.diamondworld.Turjo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;

public class ReplenishmentController extends BaseController {

    @FXML private TableView<StockReplenishmentRequest> alertTable;
    @FXML private TableColumn<StockReplenishmentRequest, String> idCol;
    @FXML private TableColumn<StockReplenishmentRequest, String> typeCol;
    @FXML private TableColumn<StockReplenishmentRequest, Integer> stockCol;
    @FXML private TableColumn<StockReplenishmentRequest, Integer> reqQtyCol;

    @FXML private ComboBox<StockReplenishmentRequest> requestCombo;
    @FXML private TextField authCodeField;

    @FXML private TextField idInput;
    @FXML private TextField typeInput;
    @FXML private TextField stockInput;
    @FXML private TextField reqQtyInput;

    private final DataStore store = DataStore.getInstance();
    private ObservableList<StockReplenishmentRequest> alertList;

    @FXML
    private void initialize() {
        alertTable.setEditable(true);

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("itemType"));
        stockCol.setCellValueFactory(new PropertyValueFactory<>("currentStock"));
        if (reqQtyCol != null) reqQtyCol.setCellValueFactory(new PropertyValueFactory<>("requestedQty"));

        idCol.setCellFactory(TextFieldTableCell.forTableColumn());
        idCol.setOnEditCommit(e -> { if (e.getRowValue() != null) e.getRowValue().setId(e.getNewValue()); });

        typeCol.setCellFactory(TextFieldTableCell.forTableColumn());
        typeCol.setOnEditCommit(e -> { if (e.getRowValue() != null) e.getRowValue().setItemType(e.getNewValue()); });

        stockCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        stockCol.setOnEditCommit(e -> { if (e.getRowValue() != null && e.getNewValue() != null) e.getRowValue().setCurrentStock(e.getNewValue()); });

        if (reqQtyCol != null) {
            reqQtyCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
            reqQtyCol.setOnEditCommit(e -> { if (e.getRowValue() != null && e.getNewValue() != null) e.getRowValue().setRequestedQty(e.getNewValue()); });
        }

        loadAlerts();

        alertTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                if (idInput != null) idInput.setText(newSel.getId());
                if (typeInput != null) typeInput.setText(newSel.getItemType());
                if (stockInput != null) stockInput.setText(String.valueOf(newSel.getCurrentStock()));
                if (reqQtyInput != null) reqQtyInput.setText(String.valueOf(newSel.getRequestedQty()));
                if (requestCombo != null) requestCombo.getSelectionModel().select(newSel);
            }
        });
    }

    @FXML
    private void onOpenConsole() {
        loadAlerts();
        setStatus("Low-stock alerts loaded. (UIE, DP, OP)");
    }

    private void loadAlerts() {
        var pending = store.getPendingReplenishments();
        alertList = FXCollections.observableArrayList(pending);
        if (alertTable != null) alertTable.setItems(alertList);
        if (requestCombo != null) requestCombo.setItems(alertList);
    }

    @FXML
    private void onAddRow() {
        String id = idInput != null ? idInput.getText().trim() : "";
        String type = typeInput != null ? typeInput.getText().trim() : "";
        int stock = 2;
        int reqQty = 10;
        try {
            if (stockInput != null && !stockInput.getText().isBlank()) stock = Integer.parseInt(stockInput.getText().trim());
            if (reqQtyInput != null && !reqQtyInput.getText().isBlank()) reqQty = Integer.parseInt(reqQtyInput.getText().trim());
        } catch (NumberFormatException e) {
            setStatus("Enter valid integers for stock and requested qty.");
            return;
        }
        if (id.isEmpty() || type.isEmpty()) {
            setStatus("ID and Item Type are required.");
            return;
        }

        StockReplenishmentRequest req = new StockReplenishmentRequest();
        req.setId(id);
        req.setItemType(type);
        req.setCurrentStock(stock);
        req.setRequestedQty(reqQty);
        req.setStatus("PENDING");

        alertList.add(req);
        alertTable.getSelectionModel().select(req);
        setStatus("New replenishment row added.");
    }

    @FXML
    private void onUpdateRow() {
        StockReplenishmentRequest sel = alertTable.getSelectionModel().getSelectedItem();
        if (sel == null) {
            setStatus("Select a row in the table to update.");
            return;
        }
        if (idInput != null && !idInput.getText().isBlank()) sel.setId(idInput.getText().trim());
        if (typeInput != null && !typeInput.getText().isBlank()) sel.setItemType(typeInput.getText().trim());
        if (stockInput != null && !stockInput.getText().isBlank()) {
            try { sel.setCurrentStock(Integer.parseInt(stockInput.getText().trim())); } catch (NumberFormatException ignored) {}
        }
        if (reqQtyInput != null && !reqQtyInput.getText().isBlank()) {
            try { sel.setRequestedQty(Integer.parseInt(reqQtyInput.getText().trim())); } catch (NumberFormatException ignored) {}
        }
        alertTable.refresh();
        setStatus("Row updated.");
    }

    @FXML
    private void onDeleteRow() {
        StockReplenishmentRequest sel = alertTable.getSelectionModel().getSelectedItem();
        if (sel == null) {
            setStatus("Select a row in the table to delete.");
            return;
        }
        alertList.remove(sel);
        setStatus("Row deleted.");
    }

    @FXML
    private void onAuthorize() {
        StockReplenishmentRequest req = requestCombo.getValue();
        if (req == null) {
            setStatus("Select a replenishment request.");
            return;
        }
        if (!store.verifyReplenishmentDemand(req)) {
            setStatus("Request volume exceeds demand threshold. (VR)");
            return;
        }
        if (!ValidationUtil.isValidAuthCode(authCodeField.getText())) {
            setStatus("Invalid authorization code. Use format AUTH1234. (VL)");
            return;
        }
        try {
            store.authorizeReplenishment(req.getId());
            setStatus("Order authorized and sent to central warehouse. (UID, DP, OP)");
            loadAlerts();
        } catch (Exception e) {
            setStatus("Failed: " + e.getMessage());
        }
    }
}
