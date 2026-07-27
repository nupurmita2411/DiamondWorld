package com.example.summer26.section1.group1.diamondworld.Nupur;

import com.example.summer26.section1.group1.diamondworld.ScenceSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class Use7Dashboard {

    @FXML
    public void initialize() {
        // Initialize logic (if any)
    }

    @FXML
    public void btnGoal1OnClick(ActionEvent actionEvent) throws IOException {
        ScenceSwitcher.switchTo("/com/example/summer26/section1/group1/diamondworld/Nupur/user7goal1.fxml", actionEvent);
    }

    @FXML
    public void btnGoal2OnClick(ActionEvent actionEvent) throws IOException {
        ScenceSwitcher.switchTo("/com/example/summer26/section1/group1/diamondworld/Nupur/User7Goal2.fxml", actionEvent);
    }

    @FXML
    public void btnGoal3OnClick(ActionEvent actionEvent) throws IOException {
        ScenceSwitcher.switchTo("/com/example/summer26/section1/group1/diamondworld/Nupur/User7Goal3.fxml", actionEvent);
    }

    @FXML
    public void btnGoal4OnClick(ActionEvent actionEvent) throws IOException {
        ScenceSwitcher.switchTo("/com/example/summer26/section1/group1/diamondworld/Nupur/User7Goal4.fxml", actionEvent);
    }

    @FXML
    public void btnGoal5OnClick(ActionEvent actionEvent) throws IOException {
        ScenceSwitcher.switchTo("/com/example/summer26/section1/group1/diamondworld/Nupur/User7Goal5.fxml", actionEvent);
    }

    @FXML
    public void btnGoal6OnClick(ActionEvent actionEvent) throws IOException {
        ScenceSwitcher.switchTo("/com/example/summer26/section1/group1/diamondworld/Nupur/User7Goal6.fxml", actionEvent);
    }

    @FXML
    public void btnGoal7OnClick(ActionEvent actionEvent) throws IOException {
        ScenceSwitcher.switchTo("/com/example/summer26/section1/group1/diamondworld/Nupur/User7Goal7.fxml", actionEvent);
    }

    @FXML
    public void btnGoal8OnClick(ActionEvent actionEvent) throws IOException {
        ScenceSwitcher.switchTo("/com/example/summer26/section1/group1/diamondworld/Nupur/User7Goal8.fxml", actionEvent);
    }
}