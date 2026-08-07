package com.example.summer26.section1.group1.diamondworld.Turjo;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ExpenseApprovalController extends BaseController {

    @FXML private ComboBox<ExpenseInvoice> invoiceCombo;
    @FXML private TextArea detailsArea;
    @FXML private TextField authCodeField;

    private final DataStore store = DataStore.getInstance();

    @FXML
    private void initialize() {
        invoiceCombo.setItems(FXCollections.observableArrayList(store.getPendingExpenses()));
        invoiceCombo.setOnAction(e -> showDetails());
    }

    @FXML
    private void onOpenQueue() {
        invoiceCombo.setItems(FXCollections.observableArrayList(store.getPendingExpenses()));
        setStatus("Expense invoice queue loaded. (UIE, OP)");
    }

    private void showDetails() {
        ExpenseInvoice inv = invoiceCombo.getValue();
        if (inv == null) return;
        detailsArea.setText(String.format("""
                Invoice: %s
                Vendor: %s
                Type: %s
                Amount: BDT %,.2f
                Service Terms: %s
                """, inv.getId(), inv.getVendor(), inv.getType(), inv.getAmount(), inv.getServiceTerms()));
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




