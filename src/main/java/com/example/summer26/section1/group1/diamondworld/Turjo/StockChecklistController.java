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

public class StockChecklistController extends BaseController {

    @FXML private TextField caseNameField;
    @FXML private TextField physicalCountField;

    @FXML private TableView<StockChecklistEntry> checklistTable;
    @FXML private TableColumn<StockChecklistEntry, String> caseCol;
    @FXML private TableColumn<StockChecklistEntry, Integer> expectedCol;
    @FXML private TableColumn<StockChecklistEntry, Integer> physicalCol;
    @FXML private TableColumn<StockChecklistEntry, Integer> diffCol;
    @FXML private TableColumn<StockChecklistEntry, String> dateCol;

    private final DataStore store = DataStore.getInstance();
    private ObservableList<StockChecklistEntry> checklistList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        if (checklistTable != null) {
            checklistTable.setEditable(true);

            caseCol.setCellValueFactory(new PropertyValueFactory<>("caseName"));
            expectedCol.setCellValueFactory(new PropertyValueFactory<>("expectedCount"));
            physicalCol.setCellValueFactory(new PropertyValueFactory<>("physicalCount"));
            diffCol.setCellValueFactory(new PropertyValueFactory<>("discrepancy"));
            dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

            caseCol.setCellFactory(TextFieldTableCell.forTableColumn());
            caseCol.setOnEditCommit(e -> { if (e.getRowValue() != null) e.getRowValue().setCaseName(e.getNewValue()); });

            expectedCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
            expectedCol.setOnEditCommit(e -> {
                if (e.getRowValue() != null && e.getNewValue() != null) {
                    e.getRowValue().setExpectedCount(e.getNewValue());
                    e.getRowValue().setDiscrepancy(e.getRowValue().getPhysicalCount() - e.getNewValue());
                    checklistTable.refresh();
                }
            });

            physicalCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
            physicalCol.setOnEditCommit(e -> {
                if (e.getRowValue() != null && e.getNewValue() != null) {
                    e.getRowValue().setPhysicalCount(e.getNewValue());
                    e.getRowValue().setDiscrepancy(e.getNewValue() - e.getRowValue().getExpectedCount());
                    checklistTable.refresh();
                }
            });

            diffCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
            diffCol.setOnEditCommit(e -> { if (e.getRowValue() != null && e.getNewValue() != null) e.getRowValue().setDiscrepancy(e.getNewValue()); });

            dateCol.setCellFactory(TextFieldTableCell.forTableColumn());
            dateCol.setOnEditCommit(e -> { if (e.getRowValue() != null) e.getRowValue().setDate(e.getNewValue()); });

            loadChecklist();

            checklistTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
                if (newSel != null) {
                    if (caseNameField != null) caseNameField.setText(newSel.getCaseName());
                    if (physicalCountField != null) physicalCountField.setText(String.valueOf(newSel.getPhysicalCount()));
                }
            });
        }
    }

    private void loadChecklist() {
        var existing = store.getAllChecklists();
        if (existing != null && !existing.isEmpty()) {
            checklistList = FXCollections.observableArrayList(existing);
        } else {
            StockChecklistEntry entry = new StockChecklistEntry();
            entry.setCaseName("Diamond Bangles Display Box C");
            entry.setExpectedCount(8);
            entry.setPhysicalCount(8);
            entry.setDiscrepancy(0);
            entry.setDate("2026-08-10");
            checklistList.add(entry);
        }
        checklistTable.setItems(checklistList);
    }

    @FXML
    private void onSubmit() {
        if (caseNameField.getText().isBlank()) {
            setStatus("Enter display case name. (UID)");
            return;
        }
        int physical;
        try {
            physical = Integer.parseInt(physicalCountField.getText());
        } catch (NumberFormatException e) {
            setStatus("Enter valid physical count. (VL)");
            return;
        }
        try {
            StockChecklistEntry entry = store.saveChecklist(caseNameField.getText(), physical);
            checklistList.add(entry);
            checklistTable.getSelectionModel().select(entry);
            int expected = entry.getExpectedCount();
            if (entry.getDiscrepancy() != 0) {
                setStatus(String.format("DISCREPANCY flagged! Expected: %d | Physical: %d | Diff: %d (VR, DP, OP)",
                        expected, physical, entry.getDiscrepancy()));
            } else {
                setStatus(String.format("Checklist balanced for %s. Saved to server. (VR, DP, OP)",
                        entry.getCaseName()));
            }
        } catch (Exception e) {
            setStatus("Save failed: " + e.getMessage());
        }
    }

    @FXML
    private void onAddRow() {
        onSubmit();
    }

    @FXML
    private void onUpdateRow() {
        StockChecklistEntry sel = checklistTable.getSelectionModel().getSelectedItem();
        if (sel == null) {
            setStatus("Select a checklist row in the table to update.");
            return;
        }
        if (caseNameField != null && !caseNameField.getText().isBlank()) sel.setCaseName(caseNameField.getText().trim());
        if (physicalCountField != null && !physicalCountField.getText().isBlank()) {
            try {
                int p = Integer.parseInt(physicalCountField.getText().trim());
                sel.setPhysicalCount(p);
                sel.setDiscrepancy(p - sel.getExpectedCount());
            } catch (NumberFormatException ignored) {}
        }
        checklistTable.refresh();
        setStatus("Checklist row updated.");
    }

    @FXML
    private void onDeleteRow() {
        StockChecklistEntry sel = checklistTable.getSelectionModel().getSelectedItem();
        if (sel == null) {
            setStatus("Select a checklist row in the table to delete.");
            return;
        }
        checklistList.remove(sel);
        setStatus("Checklist row deleted.");
    }
}
