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

public class SalesReportController extends BaseController {

    public static class ReportRow {
        private String category;
        private double grossSales;
        private double tax;
        private double netProfit;

        public ReportRow(String category, double grossSales, double tax, double netProfit) {
            this.category = category;
            this.grossSales = grossSales;
            this.tax = tax;
            this.netProfit = netProfit;
        }

        public String getCategory() { return category; }
        public void setCategory(String category) { this.category = category; }
        public double getGrossSales() { return grossSales; }
        public void setGrossSales(double grossSales) {
            this.grossSales = grossSales;
            this.tax = grossSales * 0.15;
            this.netProfit = grossSales * 0.25;
        }
        public double getTax() { return tax; }
        public void setTax(double tax) { this.tax = tax; }
        public double getNetProfit() { return netProfit; }
        public void setNetProfit(double netProfit) { this.netProfit = netProfit; }
    }

    @FXML private TextField monthField;
    @FXML private TextField yearField;
    @FXML private TextArea reportArea;

    @FXML private TableView<ReportRow> reportTable;
    @FXML private TableColumn<ReportRow, String> categoryCol;
    @FXML private TableColumn<ReportRow, Double> grossCol;
    @FXML private TableColumn<ReportRow, Double> taxCol;
    @FXML private TableColumn<ReportRow, Double> profitCol;

    @FXML private TextField categoryInput;
    @FXML private TextField grossInput;

    private final DataStore store = DataStore.getInstance();
    private ObservableList<ReportRow> rowList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        if (monthField != null) monthField.setText("6");
        if (yearField != null) yearField.setText("2026");

        if (reportTable != null) {
            reportTable.setEditable(true);

            categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
            grossCol.setCellValueFactory(new PropertyValueFactory<>("grossSales"));
            taxCol.setCellValueFactory(new PropertyValueFactory<>("tax"));
            profitCol.setCellValueFactory(new PropertyValueFactory<>("netProfit"));

            categoryCol.setCellFactory(TextFieldTableCell.forTableColumn());
            categoryCol.setOnEditCommit(e -> { if (e.getRowValue() != null) e.getRowValue().setCategory(e.getNewValue()); });

            grossCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
            grossCol.setOnEditCommit(e -> {
                if (e.getRowValue() != null && e.getNewValue() != null) {
                    e.getRowValue().setGrossSales(e.getNewValue());
                    reportTable.refresh();
                }
            });

            taxCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
            taxCol.setOnEditCommit(e -> { if (e.getRowValue() != null && e.getNewValue() != null) e.getRowValue().setTax(e.getNewValue()); });

            profitCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
            profitCol.setOnEditCommit(e -> { if (e.getRowValue() != null && e.getNewValue() != null) e.getRowValue().setNetProfit(e.getNewValue()); });

            // Sample rows
            rowList.add(new ReportRow("Gold Jewellery", 850000.0, 127500.0, 212500.0));
            rowList.add(new ReportRow("Diamond Jewellery", 1200000.0, 180000.0, 300000.0));
            rowList.add(new ReportRow("Platinum & Gemstones", 450000.0, 67500.0, 112500.0));
            reportTable.setItems(rowList);

            reportTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
                if (newSel != null) {
                    if (categoryInput != null) categoryInput.setText(newSel.getCategory());
                    if (grossInput != null) grossInput.setText(String.valueOf(newSel.getGrossSales()));
                }
            });
        }
    }

    @FXML
    private void onAddRow() {
        String cat = categoryInput != null ? categoryInput.getText().trim() : "";
        double gross = 100000.0;
        try {
            if (grossInput != null && !grossInput.getText().isBlank()) gross = Double.parseDouble(grossInput.getText().trim());
        } catch (NumberFormatException e) {
            setStatus("Enter valid numeric gross sales.");
            return;
        }
        if (cat.isEmpty()) cat = "Custom Category";
        ReportRow row = new ReportRow(cat, gross, gross * 0.15, gross * 0.25);
        rowList.add(row);
        reportTable.getSelectionModel().select(row);
        setStatus("Added new sales category row.");
    }

    @FXML
    private void onUpdateRow() {
        ReportRow sel = reportTable.getSelectionModel().getSelectedItem();
        if (sel == null) {
            setStatus("Select a row in the table to update.");
            return;
        }
        if (categoryInput != null && !categoryInput.getText().isBlank()) sel.setCategory(categoryInput.getText().trim());
        if (grossInput != null && !grossInput.getText().isBlank()) {
            try {
                sel.setGrossSales(Double.parseDouble(grossInput.getText().trim()));
            } catch (NumberFormatException ignored) {}
        }
        reportTable.refresh();
        setStatus("Row updated.");
    }

    @FXML
    private void onDeleteRow() {
        ReportRow sel = reportTable.getSelectionModel().getSelectedItem();
        if (sel == null) {
            setStatus("Select a row in the table to delete.");
            return;
        }
        rowList.remove(sel);
        setStatus("Row deleted.");
    }

    @FXML
    private void onGenerate() {
        try {
            int month = Integer.parseInt(monthField.getText().trim());
            int year = Integer.parseInt(yearField.getText().trim());
            if (!ValidationUtil.isValidMonthYear(month, year)) {
                setStatus("Invalid month/year format. (VL)");
                return;
            }
            MonthlySalesReport report = store.calculateMonthlyReport(month, year);
            if (report == null) {
                setStatus("No sales data for selected period.");
                return;
            }
            boolean verified = Math.abs(report.getGrossSales() - report.getRegisterTotal()) < 1.0;
            String text = String.format("""
                    Monthly Branch Sales Report - %02d/%d
                    ----------------------------------------
                    Gross Sales:     BDT %,.2f
                    Tax (15%%):       BDT %,.2f
                    Net Profit:      BDT %,.2f
                    Register Total:  BDT %,.2f
                    Verification:    %s (VR)
                    """,
                    month, year,
                    report.getGrossSales(), report.getTax(), report.getNetProfit(),
                    report.getRegisterTotal(),
                    verified ? "MATCHED" : "MISMATCH");
            reportArea.setText(text);
            setStatus("Report generated and displayed. (DP, OP)");
        } catch (NumberFormatException e) {
            setStatus("Enter valid numeric month and year. (VL)");
        }
    }
}
