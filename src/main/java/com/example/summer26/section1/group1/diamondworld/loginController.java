package com.example.summer26.section1.group1.diamondworld;


import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class loginController
{
    @javafx.fxml.FXML
    private TextField UserNameTF;
    @javafx.fxml.FXML
    private Label ErrorLabel;
    @javafx.fxml.FXML
    private PasswordField PasswordF;
    //static ArrayList<SceneSwitcher> List = ArrayList<>;
    @javafx.fxml.FXML
    public void initialize() {
    }


    public static String loggedInUser="";

    @javafx.fxml.FXML
    public void LoginButtonOA(ActionEvent actionEvent) {
        if (true){
            ErrorLabel.setText("Log in successful!");
            loggedInUser =UserNameTF.getText();
            SceneSwitcher .switchTo("dashboard.fxml");
        }
        else {
            ErrorLabel.setText("Log in failed");
        }

    }

    @Deprecated
    public void PasswordOA(ActionEvent actionEvent) {
    }
}