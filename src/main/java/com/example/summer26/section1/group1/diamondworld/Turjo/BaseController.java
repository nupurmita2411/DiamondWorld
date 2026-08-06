package com.example.summer26.section1.group1.diamondworld.Turjo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public abstract class BaseController {

    @FXML
    protected Label statusLabel;

    protected void setStatus(String message) {
        if (statusLabel != null) {
            statusLabel.setText(message);
        }
    }

    @FXML
    protected void onBack() {
        try {
            Navigation.goToDashboard();
        } catch (IOException e) {
            setStatus("Navigation error: " + e.getMessage());
        }
    }

    @FXML
    protected void onSignOut() {
        try {
            Navigation.signOut();
        } catch (IOException e) {
            setStatus("Sign out error: " + e.getMessage());
        }
    }
}




