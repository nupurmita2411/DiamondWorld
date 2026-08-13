package com.example.summer26.section1.group1.diamondworld.Turjo;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public final class Navigation {

    private static Stage primaryStage;
    private static Scene currentScene;

    private Navigation() {
    }

    public static void init(Stage stage) {
        primaryStage = stage;
    }

    public static void loadScene(String fxmlPath, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(Navigation.class.getResource(fxmlPath));
        javafx.scene.Parent root = loader.load();
        if (primaryStage.getScene() != null) {
            primaryStage.getScene().setRoot(root);
        } else {
            Scene scene = new Scene(root, 960, 640);
            primaryStage.setScene(scene);
            currentScene = scene;
        }
        primaryStage.setTitle(title);
    }

    public static void goToLogin() throws IOException {
        Session.clear();
        loadScene("/com/example/summer26/section1/group1/diamondworld/Login.fxml", "Diamond World - Login");
    }

    public static void goToDashboard() throws IOException {
        Employee user = Session.getCurrentUser();
        if (user == null) {
            goToLogin();
            return;
        }
        if (user.getRole() == UserRole.BRANCH_MANAGER) {
            loadScene("/com/example/summer26/section1/group1/diamondworld/turjo/branch-manager-dashboard.fxml",
                    "Diamond World - Branch Manager Dashboard");
        } else {
            loadScene("/com/example/summer26/section1/group1/diamondworld/turjo/sales-executive-dashboard.fxml",
                    "Diamond World - Sales Executive Dashboard");
        }
    }

    public static void signOut() throws IOException {
        AuthService.signOut();
        goToLogin();
    }

    public static void openGoal(String fxmlPath, String title) throws IOException {
        loadScene(fxmlPath, title);
    }
}


