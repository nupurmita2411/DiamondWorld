package com.example.summer26.section1.group1.diamondworld.Nupur;

import com.example.summer26.section1.group1.diamondworld.ScenceSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.ArrayList;

public class User8goal2 {

    @FXML private TableView<customerInquiry> inquiriesTableView;
    @FXML private TableColumn<customerInquiry, String> inquiryIdColumn;
    @FXML private TableColumn<customerInquiry, String> customerNameColumn;
    @FXML private TableColumn<customerInquiry, String> subjectColumn;
    @FXML private TableColumn<customerInquiry, String> statusColumn;

    @FXML private TextArea inquiryDetailsTextArea;
    @FXML private TextArea responseMessageTextArea;

    @FXML private Label confirmationLabel;

    @FXML private TextField inquiryIdTextField;
    @FXML private TextField customerNameTextField;
    @FXML private TextField subjectTextField;
    @FXML private TextField statusTextField;
    static ArrayList<customerInquiry> customerInquiryArrayList= new ArrayList<>();


    @FXML
    public void initialize() {
        inquiryIdColumn.setCellValueFactory(new PropertyValueFactory<>("inquiryId"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        subjectColumn.setCellValueFactory(new PropertyValueFactory<>("subject"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        inquiriesTableView.getItems().addAll(customerInquiryArrayList);


    }


    @FXML
    void addButtonOnAction(ActionEvent event) {

        String id = inquiryIdTextField.getText();
        String name = customerNameTextField.getText();
        String subject = subjectTextField.getText();
        String status = statusTextField.getText();
        String details = inquiryDetailsTextArea.getText();

        if (id.isEmpty() || name.isEmpty()) {
            confirmationLabel.setText("Status: Please enter Supplier ID and Name!");
        } else {

            customerInquiry c = new customerInquiry(id, name, subject, status, details);
            customerInquiryArrayList.add(c);

             inquiriesTableView.getItems().add(c);
            confirmationLabel.setText("Status: Supplier added successfully!");
            inquiryIdTextField.clear();
            customerNameTextField.clear();
            subjectTextField.clear();
            statusTextField.clear();
            inquiryDetailsTextArea.clear();
        }
    }


    @FXML
    void handleSendResponse(ActionEvent event) {

        customerInquiry selected = inquiriesTableView.getSelectionModel().getSelectedItem();

        if (selected == null) {
            confirmationLabel.setText("Please select an inquiry.");
            return;
        }

        if (responseMessageTextArea.getText().isEmpty()) {
            confirmationLabel.setText("Please write a response.");
            return;
        }

        confirmationLabel.setText("Response sent successfully!");

        responseMessageTextArea.clear();
        inquiryDetailsTextArea.clear();
    }

    @FXML
    void handleReturnHome(ActionEvent actionEvent) throws IOException {
        ScenceSwitcher.switchTo("/com/example/summer26/section1/group1/diamondworld/Nupur/User8Dashboard.fxml", actionEvent);
    }
}