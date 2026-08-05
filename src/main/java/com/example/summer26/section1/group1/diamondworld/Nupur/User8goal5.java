package com.example.summer26.section1.group1.diamondworld.Nupur;

import com.example.summer26.section1.group1.diamondworld.ScenceSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

public class User8goal5 {

    @FXML private TextField txtCampaignName;
    @FXML private DatePicker dpCampaignDuration;
    @FXML private ComboBox<String> cmbTargetGroup;
    @FXML private TextArea txtCampaignDescription;
    @FXML private Label lblConfirmationStatus;


    @FXML
    public void initialize() {

        cmbTargetGroup.getItems().addAll("Silver", "Gold", "Platinum", "All");
    }

    @FXML
    void handleLaunchCampaign(ActionEvent event) {
        String name = txtCampaignName.getText();
        String group = cmbTargetGroup.getValue();

        if (name.isEmpty() || group == null || dpCampaignDuration.getValue() == null) {
            lblConfirmationStatus.setText("Please fill in all required fields!");
        } else {
            lblConfirmationStatus.setText("Campaign '" + name + "' launched successfully for " + group + " customers!");
            txtCampaignName.clear();
            dpCampaignDuration.setValue(null);
            cmbTargetGroup.setValue(null);
            txtCampaignDescription.clear();
        }
    }

    @FXML
    void handleReturnHome(ActionEvent actionEvent) throws IOException {
        ScenceSwitcher.switchTo("/com/example/summer26/section1/group1/diamondworld/Nupur/User8Dashboard.fxml", actionEvent);
    }
}