package com.example.summer26.section1.group1.diamondworld.Turjo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DoubleStringConverter;

import java.util.ArrayList;

public class SalesTargetController extends BaseController {

    @FXML private TableView<SalesTarget> targetTable;
    @FXML private TableColumn<SalesTarget, String> idCol;
    @FXML private TableColumn<SalesTarget, String> nameCol;
    @FXML private TableColumn<SalesTarget, Double> prevCol;
    @FXML private TableColumn<SalesTarget, Double> targetCol;

    @FXML private TextField idInput;
    @FXML private TextField nameInput;
    @FXML private TextField prevInput;
    @FXML private TextField targetInput;

    private final DataStore store = DataStore.getInstance();
    private ObservableList<SalesTarget> targetList;

    @FXML
    private void initialize() {
        targetTable.setEditable(true);

        // Value factories
        idCol.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        prevCol.setCellValueFactory(new PropertyValueFactory<>("previousSales"));
        targetCol.setCellValueFactory(new PropertyValueFactory<>("targetAmount"));

        // Enable column cell editing on double click for ALL columns
        idCol.setCellFactory(TextFieldTableCell.forTableColumn());
        idCol.setOnEditCommit(e -> {
            if (e.getRowValue() != null) {
                e.getRowValue().setEmployeeId(e.getNewValue());
            }
        });

        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        nameCol.setOnEditCommit(e -> {
            if (e.getRowValue() != null) {
                e.getRowValue().setEmployeeName(e.getNewValue());
            }
        });

        prevCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        prevCol.setOnEditCommit(e -> {
            if (e.getRowValue() != null) {
                Double val = e.getNewValue();
                e.getRowValue().setPreviousSales(val != null ? val : 0.0);
            }
        });

        targetCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        targetCol.setOnEditCommit(e -> {
            if (e.getRowValue() != null) {
                Double val = e.getNewValue();
                e.getRowValue().setTargetAmount(val != null ? val : 0.0);
            }
        });

        // Load items into observable list
        targetList = FXCollections.observableArrayList(store.getSalesTargets());
        targetTable.setItems(targetList);

        // Listen for table row selection to populate edit form fields
        targetTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                if (idInput != null) idInput.setText(newSelection.getEmployeeId() != null ? newSelection.getEmployeeId() : "");
                if (nameInput != null) nameInput.setText(newSelection.getEmployeeName() != null ? newSelection.getEmployeeName() : "");
                if (prevInput != null) prevInput.setText(String.valueOf(newSelection.getPreviousSales()));
                if (targetInput != null) targetInput.setText(String.valueOf(newSelection.getTargetAmount()));
            }
        });
    }

    @FXML
    private void onAddRow() {
        String id = idInput != null ? idInput.getText().trim() : "";
        String name = nameInput != null ? nameInput.getText().trim() : "";
        double prev = 0.0;
        double target = 0.0;

        try {
            if (prevInput != null && !prevInput.getText().isBlank()) {
                prev = Double.parseDouble(prevInput.getText().trim());
            }
            if (targetInput != null && !targetInput.getText().isBlank()) {
                target = Double.parseDouble(targetInput.getText().trim());
            }
        } catch (NumberFormatException e) {
            setStatus("Please enter valid numeric values for Prev Sales and Target Goal.");
            return;
        }

        if (id.isEmpty() || name.isEmpty()) {
            setStatus("Employee ID and Name are required to add a new row.");
            return;
        }

        SalesTarget newTarget = new SalesTarget();
        newTarget.setEmployeeId(id);
        newTarget.setEmployeeName(name);
        newTarget.setPreviousSales(prev);
        newTarget.setTargetAmount(target);

        targetList.add(newTarget);
        targetTable.getSelectionModel().select(newTarget);
        setStatus("New row added. You can also double-click any cell to edit directly.");
    }

    @FXML
    private void onUpdateRow() {
        SalesTarget selected = targetTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            setStatus("Select a row in the table to update.");
            return;
        }

        String id = idInput != null ? idInput.getText().trim() : "";
        String name = nameInput != null ? nameInput.getText().trim() : "";
        double prev = 0.0;
        double target = 0.0;

        try {
            if (prevInput != null && !prevInput.getText().isBlank()) {
                prev = Double.parseDouble(prevInput.getText().trim());
            }
            if (targetInput != null && !targetInput.getText().isBlank()) {
                target = Double.parseDouble(targetInput.getText().trim());
            }
        } catch (NumberFormatException e) {
            setStatus("Please enter valid numeric values for Prev Sales and Target Goal.");
            return;
        }

        selected.setEmployeeId(id);
        selected.setEmployeeName(name);
        selected.setPreviousSales(prev);
        selected.setTargetAmount(target);

        targetTable.refresh();
        setStatus("Selected row updated successfully.");
    }

    @FXML
    private void onDeleteRow() {
        SalesTarget selected = targetTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            setStatus("Select a row in the table to delete.");
            return;
        }

        targetList.remove(selected);
        clearInputs();
        setStatus("Selected row deleted.");
    }

    private void clearInputs() {
        if (idInput != null) idInput.clear();
        if (nameInput != null) nameInput.clear();
        if (prevInput != null) prevInput.clear();
        if (targetInput != null) targetInput.clear();
    }

    @FXML
    private void onSave() {
        double total = targetTable.getItems().stream()
                .mapToDouble(SalesTarget::getTargetAmount).sum();
        double quota = store.getBranchQuota();
        if (total > quota * 1.1) {
            setStatus(String.format("Total targets (%.0f) exceed branch quota (%.0f). (VR)", total, quota));
            return;
        }
        try {
            store.updateSalesTargets(new ArrayList<>(targetTable.getItems()));
            setStatus(String.format("Targets saved successfully. Total: BDT %,.0f / Quota: BDT %,.0f (DP, OP)", total, quota));
        } catch (Exception e) {
            setStatus("Save failed: " + e.getMessage());
        }
    }
}
