package com.example.summer26.section1.group1.diamondworld.Turjo;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

public class SalesTargetController extends BaseController {

    @FXML private TableView<SalesTarget> targetTable;
    @FXML private TableColumn<SalesTarget, String> nameCol;
    @FXML private TableColumn<SalesTarget, String> idCol;
    @FXML private TableColumn<SalesTarget, Double> prevCol;
    @FXML private TableColumn<SalesTarget, Double> targetCol;

    private final DataStore store = DataStore.getInstance();

    @FXML
    private void initialize() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        prevCol.setCellValueFactory(new PropertyValueFactory<>("previousSales"));
        targetCol.setCellValueFactory(new PropertyValueFactory<>("targetAmount"));
        targetCol.setCellFactory(TextFieldTableCell.forTableColumn(new javafx.util.converter.DoubleStringConverter()));
        targetCol.setOnEditCommit(e -> e.getRowValue().setTargetAmount(e.getNewValue()));
        targetTable.setEditable(true);
        targetTable.setItems(FXCollections.observableArrayList(store.getSalesTargets()));
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
            store.updateSalesTargets(targetTable.getItems());
            setStatus(String.format("Targets saved. Total: BDT %,.0f / Quota: BDT %,.0f (DP, OP)", total, quota));
        } catch (Exception e) {
            setStatus("Save failed: " + e.getMessage());
        }
    }
}




