package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.bootstrap.Bootstrap;
import org.example.controller.ClientController;
import org.example.controller.ErrorController;

import java.io.IOException;


public class ClientStart extends Application {
    public static Stage stage;
    public static Stage stage_error = null;
    public static ClientController clientController;


    public static final double version = 0.4;
    public static String language = "en_US";
    public static String theme = "Light";
    public static final String host = "127.0.0.1";
    public static final String port = "3030";


    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("/auth" + ClientStart.theme + ".fxml"));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(new Scene(root));
//        primaryStage.setMaxWidth(820);
//        primaryStage.setMaxHeight(600);
        primaryStage.setWidth(800);
        primaryStage.setHeight(533);
//        primaryStage.setMinWidth(820);
//        primaryStage.setMinHeight(600);
        primaryStage.setResizable(false);

        primaryStage.alwaysOnTopProperty();
        primaryStage.alwaysOnTopProperty();
        primaryStage.show();

    }

    public static void error_windows(String text, Color color) {
        try {
            ErrorController.text = text;
            ErrorController.color = color;
            Parent root = FXMLLoader.load(ClientStart.class.getResource("/error" + ClientStart.theme + ".fxml"));
            if (stage_error == null) {
                stage_error = new Stage();
                stage_error.initStyle(StageStyle.UNDECORATED);
                stage_error.setScene(new Scene(root));
                stage_error.showAndWait();
            } else {
                stage_error.hide();
                stage_error.setScene(new Scene(root));
                stage_error.showAndWait();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void change_language() {
        switch (ClientStart.language) {
            case "en_US":
                ClientStart.language = "ru_RU";
                break;
            case "ru_RU":
                ClientStart.language = "sl_SL";
                break;
            case "sl_SL":
                ClientStart.language = "en_US";
                break;
        }
    }


    public static void change_theme() {
        switch (ClientStart.theme) {
            case "Dark":
                ClientStart.theme = "Light";
                break;
            case "Light":
                ClientStart.theme = "Dark";
                break;
        }
    }

    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.setArgs(args);
        Thread thread = new Thread(bootstrap);
        thread.setDaemon(true);
        thread.start();
        launch(args);
    }
}
