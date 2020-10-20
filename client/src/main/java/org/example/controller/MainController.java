package org.example.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.example.ClientStart;
import org.example.command.server.*;
import org.example.model.Message;
import org.example.model.MusicBand;
import org.example.util.UserHolder;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainController {
    private ObservableList<MusicBand> mbData = FXCollections.observableArrayList();

    private Long id = null;
    private boolean only_my = false;


    @FXML
    private AnchorPane parent;

    @FXML
    private Button add_button;

    @FXML
    private Button update_button;

    @FXML
    private Button del_button;

    @FXML
    private Circle circle;

    @FXML
    private Circle user_color;

    @FXML
    private TextField name_field;

    @FXML
    private TextField x_field;

    @FXML
    private TextField y_field;

    @FXML
    private TextField genre_field;

    @FXML
    private TextField album_name_field;

    @FXML
    private TextField album_tracks_field;

    @FXML
    private TextField album_length_field;

    @FXML
    private TextField album_sales_field;

    @FXML
    private TextField np_field;

    @FXML
    private TextField description_field;

    @FXML
    private TextField date_field;

    @FXML
    private Text chech_error;

    @FXML
    private Text text_error;

    @FXML
    private Polygon triangle;

    @FXML
    private Rectangle square;

    @FXML
    private TextField createdate_field;

    @FXML
    private Button more_button;

    @FXML
    private Button del_no_button;

    @FXML
    private Button del_yes_button;

    @FXML
    private Text name;

    @FXML
    private Text version_text;

    @FXML
    private Button map_button;

    @FXML
    private Button theme_button;

    @FXML
    private Button leave_button;

    @FXML
    private Button exit_button;

    @FXML
    private Button language_button;

    @FXML
    private Button clean_fields_button;

    @FXML
    private TableView<MusicBand> table = new TableView<>();

    @FXML
    private TableColumn<MusicBand, Long> table_ids = new TableColumn<>();

    @FXML
    private TableColumn<MusicBand, String> table_name = new TableColumn<>();

    @FXML
    private TableColumn<MusicBand, LocalDateTime> table_date = new TableColumn<>();

    @FXML
    private TableColumn<MusicBand, String> table_description = new TableColumn<>();

    @FXML
    private TableColumn<?, ?> table_user = new TableColumn<>();

    @FXML
    private AnchorPane menu1;

    @FXML
    private ToggleButton filter_button;

    @FXML
    private AnchorPane menu2;

    @FXML
    private Button count_greater_button;

    @FXML
    private Button del_all_button;

    @FXML
    private TextField album_name_field1;

    @FXML
    private TextField album_tracks_field1;

    @FXML
    private TextField album_length_field1;

    @FXML
    private TextField album_sales_field1;

    @FXML
    private Text chech_error1;

    @FXML
    private Text text_error1;

    @FXML
    private Button back_button;

    @FXML
    private Button del_all_no_button;

    @FXML
    private Button del_all_yes_button;

    @FXML
    private Button filter_contains_name_button;

    @FXML
    private TextField name_filter_contains;

    @FXML
    private Button info_button;

    @FXML
    private Button remove_by_description_button;

    @FXML
    private TextField description;


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

    private boolean isResult(boolean result, TextField album_tracks_field, TextField album_length_field, TextField album_sales_field, TextField np_field, TextField description_field) {
        result = result && !album_tracks_field.getText().equals("");
        result = result && !album_length_field.getText().equals("");
        result = result && !album_sales_field.getText().equals("");
        result = result && !np_field.getText().equals("");
        result = result && !description_field.getText().equals("");
        return result;
    }

    @FXML
    void exit(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    void leave(MouseEvent event) {
        UserHolder.cleanupUser();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/auth" + ClientStart.theme + ".fxml"));
            ClientStart.stage.setScene(new Scene(root));
            ClientStart.stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void update_fields() {
        try {
            MusicBand musicBand = table.getSelectionModel().getSelectedItem();
            id = musicBand.getId();
        } catch (Exception ignored) {
        }
        if (id != null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            MusicBand musicBand = table.getSelectionModel().getSelectedItem();
            try {
                update_figure(musicBand);
                id = musicBand.getId();
                name_field.setText(musicBand.getName());
                x_field.setText(String.valueOf(musicBand.getCoordinates().getX()));
                y_field.setText(String.valueOf(musicBand.getCoordinates().getY()));
                genre_field.setText(String.valueOf(musicBand.getGenre()));
                album_name_field.setText(musicBand.getDescription());
                album_tracks_field.setText(String.valueOf(musicBand.getDescription()));
                album_length_field.setText(String.valueOf(musicBand.getDescription()));
                album_sales_field.setText(String.valueOf(musicBand.getDescription()));
                np_field.setText(String.valueOf(musicBand.getNumberOfParticipants()));
                description_field.setText(musicBand.getDescription());
                try {
                    date_field.setText(format.format(musicBand.getCreationDate()));
                } catch (Exception e) {
                    date_field.setText("null");
                }
                createdate_field.setText(String.valueOf(musicBand.getCreationDate()));
                del_yes_button.setVisible(false);
                del_no_button.setVisible(false);
                del_button.setVisible(true);
                add_button.setDisable(false);
                del_button.setDisable(false);
                update_button.setDisable(false);
            } catch (Exception ignored) {
            }
        } else {
            clean_fields();
        }
    }

    @FXML
    void del() {
        if (id != null) {
            del_button.setVisible(false);
            del_yes_button.setVisible(true);
            del_no_button.setVisible(true);
        } else {
            ClientStart.error_windows("Вы не выбрали объект", Color.RED);
        }
    }

    @FXML
    void delYes() {
        if (id != null) {
            String result = ClientController.sendMessage(new Message(new RemoveServerCommand(), String.valueOf(id)));
            if (result.equals("Объект удален")) {
                update_table();
            } else {
                ClientStart.error_windows(result, Color.RED);
            }
            del_button.setVisible(true);
            del_button.setDisable(true);
            update_button.setDisable(true);
            del_yes_button.setVisible(false);
            del_no_button.setVisible(false);
            id = null;
        } else {
            ClientStart.error_windows("Вы не выбрали объект", Color.RED);
        }
    }

    @FXML
    void delNo() {
        del_button.setVisible(true);
        del_yes_button.setVisible(false);
        del_no_button.setVisible(false);
        id = null;
    }

    void update_table() {
        TableColumn.SortType ids = table_ids.getSortType();
        TableColumn.SortType name = table_name.getSortType();
        TableColumn.SortType date = table_date.getSortType();
        TableColumn.SortType des = table_description.getSortType();
        List<MusicBand> data = ClientController.getData();
        if (only_my) {
            mbData = FXCollections.observableArrayList();
            for (MusicBand musicBand : data) {
                if (musicBand.getUserName().equals(UserHolder.getUser().getLogin())) {
                    mbData.add(musicBand);
                }
            }
            table.setItems(mbData);
        } else {
            mbData = FXCollections.observableArrayList();
            mbData.addAll(data);
            table.setItems(mbData);
        }
        table_ids.setSortType(ids);
        table_name.setSortType(name);
        table_date.setSortType(date);
        table_description.setSortType(des);
    }

    @FXML
    void add() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            boolean res = true;
            if (name_field.getText().isEmpty()) {
                res = false;
                viewError("Имя пустое");
                name_field.setStyle("-fx-background-color: red; -fx-background-radius: 10");
            }
            if (x_field.getText().isEmpty() || Double.parseDouble(x_field.getText()) <= -687 || !x_field.getText().matches("(([-+])?[0-9]+(\\.[0-9]+)?)+")) {
                res = false;
                viewError("X число больше -687");
                x_field.setStyle("-fx-background-color: red; -fx-background-radius: 10");
            }
            if (y_field.getText().isEmpty() || Float.parseFloat(y_field.getText()) < -1000f || !y_field.getText().matches("(([-+])?[0-9]+(\\.[0-9]+)?)+")) {
                res = false;
                viewError("Y число больше -1000");
                y_field.setStyle("-fx-background-color: red; -fx-background-radius: 10");
            }
            try {
                if (np_field.getText().isEmpty() || Integer.parseInt(np_field.getText()) <= 0 || !np_field.getText().matches("[-+]?\\d+")) {
                    res = false;
                    viewError("numberOfParticipants больше 0");
                    np_field.setStyle("-fx-background-color: red; -fx-background-radius: 10");
                }
            } catch (Exception e) {
                res = false;
                viewError("numberOfParticipants больше 0");
                np_field.setStyle("-fx-background-color: red; -fx-background-radius: 10");
            }
            if (description_field.getText().isEmpty()) {
                res = false;
                viewError("Описание пустое");
                description_field.setStyle("-fx-background-color: red; -fx-background-radius: 10");
            }
//            if (genre_field.getText().isEmpty() || !GenreReader.checkExist(genre_field.getText())) {
//                res = false;
//                viewError("genre(PSYCHEDELIC_ROCK,RAP,POP,POST_ROCK,POST_PUNK)");
//                genre_field.setStyle("-fx-background-color: red; -fx-background-radius: 10");
//            }
            if (album_name_field.getText().isEmpty()) {
                res = false;
                viewError("Имя альбома пустое");
                album_name_field.setStyle("-fx-background-color: red; -fx-background-radius: 10");
            }
            try {
                if (album_tracks_field.getText().isEmpty() || Integer.parseInt(album_tracks_field.getText()) <= 0 || !album_tracks_field.getText().matches("[-+]?\\d+")) {
                    res = false;
                    viewError("Album tracks число больше 0");
                    album_tracks_field.setStyle("-fx-background-color: red; -fx-background-radius: 10");
                }
            } catch (Exception e) {
                res = false;
                viewError("Album tracks число больше 0");
                album_tracks_field.setStyle("-fx-background-color: red; -fx-background-radius: 10");
            }
            try {
                if (album_length_field.getText().isEmpty() || Integer.parseInt(album_length_field.getText()) <= 0 || !album_length_field.getText().matches("[-+]?\\d+")) {
                    res = false;
                    viewError("Album length число больше 0");
                    album_length_field.setStyle("-fx-background-color: red; -fx-background-radius: 10");
                }
            } catch (Exception e) {
                res = false;
                viewError("Album length число больше 0");
                album_length_field.setStyle("-fx-background-color: red; -fx-background-radius: 10");
            }
            try {
                if (album_sales_field.getText().isEmpty() || Integer.parseInt(album_sales_field.getText()) <= 0 || !album_sales_field.getText().matches("[-+]?\\d+")) {
                    res = false;
                    viewError("Album sales число больше 0");
                    album_sales_field.setStyle("-fx-background-color: red; -fx-background-radius: 10");
                }
            } catch (Exception e) {
                res = false;
                viewError("Album sales число больше 0");
                album_sales_field.setStyle("-fx-background-color: red; -fx-background-radius: 10");
            }
            if (res) {
                viewError("");
                setDefaultTheme();
                Date date;
                if (date_field.getText().equals("") || date_field.getText().equals("null")) {
                    date = null;
                } else {
                    date = format.parse(date_field.getText());
                }
                MusicBand musicBand = new MusicBand();
                musicBand.setUserName(UserHolder.getUser().getLogin());
                musicBand.setName(name_field.getText());
                ClientController.sendMessage(new Message(new AddServerCommand(), musicBand));
                mbData = FXCollections.observableArrayList();
                mbData.addAll(ClientController.getData());
            }
        } catch (ParseException e) {
            viewError("Неправильный формат у даты");
            date_field.setStyle("-fx-background-color: red; -fx-background-radius: 10");
        } catch (Exception e) {
            viewError("Что-то пошло не так");
            e.printStackTrace();
        }
        update_table();
    }

    @FXML
    void clean_fields() {
        id = null;
        name_field.setText("");
        x_field.setText("");
        y_field.setText("");
        np_field.setText("");
        description_field.setText("");
        genre_field.setText("");
        album_name_field.setText("");
        album_tracks_field.setText("");
        album_length_field.setText("");
        album_sales_field.setText("");
        date_field.setText("");
        name_field.setText("");
        createdate_field.setText("");
        description.setText("");
        name_filter_contains.setText("");
        album_name_field1.setText("");
        album_tracks_field1.setText("");
        album_length_field1.setText("");
        album_sales_field1.setText("");
        setDefaultTheme();
        chech_error.setVisible(false);
        text_error.setVisible(false);
        chech_error1.setVisible(false);
        text_error1.setVisible(false);
        triangle.setVisible(false);
        square.setVisible(false);
        circle.setVisible(false);
        user_color.setVisible(false);
    }

    private void setDefaultTheme() {
        if (ClientStart.theme.equals("Light")) {
            name_field.setStyle("-fx-background-color:  #ddfff8; -fx-background-radius: 10");
            x_field.setStyle("-fx-background-color:  #ddfff8; -fx-background-radius: 10");
            y_field.setStyle("-fx-background-color:  #ddfff8; -fx-background-radius: 10");
            np_field.setStyle("-fx-background-color:  #ddfff8; -fx-background-radius: 10");
            description_field.setStyle("-fx-background-color:  #ddfff8; -fx-background-radius: 10");
            genre_field.setStyle("-fx-background-color:  #ddfff8; -fx-background-radius: 10");
            album_name_field.setStyle("-fx-background-color:  #ddfff8; -fx-background-radius: 10");
            album_tracks_field.setStyle("-fx-background-color:  #ddfff8; -fx-background-radius: 10");
            album_length_field.setStyle("-fx-background-color:  #ddfff8; -fx-background-radius: 10");
            album_sales_field.setStyle("-fx-background-color:  #ddfff8; -fx-background-radius: 10");
            album_name_field1.setStyle("-fx-background-color:  #ddfff8; -fx-background-radius: 10");
            album_tracks_field1.setStyle("-fx-background-color:  #ddfff8; -fx-background-radius: 10");
            album_length_field1.setStyle("-fx-background-color:  #ddfff8; -fx-background-radius: 10");
            album_sales_field1.setStyle("-fx-background-color:  #ddfff8; -fx-background-radius: 10");
            date_field.setStyle("-fx-background-color:  #ddfff8; -fx-background-radius: 10");
            description.setStyle("-fx-background-color: #ddfff8; -fx-background-radius: 10");
            name_filter_contains.setStyle("-fx-background-color: #ddfff8; -fx-background-radius: 10");
        } else if (ClientStart.theme.equals("Dark")) {
            name_field.setStyle("-fx-background-color:  #4C59D8; -fx-background-radius: 10");
            x_field.setStyle("-fx-background-color:  #4C59D8; -fx-background-radius: 10");
            y_field.setStyle("-fx-background-color:  #4C59D8; -fx-background-radius: 10");
            np_field.setStyle("-fx-background-color:  #4C59D8; -fx-background-radius: 10");
            description_field.setStyle("-fx-background-color:  #4C59D8; -fx-background-radius: 10");
            genre_field.setStyle("-fx-background-color:  #4C59D8; -fx-background-radius: 10");
            album_name_field.setStyle("-fx-background-color:  #4C59D8; -fx-background-radius: 10");
            album_tracks_field.setStyle("-fx-background-color:  #4C59D8; -fx-background-radius: 10");
            album_length_field.setStyle("-fx-background-color:  #4C59D8; -fx-background-radius: 10");
            album_sales_field.setStyle("-fx-background-color:  #4C59D8; -fx-background-radius: 10");
            album_name_field1.setStyle("-fx-background-color:  #4C59D8; -fx-background-radius: 10");
            album_tracks_field1.setStyle("-fx-background-color:  #4C59D8; -fx-background-radius: 10");
            album_length_field1.setStyle("-fx-background-color:  #4C59D8; -fx-background-radius: 10");
            album_sales_field1.setStyle("-fx-background-color:  #4C59D8; -fx-background-radius: 10");
            date_field.setStyle("-fx-background-color:  #4C59D8; -fx-background-radius: 10");
            description.setStyle("-fx-background-color: #4C59D8; -fx-background-radius: 10");
            name_filter_contains.setStyle("-fx-background-color: #4C59D8; -fx-background-radius: 10");
        }
    }

    void viewError(String text) {
        if (text.equals("")) {
            chech_error.setVisible(false);
            text_error.setText(text);
            text_error.setVisible(false);
        } else {
            chech_error.setVisible(true);
            text_error.setText(text);
            text_error.setVisible(true);
        }
    }


    @FXML
    void update() {
        if (id != null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                boolean res = true;
                if (name_field.getText().isEmpty()) {
                    res = false;
                    viewError("Имя пустое");
                    name_field.setStyle("-fx-background-color: red; -fx-background-radius: 10");
                }
                if (x_field.getText().isEmpty() || Double.parseDouble(x_field.getText()) <= -687 || !x_field.getText().matches("(([-+])?[0-9]+(\\.[0-9]+)?)+")) {
                    res = false;
                    viewError("X число больше -687");
                    x_field.setStyle("-fx-background-color: red; -fx-background-radius: 10");
                }
                if (y_field.getText().isEmpty() || Float.parseFloat(y_field.getText()) < -1000f || !y_field.getText().matches("(([-+])?[0-9]+(\\.[0-9]+)?)+")) {
                    res = false;
                    viewError("Y число больше -1000");
                    y_field.setStyle("-fx-background-color: red; -fx-background-radius: 10");
                }
                try {
                    if (np_field.getText().isEmpty() || Integer.parseInt(np_field.getText()) <= 0 || !np_field.getText().matches("[-+]?\\d+")) {
                        res = false;
                        viewError("numberOfParticipants больше 0");
                        np_field.setStyle("-fx-background-color: red; -fx-background-radius: 10");
                    }
                } catch (Exception e) {
                    res = false;
                    viewError("numberOfParticipants больше 0");
                    np_field.setStyle("-fx-background-color: red; -fx-background-radius: 10");
                }
                if (description_field.getText().isEmpty()) {
                    res = false;
                    viewError("Описание пустое");
                    description_field.setStyle("-fx-background-color: red; -fx-background-radius: 10");
                }
//                if (genre_field.getText().isEmpty() || !GenreReader.checkExist(genre_field.getText())) {
//                    res = false;
//                    viewError("genre(PSYCHEDELIC_ROCK,RAP,POP,POST_ROCK,POST_PUNK)");
//                    genre_field.setStyle("-fx-background-color: red; -fx-background-radius: 10");
//                }
                if (album_name_field.getText().isEmpty()) {
                    res = false;
                    viewError("Имя альбома пустое");
                    album_name_field.setStyle("-fx-background-color: red; -fx-background-radius: 10");
                }
                try {
                    if (album_tracks_field.getText().isEmpty() || Integer.parseInt(album_tracks_field.getText()) <= 0 || !album_tracks_field.getText().matches("[-+]?\\d+")) {
                        res = false;
                        viewError("Album tracks число больше 0");
                        album_tracks_field.setStyle("-fx-background-color: red; -fx-background-radius: 10");
                    }
                } catch (Exception e) {
                    res = false;
                    viewError("Album tracks число больше 0");
                    album_tracks_field.setStyle("-fx-background-color: red; -fx-background-radius: 10");
                }
                try {
                    if (album_length_field.getText().isEmpty() || Integer.parseInt(album_length_field.getText()) <= 0 || !album_length_field.getText().matches("[-+]?\\d+")) {
                        res = false;
                        viewError("Album length число больше 0");
                        album_length_field.setStyle("-fx-background-color: red; -fx-background-radius: 10");
                    }
                } catch (Exception e) {
                    res = false;
                    viewError("Album length число больше 0");
                    album_length_field.setStyle("-fx-background-color: red; -fx-background-radius: 10");
                }
                try {
                    if (album_sales_field.getText().isEmpty() || Integer.parseInt(album_sales_field.getText()) <= 0 || !album_sales_field.getText().matches("[-+]?\\d+")) {
                        res = false;
                        viewError("Album sales число больше 0");
                        album_sales_field.setStyle("-fx-background-color: red; -fx-background-radius: 10");
                    }
                } catch (Exception e) {
                    res = false;
                    viewError("Album sales число больше 0");
                    album_sales_field.setStyle("-fx-background-color: red; -fx-background-radius: 10");
                }
                if (res) {
                    viewError("");
                    setDefaultTheme();
                    Date date;
                    if (date_field.getText().equals("") || date_field.getText().equals("null")) {
                        date = null;
                    } else {
                        date = format.parse(date_field.getText());
                    }
                    MusicBand musicBand = new MusicBand();
                    musicBand.setId(id);
                    update_figure(musicBand);
                    String result = ClientController.sendMessage(new Message(new UpdateServerCommand(), musicBand, String.valueOf(id)));
                    if (!result.equals("Команда update выполнена")) {
                        ClientStart.error_windows(result, Color.RED);
                    }
                    update_table();
                }
            } catch (ParseException e) {
                viewError("Неправильный формат у даты");
                date_field.setStyle("-fx-background-color: #ff0000; -fx-background-radius: 10");
            } catch (Exception e) {
                viewError("Что-то пошло не так");
                e.printStackTrace();
            }
            update_table();
        } else {
            ClientStart.error_windows("Вы не выбрали объект", Color.RED);
        }
    }


    @FXML
    void initialize() {
        version_text.setText(String.valueOf(ClientStart.version));
        name.setText(UserHolder.getUser().getLogin());

        // устанавливаем тип и значение которое должно хранится в колонке
        table_ids.setCellValueFactory(new PropertyValueFactory<>("Id"));
        table_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        table_date.setCellValueFactory(new PropertyValueFactory<>("creationDate"));
        table_description.setCellValueFactory(new PropertyValueFactory<>("description"));
        table_user.setCellValueFactory(new PropertyValueFactory<>("userName"));
        mbData.addAll(ClientController.getData());
        table.setItems(mbData);
        menu1.setVisible(true);
        menu2.setVisible(false);
        triangle.setVisible(false);
        square.setVisible(false);
        circle.setVisible(false);
        user_color.setVisible(false);

        update_language();
    }

    @FXML
    public void filter(MouseEvent mouseEvent) {
        only_my = !only_my;
        update_table();
    }

    @FXML
    public void filter_contains(MouseEvent mouseEvent) {
        if (name_filter_contains.getText().equals("")) {
            chech_error1.setVisible(true);
            text_error1.setVisible(true);
            text_error1.setText("Вы не указали имя");
            name_filter_contains.setStyle("-fx-background-color: red; -fx-background-radius: 10");
        } else {
            name_filter_contains.setStyle("-fx-background-color:  #ddfff8; -fx-background-radius: 10");
            ClientController.sendMessage(new Message(new FilterByDescriptionServerCommand(), name_filter_contains.getText()));
            update_table();
        }
    }

    @FXML
    public void del_all(MouseEvent mouseEvent) {
        del_all_button.setVisible(false);
        del_all_yes_button.setVisible(true);
        del_all_no_button.setVisible(true);
    }

    @FXML
    public void back(MouseEvent mouseEvent) {
        menu1.setVisible(true);
        menu2.setVisible(false);
    }

    @FXML
    public void delallNo(MouseEvent mouseEvent) {
        del_all_button.setVisible(true);
        del_all_yes_button.setVisible(false);
        del_all_no_button.setVisible(false);
    }

    @FXML
    public void delallYes(MouseEvent mouseEvent) {
        String result = ClientController.sendMessage(new Message(new ClearServerCommand()));
        int i = 0;
        for (MusicBand musicBand : ClientController.getData()) {
            if (musicBand.getUserName().equals(UserHolder.getUser().getLogin())) {
                ClientController.sendMessage(new Message(new RemoveServerCommand(), String.valueOf(musicBand.getId())));
                i++;
            }
        }
        ClientStart.error_windows("Удалено: " + i + " объектов", Color.WHITE);

        del_all_button.setVisible(true);
        del_all_yes_button.setVisible(false);
        del_all_no_button.setVisible(false);
        update_table();
    }

    @FXML
    public void more(MouseEvent mouseEvent) {
        menu1.setVisible(false);
        menu2.setVisible(true);
    }

    @FXML
    public void remove_by_description(MouseEvent mouseEvent) {
        if (description.getText().equals("")) {
            chech_error1.setVisible(true);
            text_error1.setVisible(true);
            text_error1.setText("Вы не указали описание");
            description.setStyle("-fx-background-color: red; -fx-background-radius: 10");
        } else {
            description.setStyle("-fx-background-color:  #ddfff8; -fx-background-radius: 10");
            String result = ClientController.sendMessage(new Message(new RemoveByDescriptionServerCommand(), description.getText()));
            if (!result.equals("Объект удален")) {
                ClientStart.error_windows(result, Color.RED);
            }
            update_table();
            chech_error1.setVisible(false);
            text_error1.setVisible(false);
        }
    }

    @FXML
    public void count_greater(MouseEvent mouseEvent) {
        try {
            boolean res = true;
            if (album_name_field1.getText().isEmpty()) {
                res = false;
                viewError("Имя альбома пустое");
                album_name_field1.setStyle("-fx-background-color: red; -fx-background-radius: 10");
            }
            try {
                if (album_tracks_field1.getText().isEmpty() || Integer.parseInt(album_tracks_field1.getText()) <= 0 || !album_tracks_field1.getText().matches("[-+]?\\d+")) {
                    res = false;
                    viewError("Album tracks число больше 0");
                    album_tracks_field1.setStyle("-fx-background-color: red; -fx-background-radius: 10");
                }
            } catch (Exception e) {
                res = false;
                viewError("Album tracks число больше 0");
                album_tracks_field1.setStyle("-fx-background-color: red; -fx-background-radius: 10");
            }
            try {
                if (album_length_field1.getText().isEmpty() || Integer.parseInt(album_length_field1.getText()) <= 0 || !album_length_field1.getText().matches("[-+]?\\d+")) {
                    res = false;
                    viewError("Album length число больше 0");
                    album_length_field1.setStyle("-fx-background-color: red; -fx-background-radius: 10");
                }
            } catch (Exception e) {
                res = false;
                viewError("Album length число больше 0");
                album_length_field1.setStyle("-fx-background-color: red; -fx-background-radius: 10");
            }
            try {
                if (album_sales_field1.getText().isEmpty() || Integer.parseInt(album_sales_field1.getText()) <= 0 || !album_sales_field1.getText().matches("[-+]?\\d+")) {
                    res = false;
                    viewError("Album sales число больше 0");
                    album_sales_field1.setStyle("-fx-background-color: red; -fx-background-radius: 10");
                }
            } catch (Exception e) {
                res = false;
                viewError("Album sales число больше 0");
                album_sales_field1.setStyle("-fx-background-color: red; -fx-background-radius: 10");
            }
//            if (res) {
//                viewError("");
//                setDefaultTheme();
//                MusicBand musicBand = new MusicBand(0L, "", new Coordinates(0, 0f), LocalDateTime.now(), 0, "", null, MusicGenre.POP, new Album(album_name_field1.getText(), Integer.parseInt(album_tracks_field1.getText()), Integer.parseInt(album_length_field1.getText()), Integer.parseInt(album_sales_field1.getText())), ClientController.name);
//                error_windows(ClientController.sendMessage(new Message(new MaxByIdServerCommand(), musicBand)), Color.WHITE);
//                update_table();
//            }
        } catch (Exception e) {
            viewError("Что-то пошло не так");
            e.printStackTrace();
        }
        update_table();
    }

    @FXML
    public void parentOnClick(MouseEvent mouseEvent) {
        parent.setOnMousePressed(events -> {
            xOffSet = events.getSceneX();
            yOffSet = events.getSceneY();
        });
        parent.setOnMouseDragged(events -> {
            ClientStart.stage.setX(events.getScreenX() - xOffSet);
            ClientStart.stage.setY(events.getScreenY() - yOffSet);
        });

        boolean result = isResult(true, name_field, x_field, y_field, genre_field, album_name_field);
        result = isResult(result, album_tracks_field, album_length_field, album_sales_field, np_field, description_field);
        if (!result) {
            add_button.setDisable(true);
            del_button.setDisable(true);
            update_button.setDisable(true);
        } else {
            if (id == null) {
                del_button.setDisable(true);
                update_button.setDisable(true);
            } else {
                del_button.setDisable(false);
                update_button.setDisable(false);
            }
            add_button.setDisable(false);
        }
        del_all_button.setVisible(true);
        del_all_yes_button.setVisible(false);
        del_all_no_button.setVisible(false);
        del_button.setVisible(true);
        del_button.setDisable(true);
        del_yes_button.setVisible(false);
        del_no_button.setVisible(false);
        chech_error1.setVisible(false);
        text_error1.setVisible(false);
        update_button.setDisable(true);
        id = null;
        update_table();
    }

    void update_language() {
        String[] lan = ClientStart.language.split("_");
        Locale current;
        if (lan.length > 1) {
            current = new Locale(lan[0], lan[1]);
        } else {
            current = new Locale(lan[0]);
        }
        ResourceBundle rb = ResourceBundle.getBundle("Client", current);
        //ResourceBundle bundle = ResourceBundle.getBundle("com.example.i18n.text", new UTF8Control());
        map_button.setText(rb.getString("map_button"));
        language_button.setText(rb.getString("language_button"));
        del_all_button.setText(rb.getString("del_all_button"));
        del_all_yes_button.setText(rb.getString("del_all_yes_button"));
        del_all_no_button.setText(rb.getString("del_all_no_button"));

        del_button.setText(rb.getString("del_button"));
        del_yes_button.setText(rb.getString("del_yes_button"));
        del_no_button.setText(rb.getString("del_no_button"));


        album_name_field1.setPromptText(rb.getString("album_name_field1"));
        album_length_field1.setPromptText(rb.getString("album_length_field1"));
        album_sales_field1.setPromptText(rb.getString("album_sales_field1"));
        album_tracks_field1.setPromptText(rb.getString("album_tracks_field1"));

        name_filter_contains.setPromptText(rb.getString("name_filter_contains"));
        description.setPromptText(rb.getString("description"));

        filter_contains_name_button.setText(rb.getString("filter_contains_name_button"));
        remove_by_description_button.setText(rb.getString("remove_by_description_button"));

        count_greater_button.setText(rb.getString("count_greater_button"));
        info_button.setText(rb.getString("info_button"));
        clean_fields_button.setText(rb.getString("clean_fields_button"));
        filter_button.setText(rb.getString("filter_button"));
        back_button.setText(rb.getString("back_button"));


        name_field.setPromptText(rb.getString("name_field"));
        x_field.setPromptText(rb.getString("x_field"));
        y_field.setPromptText(rb.getString("y_field"));
        genre_field.setPromptText(rb.getString("genre_field"));
        np_field.setPromptText(rb.getString("np_field"));
        album_name_field.setPromptText(rb.getString("album_name_field"));
        date_field.setPromptText(rb.getString("date_field"));
        album_tracks_field.setPromptText(rb.getString("album_tracks_field"));
        createdate_field.setPromptText(rb.getString("createdate_field"));
        album_length_field.setPromptText(rb.getString("album_length_field"));
        description_field.setPromptText(rb.getString("description_field"));
        album_sales_field.setPromptText(rb.getString("album_sales_field"));

        clean_fields_button.setText(rb.getString("clean_fields_button"));
        add_button.setText(rb.getString("add_button"));
        update_button.setText(rb.getString("update_button"));
        filter_button.setText(rb.getString("filter_button"));
        more_button.setText(rb.getString("more_button"));
    }

    public void change_language(MouseEvent mouseEvent) {
        ClientStart.change_language();
        update_language();
    }

    private void update_figure(MusicBand musicBand) {
        if (musicBand == null) {
            triangle.setVisible(false);
            square.setVisible(false);
            circle.setVisible(false);
            user_color.setVisible(false);
        } else {
            triangle.setVisible(false);
            square.setVisible(false);
            circle.setVisible(false);
            user_color.setVisible(false);
            int r = Math.abs(musicBand.getName().hashCode() % 250);
            int g = Math.abs(musicBand.getName().hashCode() * 250000 % 250);
            int b = Math.abs(musicBand.getName().hashCode() * 250000000 % 250);
            Color color = Color.rgb(r, g, b);
            r = Math.abs(musicBand.getUserName().hashCode() % 250);
            g = Math.abs(musicBand.getUserName().hashCode() * 250000 % 250);
            b = Math.abs(musicBand.getUserName().hashCode() * 250000000 % 250);
            Color ucolor = Color.rgb(r, g, b);
            switch (musicBand.getGenre()) {
                case PSYCHEDELIC_ROCK:
                    square.setFill(color);
                    square.setVisible(true);
                    user_color.setFill(ucolor);
                    user_color.setVisible(true);
                    break;
                case RAP:
                    triangle.setFill(color);
                    triangle.setVisible(true);
                    user_color.setFill(ucolor);
                    user_color.setVisible(true);
                    break;
                case POP:
                    circle.setFill(color);
                    circle.setVisible(true);
                    user_color.setFill(ucolor);
                    user_color.setVisible(true);
                    break;
            }
        }
    }

    @FXML
    public void map(MouseEvent mouseEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/map" + ClientStart.theme + ".fxml"));
            ClientStart.stage.setScene(new Scene(root));
            ClientStart.stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void info(MouseEvent mouseEvent) {
        try {
            ClientStart.error_windows(ClientController.sendMessage(new Message(new InfoServerCommand())), Color.WHITE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void change_theme(MouseEvent mouseEvent) {
        ClientStart.change_theme();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/FXController" + ClientStart.theme + ".fxml"));
            ClientStart.stage.setScene(new Scene(root));
            ClientStart.stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
