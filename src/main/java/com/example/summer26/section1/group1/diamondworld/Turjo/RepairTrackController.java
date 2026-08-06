package com.example.summer26.section1.group1.diamondworld.Turjo;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class RepairTrackController extends BaseController {

    @FXML private TextField jobCardField;
    @FXML private TextArea statusArea;

    private final DataStore store = DataStore.getInstance();

    @FXML
    private void onTrack() {
        if (jobCardField.getText().isBlank()) {
            setStatus("Enter job card number. (UID)");
            return;
        }
        var job = store.findRepairJob(jobCardField.getText());
        if (job.isEmpty()) {
            setStatus("Job card not found. (VR)");
            statusArea.clear();
            return;
        }
        RepairJob r = job.get();
        statusArea.setText(String.format("""
                Job Card: %s
                Customer: %s
                Item: %s
                Current Stage: %s
                Last Updated: %s
                """, r.getJobCardNo(), r.getCustomerName(),
                r.getItemDescription(), r.getCurrentStage(), r.getLastUpdated()));
        setStatus("Repair status displayed. (VR, DP, OP)");
    }
}




