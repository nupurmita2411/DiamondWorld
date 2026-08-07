package com.example.summer26.section1.group1.diamondworld.Turjo;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class SalesReportController extends BaseController {

    @FXML private TextField monthField;
    @FXML private TextField yearField;
    @FXML private TextArea reportArea;

    private final DataStore store = DataStore.getInstance();

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




