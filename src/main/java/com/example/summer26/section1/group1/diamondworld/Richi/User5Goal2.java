package com.example.summer26.section1.group1.diamondworld.Richi;

import javafx.event.ActionEvent;
import javafx.scene.control.*;

public class User5Goal2
{
    @javafx.fxml.FXML
    private TableColumn colTotalAmount;
    @javafx.fxml.FXML
    private TableColumn colInvoiceNo;
    @javafx.fxml.FXML
    private TextField txtPaymentRef;
    @javafx.fxml.FXML
    private ComboBox cmbPaymentMethod;
    @javafx.fxml.FXML
    private Button btnReturnHome;
    @javafx.fxml.FXML
    private TextArea txtReceiptView;
    @javafx.fxml.FXML
    private TableColumn colCustomerID;
    @javafx.fxml.FXML
    private Button btnProcessPayment;
    @javafx.fxml.FXML
    private TextField txtPaymentAmount;
    @javafx.fxml.FXML
    private TableColumn colInvoiceDate;
    @javafx.fxml.FXML
    private TableView tableUnpaidInvoices;
    @javafx.fxml.FXML
    private TableColumn  colStatus;
    @javafx.fxml.FXML
    private Button signOutTextField;

    @javafx.fxml.FXML
    public void initialize() {
    }

    @javafx.fxml.FXML
    public void handleProcessPayment(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void handleReturnHome(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void signOutOnButtonClick(ActionEvent actionEvent) {
    }
    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
