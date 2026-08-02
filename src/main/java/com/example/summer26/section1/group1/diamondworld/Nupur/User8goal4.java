package com.example.summer26.section1.group1.diamondworld.Nupur;



import com.example.summer26.section1.group1.diamondworld.ScenceSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;

public class User8goal4 {

    @FXML private Button btnMarkAsReviewed;
    @FXML private Button btnReturnHome;

    @FXML private TableView<customerFeedback> tblFeedback;
    @FXML private TableColumn<customerFeedback, String> colFeedbackId;
    @FXML private TableColumn<customerFeedback, String> colCustomerName;
    @FXML private TableColumn<customerFeedback, String> colRating;
    @FXML private TableColumn<customerFeedback, String> colDate;

    @FXML private Label lblReviewStatus;
    @FXML private TextArea txtFeedbackDetails;
    @FXML private TextArea txtManagerRemarks;

    @FXML
    public void initialize() {

        colFeedbackId.setCellValueFactory(new PropertyValueFactory<>("feedbackId"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colRating.setCellValueFactory(new PropertyValueFactory<>("rating"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        tblFeedback.getItems().add(new customerFeedback("FB-101", "Rahim Ali", "5 Stars", "2026-07-20", "Very good service!"));
        tblFeedback.getItems().add(new customerFeedback("FB-102", "Karim Shah", "4 Stars", "2026-07-22", "Great collection of diamonds."));


        tblFeedback.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                txtFeedbackDetails.setText(newVal.getDetails());
            }
        });
    }


    @FXML
    void handleMarkAsReviewed(ActionEvent event) {
        customerFeedback selected = tblFeedback.getSelectionModel().getSelectedItem();

        if (selected == null) {
            lblReviewStatus.setText("Select a feedback first!");
        } else {
            lblReviewStatus.setText("Reviewed: " + selected.getFeedbackId());
            txtManagerRemarks.clear();
        }
    }


    @FXML
    void handleReturnHome(ActionEvent event) throws Exception {
        ScenceSwitcher.switchTo("/com/example/summer26/section1/group1/diamondworld/Nupur/Use8Dashboard.fxml", event);
    }
}