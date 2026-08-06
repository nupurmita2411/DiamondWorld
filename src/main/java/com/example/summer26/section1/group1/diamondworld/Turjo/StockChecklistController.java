package com.example.summer26.section1.group1.diamondworld.Turjo;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class StockChecklistController extends BaseController {

    @FXML private TextField caseNameField;
    @FXML private TextField physicalCountField;

    private final DataStore store = DataStore.getInstance();

    @FXML
    private void onSubmit() {
        if (caseNameField.getText().isBlank()) {
            setStatus("Enter display case name. (UID)");
            return;
        }
        int physical;
        try {
            physical = Integer.parseInt(physicalCountField.getText());
        } catch (NumberFormatException e) {
            setStatus("Enter valid physical count. (VL)");
            return;
        }
        try {
            StockChecklistEntry entry = store.saveChecklist(caseNameField.getText(), physical);
            int expected = entry.getExpectedCount();
            if (entry.getDiscrepancy() != 0) {
                setStatus(String.format("DISCREPANCY flagged! Expected: %d | Physical: %d | Diff: %d (VR, DP, OP)",
                        expected, physical, entry.getDiscrepancy()));
            } else {
                setStatus(String.format("Checklist balanced for %s. Saved to server. (VR, DP, OP)",
                        entry.getCaseName()));
            }
        } catch (Exception e) {
            setStatus("Save failed: " + e.getMessage());
        }
    }
}




