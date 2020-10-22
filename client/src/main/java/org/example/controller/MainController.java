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
import org.example.model.Coordinates;
import org.example.model.Message;
import org.example.model.MusicBand;
import org.example.model.Person;
import org.example.util.UserHolder;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private TextField person_name;

    @FXML
    private TextField person_height;

    @FXML
    private TextField person_passport_id;

    @FXML
    private TextField person_eye_color;

    @FXML
    private TextField np_field;

    @FXML
    private TextField description_field;

    @FXML
    private TextField createdate_field;

    @FXML
    private TextField name_field1;

    @FXML
    private TextField x_field1;

    @FXML
    private TextField y_field1;

    @FXML
    private TextField genre_field1;

    @FXML
    private TextField person_name1;

    @FXML
    private TextField person_height1;

    @FXML
    private TextField person_passport_id1;

    @FXML
    private TextField person_eye_color1;

    @FXML
    private TextField np_field1;

    @FXML
    private TextField description_field1;

    @FXML
    private TextField createdate_field1;

    @FXML
    private Text coordinates_text;

    @FXML
    private Text frontman_text;

    @FXML
    private Text chech_error;

    @FXML
    private Text text_error;

    @FXML
    private Polygon triangle;

    @FXML
    private Rectangle square;

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
    private Button del_all_button;

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
    private Button info_button;

    @FXML
    private Button remove_by_description_button;

    private double xOffSet;
    private double yOffSet;

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");


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
            ClientStart.stage.setWidth(756);
            ClientStart.stage.setHeight(470);
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
            MusicBand musicBand = table.getSelectionModel().getSelectedItem();
            try {
                update_figure(musicBand);
                id = musicBand.getId();
                name_field.setText(musicBand.getName());
                x_field.setText(String.valueOf(musicBand.getCoordinates().getX()));
                y_field.setText(String.valueOf(musicBand.getCoordinates().getY()));
                genre_field.setText(String.valueOf(musicBand.getGenre()));
                person_name.setText(musicBand.getFrontMan().getName());
                person_passport_id.setText(musicBand.getFrontMan().getPassportID());
                person_eye_color.setText(musicBand.getFrontMan().getEyeColor().name());
                person_height.setText(String.valueOf(musicBand.getFrontMan().getHeight()));
                np_field.setText(String.valueOf(musicBand.getNumberOfParticipants()));
                description_field.setText(musicBand.getDescription());
                try {
                    createdate_field.setText(musicBand.getCreationDate().format(dateTimeFormatter));
                } catch (Exception e) {
                    createdate_field.setText("null");
                }
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
            if (result.equals("Элемент успешно удален")) {
                ClientStart.error_windows(result, Color.WHITE);
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
        setDefaultTheme();
        MusicBand musicBand = new MusicBand();
        boolean checkFailed = false;
        checkFailed = fillMusicBand(musicBand, checkFailed);

        if (checkFailed) {
            return;
        }
        try {
            musicBand.setUserName(UserHolder.getUser().getLogin());
            String s = ClientController.sendMessage(new Message(new AddServerCommand(), musicBand));
            if (s.equals("Не прошла валидация на сервере. PasportID должен быть уникальным")) {
                viewError("PasportID должен быть уникальным");
                person_passport_id.setStyle("-fx-background-color: red; -fx-background-radius: 10");
            }
            if (s.equals("Элемент успешно добавлен!")) {
                clean_fields();
                ClientStart.error_windows("Элемент успешно добавлен!", Color.WHITE);
            }
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
        person_name.setText("");
        person_height.setText("");
        person_passport_id.setText("");
        person_eye_color.setText("");
        name_field.setText("");
        createdate_field.setText("");
        name_field1.setText("");
        x_field1.setText("");
        y_field1.setText("");
        np_field1.setText("");
        description_field1.setText("");
        genre_field1.setText("");
        person_name1.setText("");
        person_height1.setText("");
        person_passport_id1.setText("");
        person_eye_color1.setText("");
        name_field1.setText("");
        createdate_field1.setText("");
        setDefaultTheme();
        chech_error.setVisible(false);
        text_error.setVisible(false);
        chech_error1.setVisible(false);
        text_error1.setVisible(false);
        triangle.setVisible(false);
        square.setVisible(false);
        circle.setVisible(false);
        user_color.setVisible(false);
        update_table();
    }

    private void setDefaultTheme() {
        if (ClientStart.theme.equals("Light")) {
            name_field.setStyle("-fx-background-color:  #ddfff8; -fx-background-radius: 10");
            x_field.setStyle("-fx-background-color:  #ddfff8; -fx-background-radius: 10");
            y_field.setStyle("-fx-background-color:  #ddfff8; -fx-background-radius: 10");
            np_field.setStyle("-fx-background-color:  #ddfff8; -fx-background-radius: 10");
            description_field.setStyle("-fx-background-color:  #ddfff8; -fx-background-radius: 10");
            genre_field.setStyle("-fx-background-color:  #ddfff8; -fx-background-radius: 10");
            person_name.setStyle("-fx-background-color:  #ddfff8; -fx-background-radius: 10");
            person_height.setStyle("-fx-background-color:  #ddfff8; -fx-background-radius: 10");
            person_passport_id.setStyle("-fx-background-color:  #ddfff8; -fx-background-radius: 10");
            person_eye_color.setStyle("-fx-background-color:  #ddfff8; -fx-background-radius: 10");

        } else if (ClientStart.theme.equals("Dark")) {
            name_field.setStyle("-fx-background-color:  #4C59D8; -fx-background-radius: 10");
            x_field.setStyle("-fx-background-color:  #4C59D8; -fx-background-radius: 10");
            y_field.setStyle("-fx-background-color:  #4C59D8; -fx-background-radius: 10");
            np_field.setStyle("-fx-background-color:  #4C59D8; -fx-background-radius: 10");
            description_field.setStyle("-fx-background-color:  #4C59D8; -fx-background-radius: 10");
            genre_field.setStyle("-fx-background-color:  #4C59D8; -fx-background-radius: 10");
            person_name.setStyle("-fx-background-color:  #4C59D8; -fx-background-radius: 10");
            person_height.setStyle("-fx-background-color:  #4C59D8; -fx-background-radius: 10");
            person_passport_id.setStyle("-fx-background-color:  #4C59D8; -fx-background-radius: 10");
            person_eye_color.setStyle("-fx-background-color:  #4C59D8; -fx-background-radius: 10");
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
            try {
                setDefaultTheme();
                MusicBand musicBand = new MusicBand();
                boolean checkFailed = false;
                checkFailed = fillMusicBand(musicBand, checkFailed);
                if (!checkFailed) {
                    musicBand.setId(id);
                    String result = ClientController.sendMessage(new Message(new UpdateServerCommand(), musicBand, String.valueOf(id)));
                    System.out.println(result);
                    if (!result.equals("Элемент успешно обновлен!")) {
                        ClientStart.error_windows(result, Color.RED);
                    } else {
                        ClientStart.error_windows(result, Color.WHITE);
                    }
                    update_table();
                }
            } catch (Exception e) {
                viewError("Что-то пошло не так");
                e.printStackTrace();
            }
            update_table();
        } else {
            ClientStart.error_windows("Вы не выбрали объект", Color.RED);
        }
    }

    private boolean fillMusicBand(MusicBand musicBand, boolean checkFailed) {
        Coordinates coordinates = new Coordinates();
        Person person = new Person();
        try {
            musicBand.setName(name_field.getText());
        } catch (Exception exception) {
            viewError(exception.getMessage());
            name_field.setStyle("-fx-background-color: red; -fx-background-radius: 10");
            checkFailed = true;
        }
        try {
            musicBand.setGenre(genre_field.getText());
        } catch (Exception exception) {
            viewError(exception.getMessage());
            genre_field.setStyle("-fx-background-color: red; -fx-background-radius: 10");
            checkFailed = true;
        }
        try {
            musicBand.setNumberOfParticipants(Integer.valueOf(np_field.getText()));
        } catch (Exception exception) {
            viewError(exception.getMessage());
            np_field.setStyle("-fx-background-color: red; -fx-background-radius: 10");
            checkFailed = true;
        }
        try {
            musicBand.setDescription(description_field.getText());
        } catch (Exception exception) {
            viewError(exception.getMessage());
            description_field.setStyle("-fx-background-color: red; -fx-background-radius: 10");
            checkFailed = true;
        }
        try {
            coordinates.setX(Long.valueOf(x_field.getText()));
        } catch (Exception exception) {
            viewError(exception.getMessage());
            x_field.setStyle("-fx-background-color: red; -fx-background-radius: 10");
            checkFailed = true;
        }
        try {
            coordinates.setY(Float.valueOf(y_field.getText()));
        } catch (Exception exception) {
            viewError(exception.getMessage());
            y_field.setStyle("-fx-background-color: red; -fx-background-radius: 10");
            checkFailed = true;
        }
        try {
            person.setName(person_name.getText());
        } catch (Exception exception) {
            viewError(exception.getMessage());
            person_name.setStyle("-fx-background-color: red; -fx-background-radius: 10");
            checkFailed = true;
        }
        try {
            person.setHeight(Double.parseDouble(person_height.getText()));
        } catch (Exception exception) {
            viewError(exception.getMessage());
            person_height.setStyle("-fx-background-color: red; -fx-background-radius: 10");
            checkFailed = true;
        }
        try {
            person.setPassportID(person_passport_id.getText());
        } catch (Exception exception) {
            viewError(exception.getMessage());
            person_passport_id.setStyle("-fx-background-color: red; -fx-background-radius: 10");
            checkFailed = true;
        }
        try {
            person.setEyeColor(person_eye_color.getText());
        } catch (Exception exception) {
            viewError(exception.getMessage());
            person_eye_color.setStyle("-fx-background-color: red; -fx-background-radius: 10");
            checkFailed = true;
        }
        musicBand.setUserName(UserHolder.getUser().getLogin());
        musicBand.setCoordinates(coordinates);
        musicBand.setFrontMan(person);
        return checkFailed;
    }

    @FXML
    void initialize() {
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
        ObservableList<MusicBand> filteredData = mbData.filtered(this::doFilter);
        table.setItems(filteredData);
    }

    private boolean doFilter(MusicBand musicBand1) {
        Coordinates coordinates1 = musicBand1.getCoordinates();
        Person frontMan1 = musicBand1.getFrontMan();
        return isContains(musicBand1.getName(), name_field1.getText()) &&
                isContains(musicBand1.getDescription(), description_field1.getText()) &&
                isContains(musicBand1.getNumberOfParticipants(), np_field1.getText()) &&
                isContains(musicBand1.getGenre(), genre_field1.getText()) &&
                isContains(coordinates1.getX(), x_field1.getText()) &&
                isContains(coordinates1.getY(), y_field1.getText()) &&
                isContains(frontMan1.getName(), person_name1.getText()) &&
                isContains(frontMan1.getPassportID(), person_passport_id1.getText()) &&
                isContains(frontMan1.getHeight(), person_height1.getText()) &&
                isContains(frontMan1.getEyeColor(), person_eye_color1.getText()) &&
                (createdate_field1.getText() == null || "".equals(createdate_field1.getText()) || musicBand1.getCreationDate().toLocalDate().equals(LocalDate.from(dateTimeFormatter.parse(createdate_field1.getText()))));
    }

    private boolean isContains(Object baseObject, Object compared) {
        return (compared == null ||
                String.valueOf(compared).isEmpty() ||
                (baseObject != null && String.valueOf(baseObject).toUpperCase().contains(String.valueOf(compared).toUpperCase())));
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

        ClientStart.error_windows(result, Color.WHITE);

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
        mbData.stream()
                .filter(this::doFilter)
                .forEach(s -> ClientController.sendMessage(new Message(new RemoveServerCommand(), String.valueOf(s.getId()))));
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

        boolean result = isResult(true, name_field, x_field, y_field, genre_field, person_name);
        result = isResult(result, person_eye_color, person_passport_id, person_height, np_field, description_field);
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

        filter_contains_name_button.setText(rb.getString("filter_contains_name_button"));
        remove_by_description_button.setText(rb.getString("remove_by_description_button"));

        info_button.setText(rb.getString("info_button"));
        clean_fields_button.setText(rb.getString("clean_fields_button"));
        filter_button.setText(rb.getString("filter_button"));
        back_button.setText(rb.getString("back_button"));


        name_field.setPromptText(rb.getString("name_field"));
        x_field.setPromptText(rb.getString("x_field"));
        y_field.setPromptText(rb.getString("y_field"));
        genre_field.setPromptText(rb.getString("genre_field"));
        np_field.setPromptText(rb.getString("np_field"));
        person_name.setPromptText(rb.getString("person_name_field"));
        person_height.setPromptText(rb.getString("person_height_field"));
        createdate_field.setPromptText(rb.getString("createdate_field"));
        person_passport_id.setPromptText(rb.getString("person_passport_id_field"));
        description_field.setPromptText(rb.getString("description_field"));
        person_eye_color.setPromptText(rb.getString("person_eye_color_id"));

        clean_fields_button.setText(rb.getString("clean_fields_button"));
        add_button.setText(rb.getString("add_button"));
        update_button.setText(rb.getString("update_button"));
        filter_button.setText(rb.getString("filter_button"));
        more_button.setText(rb.getString("more_button"));
        frontman_text.setText(rb.getString("frontman_text"));
        coordinates_text.setText(rb.getString("coordinates_text"));
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
            ClientStart.stage.setWidth(800);
            ClientStart.stage.setHeight(533);
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
            Parent root = FXMLLoader.load(getClass().getResource("/main" + ClientStart.theme + ".fxml"));
            ClientStart.stage.setScene(new Scene(root));
            ClientStart.stage.setWidth(920);
            ClientStart.stage.setHeight(530);
            ClientStart.stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
