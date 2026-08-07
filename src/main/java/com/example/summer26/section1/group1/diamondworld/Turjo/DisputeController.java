package com.example.summer26.section1.group1.diamondworld.Turjo;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DisputeController extends BaseController {

    @FXML private TextField phoneField;
    @FXML private TextField invoiceField;
    @FXML private TextArea detailsArea;
    @FXML private ComboBox<String> resolutionCombo;

    private final DataStore store = DataStore.getInstance();
    private Dispute currentDispute;

    @FXML
    private void initialize() {
        resolutionCombo.getItems().addAll("Product Replacement", "Refund Balance Credit", "Repair & Compensation");
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
        detailsArea.setText(String.format("""
                Dispute ID: %s
                Invoice: %s | Date: %s
                Certificate: %s
                Repair Trail: %s
                Status: %s
                """,
                currentDispute.getId(), currentDispute.getInvoiceId(),
                currentDispute.getPurchaseDate(), currentDispute.getCertificateNo(),
                currentDispute.getRepairTrail(), currentDispute.getStatus()));
        setStatus("Dispute records verified. (UID, VR, OP)");
    }

    @FXML
    private void onResolve() {
        if (currentDispute == null) {
            setStatus("Search for a dispute first.");
            return;
        }
        String resolution = resolutionCombo.getValue();
        if (resolution == null) {
            setStatus("Select a resolution method. (UIE)");
            return;
        }
        try {
            store.resolveDispute(currentDispute.getId(), resolution);
            setStatus("Dispute resolved. Adjustment log generated. (UIE, DP, OP)");
            currentDispute = null;
            detailsArea.clear();
        } catch (Exception e) {
            setStatus("Failed: " + e.getMessage());
        }
    }
}




