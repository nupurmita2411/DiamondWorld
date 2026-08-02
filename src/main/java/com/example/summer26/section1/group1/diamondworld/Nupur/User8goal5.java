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
    private Button btnReturnHome;
    @FXML
    private Button btnLaunchCampaign;

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
            clearFields();
        }
    }

    @FXML
    void handleReturnHome(ActionEvent event) throws IOException {
        ScenceSwitcher.switchTo("/com/example/summer26/section1/group1/diamondworld/Nupur/Use8Dashboard.fxml", event);
    }

    private void clearFields() {
        txtCampaignName.clear();
        dpCampaignDuration.setValue(null);
        cmbTargetGroup.setValue(null);
        txtCampaignDescription.clear();
    }
}