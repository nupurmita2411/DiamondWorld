package com.example.summer26.section1.group1.diamondworld.Turjo;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class GoldPriceController extends BaseController {

    @FXML private TextField k22Field;
    @FXML private TextField k21Field;
    @FXML private TextField k18Field;

    private final DataStore store = DataStore.getInstance();

    @FXML
    private void initialize() {
        var price = store.getGoldPrice();
        k22Field.setText(String.valueOf(price.getK22()));
        k21Field.setText(String.valueOf(price.getK21()));
        k18Field.setText(String.valueOf(price.getK18()));
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




