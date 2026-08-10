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

public class PosSaleController extends BaseController {

    @FXML private TextField barcodeField;
    @FXML private TextField phoneField;
    @FXML private ComboBox<String> paymentCombo;
    @FXML private TextArea receiptArea;

    @FXML private TableView<SaleTransaction> cartTable;
    @FXML private TableColumn<SaleTransaction, String> barcodeCol;
    @FXML private TableColumn<SaleTransaction, String> nameCol;
    @FXML private TableColumn<SaleTransaction, Double> subtotalCol;
    @FXML private TableColumn<SaleTransaction, Double> discountCol;
    @FXML private TableColumn<SaleTransaction, Double> totalCol;

    @FXML private TextField barcodeInput;
    @FXML private TextField nameInput;
    @FXML private TextField subtotalInput;
    @FXML private TextField discountInput;

    private final DataStore store = DataStore.getInstance();
    private ObservableList<SaleTransaction> cartList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        if (paymentCombo != null) {
            paymentCombo.getItems().addAll("Cash", "Credit Card", "EMI");
            paymentCombo.getSelectionModel().selectFirst();
        }

        if (cartTable != null) {
            cartTable.setEditable(true);

            barcodeCol.setCellValueFactory(new PropertyValueFactory<>("productBarcode"));
            nameCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
            subtotalCol.setCellValueFactory(new PropertyValueFactory<>("subtotal"));
            discountCol.setCellValueFactory(new PropertyValueFactory<>("discount"));
            totalCol.setCellValueFactory(new PropertyValueFactory<>("total"));

            barcodeCol.setCellFactory(TextFieldTableCell.forTableColumn());
            barcodeCol.setOnEditCommit(e -> { if (e.getRowValue() != null) e.getRowValue().setProductBarcode(e.getNewValue()); });

            nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
            nameCol.setOnEditCommit(e -> { if (e.getRowValue() != null) e.getRowValue().setProductName(e.getNewValue()); });

            subtotalCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
            subtotalCol.setOnEditCommit(e -> {
                if (e.getRowValue() != null && e.getNewValue() != null) {
                    e.getRowValue().setSubtotal(e.getNewValue());
                    e.getRowValue().setTotal(e.getNewValue() - e.getRowValue().getDiscount());
                    cartTable.refresh();
                }
            });

            discountCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
            discountCol.setOnEditCommit(e -> {
                if (e.getRowValue() != null && e.getNewValue() != null) {
                    e.getRowValue().setDiscount(e.getNewValue());
                    e.getRowValue().setTotal(e.getRowValue().getSubtotal() - e.getNewValue());
                    cartTable.refresh();
                }
            });

            cartTable.setItems(cartList);

            cartTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
                if (newSel != null) {
                    if (barcodeInput != null) barcodeInput.setText(newSel.getProductBarcode());
                    if (nameInput != null) nameInput.setText(newSel.getProductName());
                    if (subtotalInput != null) subtotalInput.setText(String.valueOf(newSel.getSubtotal()));
                    if (discountInput != null) discountInput.setText(String.valueOf(newSel.getDiscount()));
                }
            });
        }
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

        SaleTransaction tx = new SaleTransaction();
        tx.setProductBarcode(p.getBarcode());
        tx.setProductName(p.getName());
        tx.setSubtotal(p.getPrice());
        tx.setDiscount(0.0);
        tx.setTotal(p.getPrice());
        tx.setInvoiceId("INV-TEMP");
        tx.setDate("2026-08-10");

        cartList.add(tx);
        cartTable.getSelectionModel().select(tx);

        setStatus(String.format("Verified & Added to Cart: %s | %.1fg | %s | Cert: %s | 22K: %.0f (VR, DP)",
                p.getName(), p.getWeightGrams(), p.getClarity(), p.getCertificateLink(), gold.getK22()));
    }

    @FXML
    private void onAddRow() {
        String code = barcodeInput != null ? barcodeInput.getText().trim() : "";
        String name = nameInput != null ? nameInput.getText().trim() : "";
        double subtotal = 10000.0;
        double discount = 0.0;
        try {
            if (subtotalInput != null && !subtotalInput.getText().isBlank()) subtotal = Double.parseDouble(subtotalInput.getText().trim());
            if (discountInput != null && !discountInput.getText().isBlank()) discount = Double.parseDouble(discountInput.getText().trim());
        } catch (NumberFormatException e) {
            setStatus("Enter valid numeric values for subtotal and discount.");
            return;
        }
        if (code.isEmpty() || name.isEmpty()) {
            setStatus("Barcode and Product Name are required.");
            return;
        }

        SaleTransaction tx = new SaleTransaction();
        tx.setProductBarcode(code);
        tx.setProductName(name);
        tx.setSubtotal(subtotal);
        tx.setDiscount(discount);
        tx.setTotal(subtotal - discount);
        tx.setInvoiceId("INV-NEW");
        tx.setDate("2026-08-10");

        cartList.add(tx);
        cartTable.getSelectionModel().select(tx);
        setStatus("Cart item row added.");
    }

    @FXML
    private void onUpdateRow() {
        SaleTransaction sel = cartTable.getSelectionModel().getSelectedItem();
        if (sel == null) {
            setStatus("Select a row in the table to update.");
            return;
        }
        if (barcodeInput != null && !barcodeInput.getText().isBlank()) sel.setProductBarcode(barcodeInput.getText().trim());
        if (nameInput != null && !nameInput.getText().isBlank()) sel.setProductName(nameInput.getText().trim());
        if (subtotalInput != null && !subtotalInput.getText().isBlank()) {
            try { sel.setSubtotal(Double.parseDouble(subtotalInput.getText().trim())); } catch (NumberFormatException ignored) {}
        }
        if (discountInput != null && !discountInput.getText().isBlank()) {
            try { sel.setDiscount(Double.parseDouble(discountInput.getText().trim())); } catch (NumberFormatException ignored) {}
        }
        sel.setTotal(sel.getSubtotal() - sel.getDiscount());
        cartTable.refresh();
        setStatus("Row updated.");
    }

    @FXML
    private void onDeleteRow() {
        SaleTransaction sel = cartTable.getSelectionModel().getSelectedItem();
        if (sel == null) {
            setStatus("Select a row in the table to delete.");
            return;
        }
        cartList.remove(sel);
        setStatus("Row deleted.");
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
            SaleTransaction tx = store.processSale(barcodeField.getText().isBlank() ? "DW-RING-001" : barcodeField.getText(), phoneField.getText(), payment);
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
