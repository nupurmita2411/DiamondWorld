package com.example.summer26.section1.group1.diamondworld.Turjo;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class CustomDesignApprovalController extends BaseController {

    @FXML private ComboBox<CustomDesignRequest> requestCombo;
    @FXML private TextField markupField;
    @FXML private TableView<CustomDesignRequest> detailsTable;
    @FXML private TableColumn<CustomDesignRequest, String> idCol;
    @FXML private TableColumn<CustomDesignRequest, String> customerCol;
    @FXML private TableColumn<CustomDesignRequest, String> metalCol;
    @FXML private TableColumn<CustomDesignRequest, Number> quoteCol;

    private final DataStore store = DataStore.getInstance();

    @FXML
    private void initialize() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        metalCol.setCellValueFactory(new PropertyValueFactory<>("metalType"));
        quoteCol.setCellValueFactory(cell ->
                new javafx.beans.property.SimpleObjectProperty<>(cell.getValue().getTotalQuote()));
        loadPending();
    }

    @FXML
    private void onLoadModule() {
        loadPending();
        setStatus("Pending custom order approval module loaded. (UIE, DP, OP)");
    }

    private void loadPending() {
        var pending = store.getPendingCustomDesigns();
        requestCombo.setItems(FXCollections.observableArrayList(pending));
        detailsTable.setItems(FXCollections.observableArrayList(pending));
        if (!pending.isEmpty()) {
            requestCombo.getSelectionModel().selectFirst();
        }
    }

    @FXML
    private void onLoadDetails() {
        CustomDesignRequest req = requestCombo.getValue();
        if (req == null) {
            setStatus("Select a pending request. (VL)");
            return;
        }
        if (!req.isFeasible()) {
            setStatus("Design failed feasibility assessment. (VR)");
            return;
        }
        markupField.setText(String.valueOf(req.getMarkupFactor()));
        setStatus(String.format("Design: %s | Metal: %.1fg | Gem: %.0f | Labor: %.0f (DP, OP, VR)",
                req.getDiamondCut(), req.getMetalWeight(), req.getGemstoneEstimate(), req.getLaborCost()));
    }

    @FXML
    private void onApprove() {
        CustomDesignRequest req = requestCombo.getValue();
        if (req == null) {
            setStatus("Select a request to approve.");
            return;
        }
        if (!ValidationUtil.isPositiveNumber(markupField.getText())) {
            setStatus("Invalid markup factor. (VL)");
            return;
        }
        try {
            double markup = Double.parseDouble(markupField.getText());
            store.approveCustomDesign(req.getId(), markup);
            setStatus("Approved! Quote: BDT " + String.format("%.2f", req.getTotalQuote())
                    + " | Saved to database. (UID, VR, DP, OP)");
            loadPending();
        } catch (Exception e) {
            setStatus("Save failed: " + e.getMessage());
        }
    }
}




