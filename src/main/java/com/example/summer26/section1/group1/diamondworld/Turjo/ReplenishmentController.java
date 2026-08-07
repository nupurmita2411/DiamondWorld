package com.example.summer26.section1.group1.diamondworld.Turjo;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ReplenishmentController extends BaseController {

    @FXML private TableView<StockReplenishmentRequest> alertTable;
    @FXML private TableColumn<StockReplenishmentRequest, String> idCol;
    @FXML private TableColumn<StockReplenishmentRequest, String> typeCol;
    @FXML private TableColumn<StockReplenishmentRequest, Number> stockCol;
    @FXML private ComboBox<StockReplenishmentRequest> requestCombo;
    @FXML private TextField authCodeField;

    private final DataStore store = DataStore.getInstance();

    @FXML
    private void initialize() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("itemType"));
        stockCol.setCellValueFactory(new PropertyValueFactory<>("currentStock"));
        loadAlerts();
    }

    @FXML
    private void onOpenConsole() {
        loadAlerts();
        setStatus("Low-stock alerts loaded. (UIE, DP, OP)");
    }

    private void loadAlerts() {
        var pending = store.getPendingReplenishments();
        alertTable.setItems(FXCollections.observableArrayList(pending));
        requestCombo.setItems(FXCollections.observableArrayList(pending));
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




