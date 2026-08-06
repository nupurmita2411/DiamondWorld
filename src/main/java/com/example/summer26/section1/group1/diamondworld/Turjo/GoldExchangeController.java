package com.example.summer26.section1.group1.diamondworld.Turjo;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class GoldExchangeController extends BaseController {

    @FXML private TextField weightField;
    @FXML private TextField purityField;
    @FXML private TextArea voucherArea;

    private final DataStore store = DataStore.getInstance();

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
        setStatus("Exchange voucher generated. (UID, DP, OP)");
    }
}




