package com.example.summer26.section1.group1.diamondworld.Turjo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

public class RepairTrackController extends BaseController {

    @FXML private TextField jobCardField;
    @FXML private TextArea statusArea;

    @FXML private TableView<RepairJob> repairTable;
    @FXML private TableColumn<RepairJob, String> cardCol;
    @FXML private TableColumn<RepairJob, String> customerCol;
    @FXML private TableColumn<RepairJob, String> itemCol;
    @FXML private TableColumn<RepairJob, String> stageCol;

    @FXML private TextField cardInput;
    @FXML private TextField customerInput;
    @FXML private TextField itemInput;
    @FXML private TextField stageInput;

    private final DataStore store = DataStore.getInstance();
    private ObservableList<RepairJob> repairList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        if (repairTable != null) {
            repairTable.setEditable(true);

            cardCol.setCellValueFactory(new PropertyValueFactory<>("jobCardNo"));
            customerCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            itemCol.setCellValueFactory(new PropertyValueFactory<>("itemDescription"));
            stageCol.setCellValueFactory(new PropertyValueFactory<>("currentStage"));

            cardCol.setCellFactory(TextFieldTableCell.forTableColumn());
            cardCol.setOnEditCommit(e -> { if (e.getRowValue() != null) e.getRowValue().setJobCardNo(e.getNewValue()); });

            customerCol.setCellFactory(TextFieldTableCell.forTableColumn());
            customerCol.setOnEditCommit(e -> { if (e.getRowValue() != null) e.getRowValue().setCustomerName(e.getNewValue()); });

            itemCol.setCellFactory(TextFieldTableCell.forTableColumn());
            itemCol.setOnEditCommit(e -> { if (e.getRowValue() != null) e.getRowValue().setItemDescription(e.getNewValue()); });

            stageCol.setCellFactory(TextFieldTableCell.forTableColumn());
            stageCol.setOnEditCommit(e -> { if (e.getRowValue() != null) e.getRowValue().setCurrentStage(e.getNewValue()); });

            loadRepairJobs();

            repairTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
                if (newSel != null) {
                    if (jobCardField != null) jobCardField.setText(newSel.getJobCardNo());
                    if (cardInput != null) cardInput.setText(newSel.getJobCardNo());
                    if (customerInput != null) customerInput.setText(newSel.getCustomerName());
                    if (itemInput != null) itemInput.setText(newSel.getItemDescription());
                    if (stageInput != null) stageInput.setText(newSel.getCurrentStage());
                    showJobDetails(newSel);
                }
            });
        }
    }

    private void loadRepairJobs() {
        var existing = store.getAllRepairJobs();
        if (existing != null && !existing.isEmpty()) {
            repairList = FXCollections.observableArrayList(existing);
        } else {
            RepairJob r = new RepairJob();
            r.setJobCardNo("JC-2026-0088");
            r.setCustomerName("Anisur Rahman");
            r.setItemDescription("Solitaire Ring Claw Re-tipping");
            r.setCurrentStage("POLISHING & QUALITY CHECK");
            r.setLastUpdated("2026-08-10 14:30");
            repairList.add(r);
        }
        repairTable.setItems(repairList);
    }

    private void showJobDetails(RepairJob r) {
        if (statusArea != null && r != null) {
            statusArea.setText(String.format("""
                    Job Card: %s
                    Customer: %s
                    Item: %s
                    Current Stage: %s
                    Last Updated: %s
                    """, r.getJobCardNo(), r.getCustomerName(),
                    r.getItemDescription(), r.getCurrentStage(), r.getLastUpdated()));
        }
    }

    @FXML
    private void onTrack() {
        if (jobCardField.getText().isBlank()) {
            setStatus("Enter job card number. (UID)");
            return;
        }
        var job = store.findRepairJob(jobCardField.getText());
        if (job.isEmpty()) {
            setStatus("Job card not found. (VR)");
            statusArea.clear();
            return;
        }
        RepairJob r = job.get();
        showJobDetails(r);
        repairTable.getSelectionModel().select(r);
        setStatus("Repair status displayed. (VR, DP, OP)");
    }

    @FXML
    private void onAddRow() {
        String card = cardInput != null ? cardInput.getText().trim() : "";
        String customer = customerInput != null ? customerInput.getText().trim() : "";
        String item = itemInput != null ? itemInput.getText().trim() : "";
        String stage = stageInput != null ? stageInput.getText().trim() : "";

        if (card.isEmpty() || customer.isEmpty()) {
            setStatus("Job Card No and Customer Name are required.");
            return;
        }

        RepairJob r = new RepairJob();
        r.setJobCardNo(card);
        r.setCustomerName(customer);
        r.setItemDescription(item.isEmpty() ? "Jewellery Repair" : item);
        r.setCurrentStage(stage.isEmpty() ? "RECEIVED AT WORKSHOP" : stage);
        r.setLastUpdated("2026-08-10 17:00");

        repairList.add(r);
        repairTable.getSelectionModel().select(r);
        setStatus("New repair job row added.");
    }

    @FXML
    private void onUpdateRow() {
        RepairJob sel = repairTable.getSelectionModel().getSelectedItem();
        if (sel == null) {
            setStatus("Select a repair job row in the table to update.");
            return;
        }
        if (cardInput != null && !cardInput.getText().isBlank()) sel.setJobCardNo(cardInput.getText().trim());
        if (customerInput != null && !customerInput.getText().isBlank()) sel.setCustomerName(customerInput.getText().trim());
        if (itemInput != null && !itemInput.getText().isBlank()) sel.setItemDescription(itemInput.getText().trim());
        if (stageInput != null && !stageInput.getText().isBlank()) sel.setCurrentStage(stageInput.getText().trim());
        repairTable.refresh();
        setStatus("Repair job row updated.");
    }

    @FXML
    private void onDeleteRow() {
        RepairJob sel = repairTable.getSelectionModel().getSelectedItem();
        if (sel == null) {
            setStatus("Select a repair job row in the table to delete.");
            return;
        }
        repairList.remove(sel);
        setStatus("Repair job row deleted.");
    }
}
