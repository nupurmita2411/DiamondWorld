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
import javafx.util.converter.DoubleStringConverter;

public class ExpenseApprovalController extends BaseController {

    @FXML private ComboBox<ExpenseInvoice> invoiceCombo;
    @FXML private TextArea detailsArea;
    @FXML private TextField authCodeField;

    @FXML private TableView<ExpenseInvoice> invoiceTable;
    @FXML private TableColumn<ExpenseInvoice, String> idCol;
    @FXML private TableColumn<ExpenseInvoice, String> vendorCol;
    @FXML private TableColumn<ExpenseInvoice, String> typeCol;
    @FXML private TableColumn<ExpenseInvoice, Double> amountCol;
    @FXML private TableColumn<ExpenseInvoice, String> termsCol;

    @FXML private TextField idInput;
    @FXML private TextField vendorInput;
    @FXML private TextField typeInput;
    @FXML private TextField amountInput;

    private final DataStore store = DataStore.getInstance();
    private ObservableList<ExpenseInvoice> expenseList;

    @FXML
    private void initialize() {
        if (invoiceCombo != null) {
            invoiceCombo.setOnAction(e -> showDetails());
        }

        if (invoiceTable != null) {
            invoiceTable.setEditable(true);

            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            vendorCol.setCellValueFactory(new PropertyValueFactory<>("vendor"));
            typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
            termsCol.setCellValueFactory(new PropertyValueFactory<>("serviceTerms"));

            idCol.setCellFactory(TextFieldTableCell.forTableColumn());
            idCol.setOnEditCommit(e -> { if (e.getRowValue() != null) e.getRowValue().setId(e.getNewValue()); });

            vendorCol.setCellFactory(TextFieldTableCell.forTableColumn());
            vendorCol.setOnEditCommit(e -> { if (e.getRowValue() != null) e.getRowValue().setVendor(e.getNewValue()); });

            typeCol.setCellFactory(TextFieldTableCell.forTableColumn());
            typeCol.setOnEditCommit(e -> { if (e.getRowValue() != null) e.getRowValue().setType(e.getNewValue()); });

            amountCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
            amountCol.setOnEditCommit(e -> { if (e.getRowValue() != null && e.getNewValue() != null) e.getRowValue().setAmount(e.getNewValue()); });

            termsCol.setCellFactory(TextFieldTableCell.forTableColumn());
            termsCol.setOnEditCommit(e -> { if (e.getRowValue() != null) e.getRowValue().setServiceTerms(e.getNewValue()); });

            onOpenQueue();

            invoiceTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
                if (newSel != null) {
                    if (idInput != null) idInput.setText(newSel.getId());
                    if (vendorInput != null) vendorInput.setText(newSel.getVendor());
                    if (typeInput != null) typeInput.setText(newSel.getType());
                    if (amountInput != null) amountInput.setText(String.valueOf(newSel.getAmount()));
                    if (invoiceCombo != null) invoiceCombo.getSelectionModel().select(newSel);
                }
            });
        }
    }

    @FXML
    private void onOpenQueue() {
        var pending = store.getPendingExpenses();
        expenseList = FXCollections.observableArrayList(pending);
        if (invoiceCombo != null) invoiceCombo.setItems(expenseList);
        if (invoiceTable != null) invoiceTable.setItems(expenseList);
        setStatus("Expense invoice queue loaded. (UIE, OP)");
    }

    private void showDetails() {
        ExpenseInvoice inv = invoiceCombo.getValue();
        if (inv == null) return;
        if (detailsArea != null) {
            detailsArea.setText(String.format("""
                    Invoice: %s
                    Vendor: %s
                    Type: %s
                    Amount: BDT %,.2f
                    Service Terms: %s
                    """, inv.getId(), inv.getVendor(), inv.getType(), inv.getAmount(), inv.getServiceTerms()));
        }
    }

    @FXML
    private void onAddRow() {
        String id = idInput != null ? idInput.getText().trim() : "";
        String vendor = vendorInput != null ? vendorInput.getText().trim() : "";
        String type = typeInput != null ? typeInput.getText().trim() : "";
        double amount = 10000.0;
        try {
            if (amountInput != null && !amountInput.getText().isBlank()) amount = Double.parseDouble(amountInput.getText().trim());
        } catch (NumberFormatException e) {
            setStatus("Enter valid numeric expense amount.");
            return;
        }

        if (id.isEmpty() || vendor.isEmpty()) {
            setStatus("ID and Vendor name are required.");
            return;
        }

        ExpenseInvoice inv = new ExpenseInvoice();
        inv.setId(id);
        inv.setVendor(vendor);
        inv.setType(type.isEmpty() ? "Operational" : type);
        inv.setAmount(amount);
        inv.setServiceTerms("Net 30 Days");
        inv.setStatus("PENDING");

        expenseList.add(inv);
        invoiceTable.getSelectionModel().select(inv);
        setStatus("New expense row added.");
    }

    @FXML
    private void onUpdateRow() {
        ExpenseInvoice sel = invoiceTable.getSelectionModel().getSelectedItem();
        if (sel == null) {
            setStatus("Select a row in the table to update.");
            return;
        }
        if (idInput != null && !idInput.getText().isBlank()) sel.setId(idInput.getText().trim());
        if (vendorInput != null && !vendorInput.getText().isBlank()) sel.setVendor(vendorInput.getText().trim());
        if (typeInput != null && !typeInput.getText().isBlank()) sel.setType(typeInput.getText().trim());
        if (amountInput != null && !amountInput.getText().isBlank()) {
            try { sel.setAmount(Double.parseDouble(amountInput.getText().trim())); } catch (NumberFormatException ignored) {}
        }
        invoiceTable.refresh();
        setStatus("Row updated.");
    }

    @FXML
    private void onDeleteRow() {
        ExpenseInvoice sel = invoiceTable.getSelectionModel().getSelectedItem();
        if (sel == null) {
            setStatus("Select a row in the table to delete.");
            return;
        }
        expenseList.remove(sel);
        setStatus("Row deleted.");
    }

    @FXML
    private void onApprove() {
        ExpenseInvoice inv = invoiceCombo.getValue();
        if (inv == null) {
            setStatus("Select an invoice.");
            return;
        }
        if (!store.verifyVendorTerms(inv.getId())) {
            setStatus("Vendor terms verification failed. (VR)");
            return;
        }
        if (!ValidationUtil.isValidAuthCode(authCodeField.getText())) {
            setStatus("Invalid authorization code. Use AUTH1234 format. (VL)");
            return;
        }
        try {
            store.approveExpense(inv.getId());
            setStatus("Payment approved. Accounting alert sent. (UID, DP, OP)");
            onOpenQueue();
        } catch (Exception e) {
            setStatus("Failed: " + e.getMessage());
        }
    }
}
