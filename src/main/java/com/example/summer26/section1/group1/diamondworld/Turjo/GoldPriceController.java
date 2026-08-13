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

public class GoldPriceController extends BaseController {

    public static class GoldPriceRow {
        private String karat;
        private double pricePerGram;

        public GoldPriceRow(String karat, double pricePerGram) {
            this.karat = karat;
            this.pricePerGram = pricePerGram;
        }

        public String getKarat() { return karat; }
        public void setKarat(String karat) { this.karat = karat; }
        public double getPricePerGram() { return pricePerGram; }
        public void setPricePerGram(double pricePerGram) { this.pricePerGram = pricePerGram; }
    }

    @FXML private TextField k22Field;
    @FXML private TextField k21Field;
    @FXML private TextField k18Field;

    @FXML private TableView<GoldPriceRow> priceTable;
    @FXML private TableColumn<GoldPriceRow, String> karatCol;
    @FXML private TableColumn<GoldPriceRow, Double> priceCol;

    @FXML private TextField karatInput;
    @FXML private TextField priceInput;

    private final DataStore store = DataStore.getInstance();
    private ObservableList<GoldPriceRow> priceList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        var price = store.getGoldPrice();
        if (k22Field != null) k22Field.setText(String.valueOf(price.getK22()));
        if (k21Field != null) k21Field.setText(String.valueOf(price.getK21()));
        if (k18Field != null) k18Field.setText(String.valueOf(price.getK18()));

        if (priceTable != null) {
            priceTable.setEditable(true);

            karatCol.setCellValueFactory(new PropertyValueFactory<>("karat"));
            priceCol.setCellValueFactory(new PropertyValueFactory<>("pricePerGram"));

            karatCol.setCellFactory(TextFieldTableCell.forTableColumn());
            karatCol.setOnEditCommit(e -> { if (e.getRowValue() != null) e.getRowValue().setKarat(e.getNewValue()); });

            priceCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
            priceCol.setOnEditCommit(e -> { if (e.getRowValue() != null && e.getNewValue() != null) e.getRowValue().setPricePerGram(e.getNewValue()); });

            priceList.add(new GoldPriceRow("22K Gold", price.getK22()));
            priceList.add(new GoldPriceRow("21K Gold", price.getK21()));
            priceList.add(new GoldPriceRow("18K Gold", price.getK18()));
            priceList.add(new GoldPriceRow("24K Pure Gold", 10500.0));
            priceList.add(new GoldPriceRow("Pure Silver 999", 140.0));
            priceTable.setItems(priceList);

            priceTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
                if (newSel != null) {
                    if (karatInput != null) karatInput.setText(newSel.getKarat());
                    if (priceInput != null) priceInput.setText(String.valueOf(newSel.getPricePerGram()));
                }
            });
        }
    }

    @FXML
    private void onAddRow() {
        String karat = karatInput != null ? karatInput.getText().trim() : "";
        double val = 9000.0;
        try {
            if (priceInput != null && !priceInput.getText().isBlank()) val = Double.parseDouble(priceInput.getText().trim());
        } catch (NumberFormatException e) {
            setStatus("Enter valid numeric price per gram.");
            return;
        }
        if (karat.isEmpty()) karat = "Custom Karat";
        GoldPriceRow row = new GoldPriceRow(karat, val);
        priceList.add(row);
        priceTable.getSelectionModel().select(row);
        setStatus("Added new gold price row.");
    }

    @FXML
    private void onUpdateRow() {
        GoldPriceRow sel = priceTable.getSelectionModel().getSelectedItem();
        if (sel == null) {
            setStatus("Select a row in the table to update.");
            return;
        }
        if (karatInput != null && !karatInput.getText().isBlank()) sel.setKarat(karatInput.getText().trim());
        if (priceInput != null && !priceInput.getText().isBlank()) {
            try { sel.setPricePerGram(Double.parseDouble(priceInput.getText().trim())); } catch (NumberFormatException ignored) {}
        }
        priceTable.refresh();
        setStatus("Row updated.");
    }

    @FXML
    private void onDeleteRow() {
        GoldPriceRow sel = priceTable.getSelectionModel().getSelectedItem();
        if (sel == null) {
            setStatus("Select a row in the table to delete.");
            return;
        }
        priceList.remove(sel);
        setStatus("Row deleted.");
    }

    @FXML
    private void onUpdate() {
        if (!ValidationUtil.isPositiveNumber(k22Field.getText())
                || !ValidationUtil.isPositiveNumber(k21Field.getText())
                || !ValidationUtil.isPositiveNumber(k18Field.getText())) {
            setStatus("All prices must be positive numbers. (VL)");
            return;
        }
        double k22 = Double.parseDouble(k22Field.getText());
        double k21 = Double.parseDouble(k21Field.getText());
        double k18 = Double.parseDouble(k18Field.getText());
        if (!store.verifyBullionIndex(k22)) {
            setStatus("22K price outside bullion index range (8000-12000). (VR)");
            return;
        }
        try {
            store.updateGoldPrice(k22, k21, k18);
            setStatus("Gold prices updated. Catalog valuations recalculated. (UID, DP, OP)");
        } catch (Exception e) {
            setStatus("Update failed: " + e.getMessage());
        }
    }
}
