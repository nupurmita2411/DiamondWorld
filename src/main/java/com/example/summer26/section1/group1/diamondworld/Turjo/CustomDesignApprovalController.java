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
import javafx.util.converter.DoubleStringConverter;

public class CustomDesignApprovalController extends BaseController {

    @FXML private ComboBox<CustomDesignRequest> requestCombo;
    @FXML private TextField markupField;
    @FXML private TableView<CustomDesignRequest> detailsTable;
    @FXML private TableColumn<CustomDesignRequest, String> idCol;
    @FXML private TableColumn<CustomDesignRequest, String> customerCol;
    @FXML private TableColumn<CustomDesignRequest, String> metalCol;
    @FXML private TableColumn<CustomDesignRequest, Double> laborCol;
    @FXML private TableColumn<CustomDesignRequest, Double> quoteCol;

    @FXML private TextField idInput;
    @FXML private TextField customerInput;
    @FXML private TextField metalInput;
    @FXML private TextField laborInput;

    private final DataStore store = DataStore.getInstance();
    private ObservableList<CustomDesignRequest> requestList;

    @FXML
    private void initialize() {
        detailsTable.setEditable(true);

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        metalCol.setCellValueFactory(new PropertyValueFactory<>("metalType"));
        laborCol.setCellValueFactory(new PropertyValueFactory<>("laborCost"));
        quoteCol.setCellValueFactory(cell ->
                new javafx.beans.property.SimpleObjectProperty<>(cell.getValue().getTotalQuote()));

        idCol.setCellFactory(TextFieldTableCell.forTableColumn());
        idCol.setOnEditCommit(e -> { if (e.getRowValue() != null) e.getRowValue().setId(e.getNewValue()); });

        customerCol.setCellFactory(TextFieldTableCell.forTableColumn());
        customerCol.setOnEditCommit(e -> { if (e.getRowValue() != null) e.getRowValue().setCustomerName(e.getNewValue()); });

        metalCol.setCellFactory(TextFieldTableCell.forTableColumn());
        metalCol.setOnEditCommit(e -> { if (e.getRowValue() != null) e.getRowValue().setMetalType(e.getNewValue()); });

        laborCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        laborCol.setOnEditCommit(e -> { if (e.getRowValue() != null && e.getNewValue() != null) e.getRowValue().setLaborCost(e.getNewValue()); });

        loadPending();

        detailsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                if (idInput != null) idInput.setText(newSel.getId());
                if (customerInput != null) customerInput.setText(newSel.getCustomerName());
                if (metalInput != null) metalInput.setText(newSel.getMetalType());
                if (laborInput != null) laborInput.setText(String.valueOf(newSel.getLaborCost()));
                if (requestCombo != null) requestCombo.getSelectionModel().select(newSel);
            }
        });
    }

    @FXML
    private void onLoadModule() {
        loadPending();
        setStatus("Pending custom order approval module loaded. (UIE, DP, OP)");
    }

    private void loadPending() {
        var pending = store.getPendingCustomDesigns();
        requestList = FXCollections.observableArrayList(pending);
        if (requestCombo != null) requestCombo.setItems(requestList);
        if (detailsTable != null) detailsTable.setItems(requestList);
        if (!pending.isEmpty() && requestCombo != null) {
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
    private void onAddRow() {
        String id = idInput != null ? idInput.getText().trim() : "";
        String customer = customerInput != null ? customerInput.getText().trim() : "";
        String metal = metalInput != null ? metalInput.getText().trim() : "";
        double labor = 5000.0;
        try {
            if (laborInput != null && !laborInput.getText().isBlank()) labor = Double.parseDouble(laborInput.getText().trim());
        } catch (NumberFormatException e) {
            setStatus("Enter valid numeric labor cost.");
            return;
        }
        if (id.isEmpty() || customer.isEmpty()) {
            setStatus("ID and Customer Name are required.");
            return;
        }
        CustomDesignRequest req = new CustomDesignRequest();
        req.setId(id);
        req.setCustomerName(customer);
        req.setMetalType(metal.isEmpty() ? "22K Gold" : metal);
        req.setLaborCost(labor);
        req.setMarkupFactor(1.25);
        req.setFeasible(true);
        req.setDiamondCut("Round");
        req.setMetalWeight(10.0);
        req.setGemstoneEstimate(15000.0);
        req.setStatus("PENDING");

        requestList.add(req);
        detailsTable.getSelectionModel().select(req);
        setStatus("New design request row added.");
    }

    @FXML
    private void onUpdateRow() {
        CustomDesignRequest sel = detailsTable.getSelectionModel().getSelectedItem();
        if (sel == null) {
            setStatus("Select a row in the table to update.");
            return;
        }
        if (idInput != null && !idInput.getText().isBlank()) sel.setId(idInput.getText().trim());
        if (customerInput != null && !customerInput.getText().isBlank()) sel.setCustomerName(customerInput.getText().trim());
        if (metalInput != null && !metalInput.getText().isBlank()) sel.setMetalType(metalInput.getText().trim());
        if (laborInput != null && !laborInput.getText().isBlank()) {
            try {
                sel.setLaborCost(Double.parseDouble(laborInput.getText().trim()));
            } catch (NumberFormatException ignored) {}
        }
        detailsTable.refresh();
        setStatus("Row updated successfully.");
    }

    @FXML
    private void onDeleteRow() {
        CustomDesignRequest sel = detailsTable.getSelectionModel().getSelectedItem();
        if (sel == null) {
            setStatus("Select a row in the table to delete.");
            return;
        }
        requestList.remove(sel);
        setStatus("Selected row deleted.");
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
