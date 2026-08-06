package com.example.summer26.section1.group1.diamondworld.Turjo;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class CustomOrderController extends BaseController {

    @FXML private TextField customerField;
    @FXML private ComboBox<String> metalCombo;
    @FXML private TextField ringSizeField;
    @FXML private ComboBox<String> cutCombo;
    @FXML private TextField depositField;

    private final DataStore store = DataStore.getInstance();

    @FXML
    private void initialize() {
        metalCombo.getItems().addAll("18K Yellow Gold", "18K White Gold", "22K Gold", "Platinum");
        cutCombo.getItems().addAll("Round", "Princess", "Emerald", "Oval", "Cushion");
    }

    @FXML
    private void onCalculateDeposit() {
        if (!ValidationUtil.isValidRingSize(ringSizeField.getText())) {
            setStatus("Ring size must be between 3 and 15. (VR)");
            return;
        }
        double deposit = 75000 * 0.4;
        depositField.setText(String.format("%.0f", deposit));
        setStatus("Minimum advance deposit calculated: BDT " + depositField.getText() + " (DP, OP)");
    }

    @FXML
    private void onSubmit() {
        if (customerField.getText().isBlank() || metalCombo.getValue() == null || cutCombo.getValue() == null) {
            setStatus("Fill all required fields.");
            return;
        }
        if (!ValidationUtil.isValidRingSize(ringSizeField.getText())) {
            setStatus("Invalid ring size. (VR)");
            return;
        }
        try {
            CustomDesignRequest req = store.createCustomOrder(
                    metalCombo.getValue(), ringSizeField.getText(),
                    cutCombo.getValue(), customerField.getText(),
                    Double.parseDouble(depositField.getText().isBlank() ? "30000" : depositField.getText()));
            setStatus(String.format("Order %s filed. Manufacturing ticket issued. (UID, DP, OP)", req.getId()));
        } catch (Exception e) {
            setStatus("Submit failed: " + e.getMessage());
        }
    }
}




