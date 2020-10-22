package org.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.ClientStart;

import java.net.URL;
import java.util.ResourceBundle;

public class ErrorController {

    public static String text;
    public static Color color;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane par;

    @FXML
    private Text error_text;

    @FXML
    private TextField login_name_field;

    @FXML
    private Button ok_button;

    @FXML
    private Text login_pass_error;

    @FXML
    private Text login_name_error;

    @FXML
    private Text version_text;

    @FXML
    void ok(MouseEvent event) {
        ok_button.getScene().getWindow().hide();
    }

    private double xOffSet;
    private double yOffSet;

    @FXML
    void makeDragable(MouseEvent event) {
        par.setOnMousePressed(events -> {
            xOffSet = events.getSceneX();
            yOffSet = events.getSceneY();
        });
        par.setOnMouseDragged(events -> {
            Stage stage = (Stage) par.getScene().getWindow();
            stage.setX(events.getScreenX() - xOffSet);
            stage.setY(events.getScreenY() - yOffSet);
        });
    }

    @FXML
    void initialize() {
        error_text.setText(text);
        error_text.setFill(color);
    }
}
