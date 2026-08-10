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

public class CustomOrderController extends BaseController {

    @FXML private TextField customerField;
    @FXML private ComboBox<String> metalCombo;
    @FXML private TextField ringSizeField;
    @FXML private ComboBox<String> cutCombo;
    @FXML private TextField depositField;

    @FXML private TableView<CustomDesignRequest> orderTable;
    @FXML private TableColumn<CustomDesignRequest, String> idCol;
    @FXML private TableColumn<CustomDesignRequest, String> customerCol;
    @FXML private TableColumn<CustomDesignRequest, String> metalCol;
    @FXML private TableColumn<CustomDesignRequest, String> cutCol;
    @FXML private TableColumn<CustomDesignRequest, String> sizeCol;

    private final DataStore store = DataStore.getInstance();
    private ObservableList<CustomDesignRequest> orderList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        if (metalCombo != null) {
            metalCombo.getItems().addAll("18K Yellow Gold", "18K White Gold", "22K Gold", "Platinum");
        }
        if (cutCombo != null) {
            cutCombo.getItems().addAll("Round", "Princess", "Emerald", "Oval", "Cushion");
        }

        if (orderTable != null) {
            orderTable.setEditable(true);

            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            customerCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            metalCol.setCellValueFactory(new PropertyValueFactory<>("metalType"));
            cutCol.setCellValueFactory(new PropertyValueFactory<>("diamondCut"));
            sizeCol.setCellValueFactory(new PropertyValueFactory<>("ringSize"));

            idCol.setCellFactory(TextFieldTableCell.forTableColumn());
            idCol.setOnEditCommit(e -> { if (e.getRowValue() != null) e.getRowValue().setId(e.getNewValue()); });

            customerCol.setCellFactory(TextFieldTableCell.forTableColumn());
            customerCol.setOnEditCommit(e -> { if (e.getRowValue() != null) e.getRowValue().setCustomerName(e.getNewValue()); });

            metalCol.setCellFactory(TextFieldTableCell.forTableColumn());
            metalCol.setOnEditCommit(e -> { if (e.getRowValue() != null) e.getRowValue().setMetalType(e.getNewValue()); });

            cutCol.setCellFactory(TextFieldTableCell.forTableColumn());
            cutCol.setOnEditCommit(e -> { if (e.getRowValue() != null) e.getRowValue().setDiamondCut(e.getNewValue()); });

            sizeCol.setCellFactory(TextFieldTableCell.forTableColumn());
            sizeCol.setOnEditCommit(e -> { if (e.getRowValue() != null) e.getRowValue().setRingSize(e.getNewValue()); });

            loadOrders();

            orderTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
                if (newSel != null) {
                    if (customerField != null) customerField.setText(newSel.getCustomerName());
                    if (ringSizeField != null) ringSizeField.setText(newSel.getRingSize());
                    if (metalCombo != null) metalCombo.setValue(newSel.getMetalType());
                    if (cutCombo != null) cutCombo.setValue(newSel.getDiamondCut());
                }
            });
        }
    }

    private void loadOrders() {
        var pending = store.getPendingCustomDesigns();
        if (pending != null && !pending.isEmpty()) {
            orderList = FXCollections.observableArrayList(pending);
        } else {
            CustomDesignRequest r = new CustomDesignRequest();
            r.setId("CR-201");
            r.setCustomerName("Sadia Rahman");
            r.setMetalType("18K White Gold");
            r.setDiamondCut("Round");
            r.setRingSize("7.5");
            orderList.add(r);
        }
        orderTable.setItems(orderList);
    }

    @FXML
    private void onCalculateDeposit() {
        if (!ValidationUtil.isValidRingSize(ringSizeField.getText())) {
            setStatus("Ring size must be between 3 and 15. (VR)");
            return;
        }
        double deposit = 75000 * 0.4;
        depositField.setText(String.format("%.0f", deposit));
        setStatus("Minimum advance deposit calculated: BDT " + depositField.getText() + " (DP, OP)");
    }

    @FXML
    private void onAddRow() {
        onSubmit();
    }

    @FXML
    private void onUpdateRow() {
        CustomDesignRequest sel = orderTable.getSelectionModel().getSelectedItem();
        if (sel == null) {
            setStatus("Select an order row in the table to update.");
            return;
        }
        if (customerField != null && !customerField.getText().isBlank()) sel.setCustomerName(customerField.getText().trim());
        if (metalCombo != null && metalCombo.getValue() != null) sel.setMetalType(metalCombo.getValue());
        if (cutCombo != null && cutCombo.getValue() != null) sel.setDiamondCut(cutCombo.getValue());
        if (ringSizeField != null && !ringSizeField.getText().isBlank()) sel.setRingSize(ringSizeField.getText().trim());
        orderTable.refresh();
        setStatus("Order row updated.");
    }

    @FXML
    private void onDeleteRow() {
        CustomDesignRequest sel = orderTable.getSelectionModel().getSelectedItem();
        if (sel == null) {
            setStatus("Select an order row in the table to delete.");
            return;
        }
        orderList.remove(sel);
        setStatus("Order row deleted.");
    }

    @FXML
    private void onSubmit() {
        if (customerField.getText().isBlank() || metalCombo.getValue() == null || cutCombo.getValue() == null) {
            setStatus("Fill all required fields.");
            return;
        }
        if (!ValidationUtil.isValidRingSize(ringSizeField.getText())) {
            setStatus("Invalid ring size. (VR)");
            return;
        }
        try {
            CustomDesignRequest req = store.createCustomOrder(
                    metalCombo.getValue(), ringSizeField.getText(),
                    cutCombo.getValue(), customerField.getText(),
                    Double.parseDouble(depositField.getText().isBlank() ? "30000" : depositField.getText()));
            orderList.add(req);
            orderTable.getSelectionModel().select(req);
            setStatus(String.format("Order %s filed. Manufacturing ticket issued. (UID, DP, OP)", req.getId()));
        } catch (Exception e) {
            setStatus("Submit failed: " + e.getMessage());
        }
    }
}
