package com.example.summer26.section1.group1.diamondworld.Turjo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

public class DisputeController extends BaseController {

    @FXML private TextField phoneField;
    @FXML private TextField invoiceField;
    @FXML private TextArea detailsArea;
    @FXML private ComboBox<String> resolutionCombo;

    @FXML private TableView<Dispute> disputeTable;
    @FXML private TableColumn<Dispute, String> idCol;
    @FXML private TableColumn<Dispute, String> custPhoneCol;
    @FXML private TableColumn<Dispute, String> invCol;
    @FXML private TableColumn<Dispute, String> statusCol;
    @FXML private TableColumn<Dispute, String> resCol;

    @FXML private TextField idInput;
    @FXML private TextField phoneInput;
    @FXML private TextField invInput;
    @FXML private TextField statusInput;

    private final DataStore store = DataStore.getInstance();
    private Dispute currentDispute;
    private ObservableList<Dispute> disputeList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        if (resolutionCombo != null) {
            resolutionCombo.getItems().addAll("Product Replacement", "Refund Balance Credit", "Repair & Compensation");
        }

        if (disputeTable != null) {
            disputeTable.setEditable(true);

            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            custPhoneCol.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
            invCol.setCellValueFactory(new PropertyValueFactory<>("invoiceId"));
            statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
            resCol.setCellValueFactory(new PropertyValueFactory<>("resolution"));

            idCol.setCellFactory(TextFieldTableCell.forTableColumn());
            idCol.setOnEditCommit(e -> { if (e.getRowValue() != null) e.getRowValue().setId(e.getNewValue()); });

            custPhoneCol.setCellFactory(TextFieldTableCell.forTableColumn());
            custPhoneCol.setOnEditCommit(e -> { if (e.getRowValue() != null) e.getRowValue().setCustomerPhone(e.getNewValue()); });

            invCol.setCellFactory(TextFieldTableCell.forTableColumn());
            invCol.setOnEditCommit(e -> { if (e.getRowValue() != null) e.getRowValue().setInvoiceId(e.getNewValue()); });

            statusCol.setCellFactory(TextFieldTableCell.forTableColumn());
            statusCol.setOnEditCommit(e -> { if (e.getRowValue() != null) e.getRowValue().setStatus(e.getNewValue()); });

            resCol.setCellFactory(TextFieldTableCell.forTableColumn());
            resCol.setOnEditCommit(e -> { if (e.getRowValue() != null) e.getRowValue().setResolution(e.getNewValue()); });

            loadDisputes();

            disputeTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
                if (newSel != null) {
                    currentDispute = newSel;
                    if (idInput != null) idInput.setText(newSel.getId());
                    if (phoneInput != null) phoneInput.setText(newSel.getCustomerPhone());
                    if (invInput != null) invInput.setText(newSel.getInvoiceId());
                    if (statusInput != null) statusInput.setText(newSel.getStatus());
                    showDisputeDetails(newSel);
                }
            });
        }
    }

    private void loadDisputes() {
        var existing = store.getAllDisputes();
        if (existing != null && !existing.isEmpty()) {
            disputeList = FXCollections.observableArrayList(existing);
        } else {
            Dispute sample = new Dispute();
            sample.setId("DSP-101");
            sample.setCustomerPhone("01712345678");
            sample.setInvoiceId("INV-2026-0142");
            sample.setPurchaseDate("2026-05-10");
            sample.setCertificateNo("GIA-998877");
            sample.setRepairTrail("Claw re-tipping needed");
            sample.setStatus("PENDING");
            disputeList.add(sample);
        }
        disputeTable.setItems(disputeList);
    }

    private void showDisputeDetails(Dispute d) {
        if (detailsArea != null && d != null) {
            detailsArea.setText(String.format("""
                    Dispute ID: %s
                    Invoice: %s | Date: %s
                    Certificate: %s
                    Repair Trail: %s
                    Status: %s
                    """,
                    d.getId(), d.getInvoiceId(),
                    d.getPurchaseDate(), d.getCertificateNo(),
                    d.getRepairTrail(), d.getStatus()));
        }
    }

    @FXML
    private void onSearch() {
        var dispute = store.findDispute(phoneField.getText(), invoiceField.getText());
        if (dispute.isEmpty()) {
            setStatus("No matching dispute found. (VR)");
            detailsArea.clear();
            currentDispute = null;
            return;
        }
        currentDispute = dispute.get();
        showDisputeDetails(currentDispute);
        disputeTable.getSelectionModel().select(currentDispute);
        setStatus("Dispute records verified. (UID, VR, OP)");
    }

    @FXML
    private void onAddRow() {
        String id = idInput != null ? idInput.getText().trim() : "";
        String phone = phoneInput != null ? phoneInput.getText().trim() : "";
        String inv = invInput != null ? invInput.getText().trim() : "";
        String status = statusInput != null ? statusInput.getText().trim() : "";

        if (id.isEmpty() || phone.isEmpty()) {
            setStatus("ID and Phone number are required.");
            return;
        }

        Dispute d = new Dispute();
        d.setId(id);
        d.setCustomerPhone(phone);
        d.setInvoiceId(inv.isEmpty() ? "INV-NEW" : inv);
        d.setStatus(status.isEmpty() ? "OPEN" : status);
        d.setPurchaseDate("2026-08-10");

        disputeList.add(d);
        disputeTable.getSelectionModel().select(d);
        setStatus("New dispute row added.");
    }

    @FXML
    private void onUpdateRow() {
        Dispute sel = disputeTable.getSelectionModel().getSelectedItem();
        if (sel == null) {
            setStatus("Select a row in the table to update.");
            return;
        }
        if (idInput != null && !idInput.getText().isBlank()) sel.setId(idInput.getText().trim());
        if (phoneInput != null && !phoneInput.getText().isBlank()) sel.setCustomerPhone(phoneInput.getText().trim());
        if (invInput != null && !invInput.getText().isBlank()) sel.setInvoiceId(invInput.getText().trim());
        if (statusInput != null && !statusInput.getText().isBlank()) sel.setStatus(statusInput.getText().trim());
        disputeTable.refresh();
        setStatus("Row updated.");
    }

    @FXML
    private void onDeleteRow() {
        Dispute sel = disputeTable.getSelectionModel().getSelectedItem();
        if (sel == null) {
            setStatus("Select a row in the table to delete.");
            return;
        }
        disputeList.remove(sel);
        setStatus("Row deleted.");
    }

    @FXML
    private void onResolve() {
        if (currentDispute == null) {
            setStatus("Search or select a dispute first.");
            return;
        }
        String resolution = resolutionCombo.getValue();
        if (resolution == null) {
            setStatus("Select a resolution method. (UIE)");
            return;
        }
        try {
            store.resolveDispute(currentDispute.getId(), resolution);
            currentDispute.setStatus("RESOLVED");
            currentDispute.setResolution(resolution);
            disputeTable.refresh();
            setStatus("Dispute resolved. Adjustment log generated. (UIE, DP, OP)");
        } catch (Exception e) {
            setStatus("Failed: " + e.getMessage());
        }
    }
}
