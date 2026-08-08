package com.example.summer26.section1.group1.diamondworld.Nupur;
import com.example.summer26.section1.group1.diamondworld.ScenceSwitcher;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class Use8Dashboard {

    @FXML
    public void btnGoal1OnClick(ActionEvent actionEvent) throws IOException {
        ScenceSwitcher.switchTo("/com/example/summer26/section1/group1/diamondworld/Nupur/User8Goal1.fxml", actionEvent);
    }

    @FXML
    public void btnGoal2OnClick(ActionEvent actionEvent) throws IOException {
        ScenceSwitcher.switchTo("/com/example/summer26/section1/group1/diamondworld/Nupur/User8Goal2.fxml", actionEvent);
    }

    @FXML
    public void btnGoal3OnClick(ActionEvent actionEvent) throws IOException {
        ScenceSwitcher.switchTo("/com/example/summer26/section1/group1/diamondworld/Nupur/User8Goal3.fxml", actionEvent);
    }

    @FXML
    public void btnGoal4OnClick(ActionEvent actionEvent) throws IOException {
        ScenceSwitcher.switchTo("/com/example/summer26/section1/group1/diamondworld/Nupur/User8Goal4.fxml", actionEvent);
    }

    @FXML
    public void btnGoal5OnClick(ActionEvent actionEvent) throws IOException {
        ScenceSwitcher.switchTo("/com/example/summer26/section1/group1/diamondworld/Nupur/User8Goal5.fxml", actionEvent);
    }

    @FXML
    public void btnGoal6OnClick(ActionEvent actionEvent) throws IOException {
        ScenceSwitcher.switchTo("/com/example/summer26/section1/group1/diamondworld/Nupur/User8Goal6.fxml", actionEvent);
    }

    @FXML
    public void btnGoal7OnClick(ActionEvent actionEvent) throws IOException {
        ScenceSwitcher.switchTo("/com/example/summer26/section1/group1/diamondworld/Nupur/User8Goal7.fxml", actionEvent);
    }

    @FXML
    public void btnGoal8OnClick(ActionEvent actionEvent) throws IOException {
        ScenceSwitcher.switchTo("/com/example/summer26/section1/group1/diamondworld/Nupur/User8Goal8.fxml", actionEvent);
    }

    @FXML
    public void logOutButtonOA(ActionEvent actionEvent) throws IOException {

        ScenceSwitcher.switchTo("/com/example/summer26/section1/group1/diamondworld/Nupur/login.fxml", actionEvent);

    }
}