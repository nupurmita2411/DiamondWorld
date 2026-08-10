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
import javafx.util.converter.DoubleStringConverter;

public class GoldExchangeController extends BaseController {

    public static class ExchangeRow {
        private String description;
        private double weight;
        private double purity;
        private double creditValue;

        public ExchangeRow(String description, double weight, double purity, double creditValue) {
            this.description = description;
            this.weight = weight;
            this.purity = purity;
            this.creditValue = creditValue;
        }

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public double getWeight() { return weight; }
        public void setWeight(double weight) { this.weight = weight; }
        public double getPurity() { return purity; }
        public void setPurity(double purity) { this.purity = purity; }
        public double getCreditValue() { return creditValue; }
        public void setCreditValue(double creditValue) { this.creditValue = creditValue; }
    }

    @FXML private TextField weightField;
    @FXML private TextField purityField;
    @FXML private TextArea voucherArea;

    @FXML private TableView<ExchangeRow> exchangeTable;
    @FXML private TableColumn<ExchangeRow, String> descCol;
    @FXML private TableColumn<ExchangeRow, Double> weightCol;
    @FXML private TableColumn<ExchangeRow, Double> purityCol;
    @FXML private TableColumn<ExchangeRow, Double> valueCol;

    private final DataStore store = DataStore.getInstance();
    private ObservableList<ExchangeRow> exchangeList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        if (exchangeTable != null) {
            exchangeTable.setEditable(true);

            descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            weightCol.setCellValueFactory(new PropertyValueFactory<>("weight"));
            purityCol.setCellValueFactory(new PropertyValueFactory<>("purity"));
            valueCol.setCellValueFactory(new PropertyValueFactory<>("creditValue"));

            descCol.setCellFactory(TextFieldTableCell.forTableColumn());
            descCol.setOnEditCommit(e -> { if (e.getRowValue() != null) e.getRowValue().setDescription(e.getNewValue()); });

            weightCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
            weightCol.setOnEditCommit(e -> { if (e.getRowValue() != null && e.getNewValue() != null) e.getRowValue().setWeight(e.getNewValue()); });

            purityCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
            purityCol.setOnEditCommit(e -> { if (e.getRowValue() != null && e.getNewValue() != null) e.getRowValue().setPurity(e.getNewValue()); });

            valueCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
            valueCol.setOnEditCommit(e -> { if (e.getRowValue() != null && e.getNewValue() != null) e.getRowValue().setCreditValue(e.getNewValue()); });

            exchangeList.add(new ExchangeRow("22K Gold Chain Trade-In", 10.5, 91.6, 85000.0));
            exchangeTable.setItems(exchangeList);

            exchangeTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
                if (newSel != null) {
                    if (weightField != null) weightField.setText(String.valueOf(newSel.getWeight()));
                    if (purityField != null) purityField.setText(String.valueOf(newSel.getPurity()));
                }
            });
        }
    }

    @FXML
    private void onCalculate() {
        if (!ValidationUtil.isPositiveNumber(weightField.getText())) {
            setStatus("Enter valid melting weight. (VL)");
            return;
        }
        double purity;
        try {
            purity = Double.parseDouble(purityField.getText());
        } catch (NumberFormatException e) {
            setStatus("Enter valid purity percentage. (VL)");
            return;
        }
        if (!ValidationUtil.isValidPurityRange(purity)) {
            setStatus("Purity must be 50-99.9% per lab certification. (VL)");
            return;
        }
        double weight = Double.parseDouble(weightField.getText());
        double value = store.calculateGoldExchangeValue(weight, purity);
        voucherArea.setText(String.format("""
                === OLD GOLD EXCHANGE VOUCHER ===
                Weight: %.2f grams
                Purity: %.1f%%
                Market Index (22K): BDT %.0f/gram
                Melting Loss: 8%%
                Exchange Credit: BDT %,.2f
                (Applicable toward purchase bill)
                """, weight, purity, store.getGoldPrice().getK22(), value));

        ExchangeRow row = new ExchangeRow("Old Gold Trade-In (" + purity + "%)", weight, purity, value);
        exchangeList.add(row);
        if (exchangeTable != null) exchangeTable.getSelectionModel().select(row);

        setStatus("Exchange voucher generated & added to table. (UID, DP, OP)");
    }

    @FXML
    private void onAddRow() {
        onCalculate();
    }

    @FXML
    private void onUpdateRow() {
        ExchangeRow sel = exchangeTable.getSelectionModel().getSelectedItem();
        if (sel == null) {
            setStatus("Select an exchange row in the table to update.");
            return;
        }
        if (weightField != null && !weightField.getText().isBlank()) {
            try { sel.setWeight(Double.parseDouble(weightField.getText().trim())); } catch (NumberFormatException ignored) {}
        }
        if (purityField != null && !purityField.getText().isBlank()) {
            try { sel.setPurity(Double.parseDouble(purityField.getText().trim())); } catch (NumberFormatException ignored) {}
        }
        exchangeTable.refresh();
        setStatus("Exchange row updated.");
    }

    @FXML
    private void onDeleteRow() {
        ExchangeRow sel = exchangeTable.getSelectionModel().getSelectedItem();
        if (sel == null) {
            setStatus("Select an exchange row in the table to delete.");
            return;
        }
        exchangeList.remove(sel);
        setStatus("Exchange row deleted.");
    }
}
