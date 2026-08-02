package com.example.summer26.section1.group1.diamondworld.Nupur;

import com.example.summer26.section1.group1.diamondworld.ScenceSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

public class User8goal2 {

    @FXML private TableView<customerInquiry> inquiriesTableView;
    @FXML private TableColumn<customerInquiry, String> inquiryIdColumn;
    @FXML private TableColumn<customerInquiry, String> customerNameColumn;
    @FXML private TableColumn<customerInquiry, String> subjectColumn;
    @FXML private TableColumn<customerInquiry, String> statusColumn;

    @FXML private TextArea inquiryDetailsTextArea;
    @FXML private TextArea responseMessageTextArea;

    @FXML private Button returnHomeButton;
    @FXML private Button sendResponseButton;
    @FXML private Label confirmationLabel;

    @FXML private TextField inquiryIdTextField;
    @FXML private TextField customerNameTextField;
    @FXML private TextField subjectTextField;
    @FXML private TextField statusTextField;

    @FXML
    public void initialize() {
        inquiryIdColumn.setCellValueFactory(new PropertyValueFactory<>("inquiryId"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        subjectColumn.setCellValueFactory(new PropertyValueFactory<>("subject"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));


        inquiriesTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                inquiryDetailsTextArea.setText(newVal.getDetails());
            }
        });
    }


    @FXML
    void addButtonOnAction(ActionEvent event) {
        String id = (inquiryIdTextField != null) ? inquiryIdTextField.getText().trim() : "";
        String name = (customerNameTextField != null) ? customerNameTextField.getText().trim() : "";
        String subject = (subjectTextField != null) ? subjectTextField.getText().trim() : "";
        String status = (statusTextField != null) ? statusTextField.getText().trim() : "";
        String details = (inquiryDetailsTextArea != null) ? inquiryDetailsTextArea.getText().trim() : "";

        if (status.isEmpty()) {
            status = "Pending";
        }


        if (name.isEmpty() || subject.isEmpty()) {
            if (confirmationLabel != null) {
                confirmationLabel.setText("Notification: Customer Name and Subject are required!");
            }
            return;
        }


        customerInquiry newInquiry = new customerInquiry(id, name, subject, status, details);
        inquiriesTableView.getItems().add(newInquiry);

        if (confirmationLabel != null) {
            confirmationLabel.setText("Notification: Inquiry added successfully!");
        }


        if (inquiryIdTextField != null) inquiryIdTextField.clear();
        if (customerNameTextField != null) customerNameTextField.clear();
        if (subjectTextField != null) subjectTextField.clear();
        if (statusTextField != null) statusTextField.clear();
        inquiryDetailsTextArea.clear();
    }


    @FXML
    void handleSendResponse(ActionEvent event) {
        customerInquiry selectedInquiry = inquiriesTableView.getSelectionModel().getSelectedItem();
        String details = inquiryDetailsTextArea.getText().trim();
        String response = responseMessageTextArea.getText().trim();

        if (selectedInquiry == null) {
            if (confirmationLabel != null) {
                confirmationLabel.setText("Notification: Please select an inquiry from the table first!");
            }
            return;
        }


        if (details.isEmpty()) {
            if (confirmationLabel != null) {
                confirmationLabel.setText("Notification: Inquiry details cannot be empty!");
            }
            return;
        }


        if (response.isEmpty()) {
            if (confirmationLabel != null) {
                confirmationLabel.setText("Notification: Response message cannot be empty!");
            }
            return;
        }


        selectedInquiry.setStatus("Responded");
        inquiriesTableView.refresh();

        if (confirmationLabel != null) {
            confirmationLabel.setText("Notification: Response sent successfully!");
        }


        responseMessageTextArea.clear();
        inquiryDetailsTextArea.clear();
    }


    @FXML
    void handleReturnHome(ActionEvent event) throws IOException {
        ScenceSwitcher.switchTo("/com/example/summer26/section1/group1/diamondworld/Nupur/Use8Dashboard.fxml", event);
    }
}