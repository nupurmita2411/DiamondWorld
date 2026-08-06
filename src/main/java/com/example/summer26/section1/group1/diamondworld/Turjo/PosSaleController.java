package com.example.summer26.section1.group1.diamondworld.Turjo;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class PosSaleController extends BaseController {

    @FXML private TextField barcodeField;
    @FXML private TextField phoneField;
    @FXML private ComboBox<String> paymentCombo;
    @FXML private TextArea receiptArea;

    private final DataStore store = DataStore.getInstance();

    @FXML
    private void initialize() {
        paymentCombo.getItems().addAll("Cash", "Credit Card", "EMI");
        paymentCombo.getSelectionModel().selectFirst();
    }

    @FXML
    private void onScan() {
        String barcode = barcodeField.getText().trim();
        if (barcode.isEmpty()) {
            setStatus("Scan or enter barcode. (UIE)");
            return;
        }
        var product = store.findProductByBarcode(barcode);
        if (product.isEmpty()) {
            setStatus("Product not found in inventory. (VR)");
            return;
        }
        Product p = product.get();
        var gold = store.getGoldPrice();
        setStatus(String.format("Verified: %s | %.1fg | %s | Cert: %s | 22K: %.0f (VR, DP)",
                p.getName(), p.getWeightGrams(), p.getClarity(), p.getCertificateLink(), gold.getK22()));
    }

    @FXML
    private void onCheckout() {
        if (!ValidationUtil.isValidPhone(phoneField.getText())) {
            setStatus("Invalid customer phone. (VL)");
            return;
        }
        String payment = paymentCombo.getValue();
        if (payment == null) {
            setStatus("Select payment mode. (VR)");
            return;
        }
        try {
            SaleTransaction tx = store.processSale(barcodeField.getText(), phoneField.getText(), payment);
            if (tx == null) {
                setStatus("Sale failed - out of stock or invalid item. (VR)");
                return;
            }
            receiptArea.setText(String.format("""
                    === DIAMOND WORLD RECEIPT ===
                    Invoice: %s
                    Product: %s
                    Subtotal:  BDT %,.2f
                    Discount:  BDT %,.2f
                    Tax:       BDT %,.2f
                    TOTAL:     BDT %,.2f
                    Payment:   %s
                    Date:      %s
                    """, tx.getInvoiceId(), tx.getProductName(),
                    tx.getSubtotal(), tx.getDiscount(), tx.getTax(), tx.getTotal(),
                    tx.getPaymentMode(), tx.getDate()));
            setStatus("Transaction complete. Stock updated. Receipt printed. (UID, VR, DP, OP)");
        } catch (Exception e) {
            setStatus("Checkout failed: " + e.getMessage());
        }
    }
}




