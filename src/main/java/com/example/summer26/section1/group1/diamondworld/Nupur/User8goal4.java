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



    @FXML private TableView<customerFeedback> tblFeedback;
    @FXML private TableColumn<customerFeedback, String> colFeedbackId;
    @FXML private TableColumn<customerFeedback, String> colCustomerName;
    @FXML private TableColumn<customerFeedback, String> colRating;
    @FXML private TableColumn<customerFeedback, String> colDate;

    @FXML private Label lblReviewStatus;
    @FXML private TextArea txtManagerRemarks;
    @FXML
    private TableColumn<customerFeedback,String> colDetails;



    @FXML
    public void initialize() {

        colFeedbackId.setCellValueFactory(new PropertyValueFactory<>("feedbackId"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colRating.setCellValueFactory(new PropertyValueFactory<>("rating"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colDetails.setCellValueFactory(new PropertyValueFactory<>("Details"));
        tblFeedback.getItems().add(new customerFeedback("FB-101", "Rahim Ali", "2026-07-20", "5 Stars", "Very good service!"));
        tblFeedback.getItems().add(new customerFeedback("FB-102", "Karim Shah", "2026-07-22", "4 Stars", "Great collection of diamonds."));


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
    void handleReturnHome(ActionEvent actionEvent) throws Exception {
        ScenceSwitcher.switchTo("/com/example/summer26/section1/group1/diamondworld/Nupur/User8Dashboard.fxml", actionEvent);
    }
}