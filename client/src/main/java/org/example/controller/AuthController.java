package org.example.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import org.example.ClientStart;
import org.example.command.server.AuthServerCommand;
import org.example.command.server.RegisterServerCommand;
import org.example.enums.AuthState;


import org.example.model.Message;
import org.example.model.User;
import org.example.util.UserHolder;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class AuthController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane parent;

    @FXML
    private TextField register_name_field;


    @FXML
    private Button register_button;

    @FXML
    private PasswordField register_pass2_field;

    @FXML
    private PasswordField register_pass_field;

    @FXML
    private Text register_pass_error;

    @FXML
    private Text register_passnull_error;

    @FXML
    private Text register_name_error;

    @FXML
    private TextField login_name_field;

    @FXML
    private Button login_button;

    @FXML
    private PasswordField login_pass_field;

    @FXML
    private Text login_pass_error;

    @FXML
    private Text login_name_error;

    @FXML
    private Button exit_button;

    @FXML
    private Text version_text;

    @FXML
    private Button language_button;

    @FXML
    private Button theme_button;

    @FXML
    private Text login_text;

    @FXML
    private Text register_text;

    @FXML
    void exit(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    void login(MouseEvent event) {
        User user = new User(login_name_field.getText(), login_pass_field.getText());
        String result = ClientController.sendMessage(new Message(new AuthServerCommand(), user));

        if (result.equals("Пользователь не найден")) {
            login_name_error.setVisible(true);
            login_pass_error.setVisible(false);
        } else {
            login_name_error.setVisible(false);
            if (result.equals("Неправильный пароль")) {
                login_pass_error.setVisible(true);
            }
            else if(result.equals("Ошибка авторизации")){
                login_pass_error.setVisible(true);
            }
            else if (result.equals(AuthState.AUTH_SUCCESS.name())) {
                login_pass_error.setVisible(false);
                UserHolder.setUser(user);
                //login_button.getScene().getWindow().hide();
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/main" + ClientStart.theme + ".fxml"));
                    ClientStart.stage.setScene(new Scene(root));
                    ClientStart.stage.setWidth(920);
                    ClientStart.stage.setHeight(530);
                    ClientStart.stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Проблемы");
                System.out.println("///////" + result);
            }
        }
    }

    private double xOffSet;
    private double yOffSet;

    @FXML
    void makeDragable(MouseEvent event) {
        parent.setOnMousePressed(events -> {
            xOffSet = events.getSceneX();
            yOffSet = events.getSceneY();
        });
        parent.setOnMouseDragged(events -> {
            ClientStart.stage.setX(events.getScreenX() - xOffSet);
            ClientStart.stage.setY(events.getScreenY() - yOffSet);
        });
    }

    @FXML
    void register(MouseEvent event) {
        if (!register_pass_field.getText().equals(register_pass2_field.getText())) {
            register_pass_error.setVisible(true);
            register_name_error.setVisible(false);
        } else if (register_pass_field.getText().equals("")) {
            register_pass_error.setVisible(false);
            register_passnull_error.setVisible(true);
            register_name_error.setVisible(false);
        } else {
            register_pass_error.setVisible(false);
            register_passnull_error.setVisible(false);
            register_name_error.setVisible(false);
            String result = ClientController.sendMessage(new Message(new RegisterServerCommand(), new User(register_name_field.getText(), register_pass_field.getText())));

            if (result.equals("Имя пользователя занято")) {
                register_name_error.setVisible(true);
            }
            else if (result.equals("Успешная регистрация")) {
                UserHolder.setUser(new User(register_name_field.getText(), register_pass_field.getText()));
                register_name_error.setVisible(false);
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/main" + ClientStart.theme + ".fxml"));
                    ClientStart.stage.setScene(new Scene(root));
                    ClientStart.stage.setWidth(920);
                    ClientStart.stage.setHeight(530);
                    ClientStart.stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("проблемы");
                System.out.println("///////" + result);
            }
        }
    }

    @FXML
    void initialize() {
        update_language(ClientStart.language);
    }

    void update_language(String language) {
        String[] lan = language.split("_");
        Locale current;
        if (lan.length > 1) {
            current = new Locale(lan[0], lan[1]);
        } else {
            current = new Locale(lan[0]);
        }
        ResourceBundle rb = ResourceBundle.getBundle("Client", current);
        language_button.setText(rb.getString("language_button"));
        login_text.setText(rb.getString("login_text"));
        register_text.setText(rb.getString("register_text"));
        login_name_error.setText(rb.getString("login_name_error"));
        login_pass_error.setText(rb.getString("login_pass_error"));
        login_name_field.setPromptText(rb.getString("login_name_field"));
        login_pass_field.setPromptText(rb.getString("login_pass_field"));
        login_button.setText(rb.getString("login_button"));
        register_text.setText(rb.getString("register_text"));
        register_button.setText(rb.getString("register_button"));
        register_name_error.setText(rb.getString("register_name_error"));
        register_pass_error.setText(rb.getString("register_pass_error"));
        register_passnull_error.setText(rb.getString("register_passnull_error"));
        register_name_field.setPromptText(rb.getString("register_name_field"));
        register_pass_field.setPromptText(rb.getString("register_pass_field"));
        register_pass2_field.setPromptText(rb.getString("register_pass2_field"));
    }

    public void change_language(MouseEvent mouseEvent) {
        ClientStart.change_language();
        update_language(ClientStart.language);
    }

    public void change_theme(MouseEvent mouseEvent) {
        ClientStart.change_theme();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/auth" + ClientStart.theme + ".fxml"));
            ClientStart.stage.setScene(new Scene(root));
            ClientStart.stage.setWidth(756);
            ClientStart.stage.setHeight(470);
            ClientStart.stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
