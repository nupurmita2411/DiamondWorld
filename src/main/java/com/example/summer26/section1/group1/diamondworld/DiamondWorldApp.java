package com.example.summer26.section1.group1.diamondworld;

import com.example.summer26.section1.group1.diamondworld.Turjo.DataStore;
import com.example.summer26.section1.group1.diamondworld.Turjo.Navigation;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class DiamondWorldApp extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        DataStore.getInstance().initialize();
        Navigation.init(stage);

        FXMLLoader loader = new FXMLLoader(
                DiamondWorldApp.class.getResource("/com/example/summer26/section1/group1/diamondworld/Login.fxml"));
        Scene scene = new Scene(loader.load(), 960, 640);

        stage.setTitle("Diamond World - Jewellery Shop Management System");
        stage.setScene(scene);
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}


