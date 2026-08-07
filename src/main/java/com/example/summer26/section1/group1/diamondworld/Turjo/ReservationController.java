package com.example.summer26.section1.group1.diamondworld.Turjo;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ReservationController extends BaseController {

    @FXML private TextField itemTagField;
    @FXML private TextField customerIdField;

    private final DataStore store = DataStore.getInstance();

    @FXML
    private void onReserve() {
        if (itemTagField.getText().isBlank() || customerIdField.getText().isBlank()) {
            setStatus("Enter item tag and customer ID. (UID)");
            return;
        }
        try {
            Reservation r = store.createReservation(itemTagField.getText(), customerIdField.getText());
            if (r == null) {
                setStatus("Item unavailable or already sold/reserved. (VR)");
                return;
            }
            setStatus(String.format("Reserved! ID: %s | Expires: %s (48hr auto-release) (DP, OP)",
                    r.getId(), r.getExpiryTimestamp()));
        } catch (Exception e) {
            setStatus("Reservation failed: " + e.getMessage());
        }
    }
}




