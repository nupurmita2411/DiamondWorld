package com.example.summer26.section1.group1.diamondworld.Nupur;

import com.example.summer26.section1.group1.diamondworld.ScenceSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class User8goal3 {

    @FXML private TextField searchTextField;
    @FXML private TextField pointsToAddTextField;

    @FXML private Label customerNameLabel;
    @FXML private Label membershipTierLabel;
    @FXML private Label currentPointsLabel;

    @FXML private ComboBox<String> rewardComboBox;

    @FXML
    public void initialize() {
        if (rewardComboBox != null) {
            rewardComboBox.getItems().addAll("10% Discount Coupon", "$20 Gift Card", "Free Diamond Care Kit");
        }
    }


    @FXML
    void handleSearchCustomer(ActionEvent event) {
        String searchInput = searchTextField.getText().trim();

        if ( searchInput.equalsIgnoreCase("Nupur")) {
            customerNameLabel.setText("Nupur Akter");
            membershipTierLabel.setText("Gold Tier");
            currentPointsLabel.setText("150");
        } else {
            customerNameLabel.setText("No Customer Found!");
            membershipTierLabel.setText("N/A");
            currentPointsLabel.setText("0");
        }
    }
    @FXML
    void handleUpdatePoints(ActionEvent event) {
        String pointsInput = pointsToAddTextField.getText().trim();

        if (pointsInput.isEmpty()) {
            return;
        }


        int currentPoints = Integer.parseInt(currentPointsLabel.getText());
        int addPoints = Integer.parseInt(pointsInput);

        int totalPoints = currentPoints + addPoints;
        currentPointsLabel.setText(String.valueOf(totalPoints));

        pointsToAddTextField.clear();
    }

    @FXML
    void handleRedeemReward(ActionEvent event) {
        String selectedReward = rewardComboBox.getValue();

        if (selectedReward != null) {
            int currentPoints = Integer.parseInt(currentPointsLabel.getText());
            if (currentPoints >= 50) {
                currentPointsLabel.setText(String.valueOf(currentPoints - 50));
            }
        }
    }


    @FXML
    void handleReturnHome(ActionEvent event) throws IOException {
        ScenceSwitcher.switchTo("/com/example/summer26/section1/group1/diamondworld/Nupur/Use8Dashboard.fxml", event);
    }
}