package org.example.dao;

import org.example.model.Coordinates;
import org.example.model.MusicBand;
import org.example.model.Person;
import org.example.model.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class DBController {
    private Connection connection = null;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    private MusicBand fetch(final ResultSet resultSet) throws SQLException {
        MusicBand musicBand = new MusicBand();
        Person person = new Person();
        Coordinates coordinates = new Coordinates();
        if (resultSet.next()) {
            musicBand.setId(resultSet.getLong("id"));
            musicBand.setName(resultSet.getString("name"));
            musicBand.setDescription(resultSet.getString("description"));
            coordinates.setX(resultSet.getLong("COORDINATES_X"));
            coordinates.setY(resultSet.getFloat("COORDINATES_Y"));
            musicBand.setCreationDate(LocalDateTime.ofInstant(resultSet.getTimestamp("CREATION_DATE").toInstant(), ZoneId.systemDefault()));
            musicBand.setNumberOfParticipants(resultSet.getInt("NUMBER_OF_PARTICIPANTS"));
            musicBand.setGenre(resultSet.getString("GENRE"));
            musicBand.setUserName(resultSet.getString("USER_LOGIN"));
            person.setName(resultSet.getString("PERSON_NAME"));
            person.setEyeColor(resultSet.getString("PERSON_EYE_COLOR"));
            person.setPassportID(resultSet.getString("PERSON_PASSPORT_ID"));
            person.setHeight(resultSet.getInt("PERSON_HEIGHT"));
            musicBand.setFrontMan(person);
            musicBand.setCoordinates(coordinates);
            return musicBand;
        }
        return null;
    }

    private User fetchUser(final ResultSet resultSet) throws SQLException {
        User user = new User();
        if (resultSet.next()) {
            user.setLogin(resultSet.getString("name"));
            user.setPassword(resultSet.getString("pass"));
            return user;
        }
        return null;
    }

    public User findByName(String name) throws SQLException {
        final String sql = "SELECT*FROM users WHERE name=?";
        final PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, name);
        ResultSet resultSet = preparedStatement.executeQuery();
        return fetchUser(resultSet);
    }

    public MusicBand findOne(Long id) throws SQLException {
        final String sql = "SELECT ";
        final PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        return fetch(resultSet);
    }

    public Long getId() throws SQLException {
        final String sql = "SELECT NEXTVAL('id_seq')";
        final PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getLong("nextval");
        }
        return 0L;
    }

    public List<MusicBand> findAll() throws SQLException {
        final String sql = "SELECT*FROM music_band";
        final List<MusicBand> list = new ArrayList<>();
        final PreparedStatement preparedStatement = connection.prepareStatement(sql);
        final ResultSet resultSet = preparedStatement.executeQuery();
        MusicBand project;
        do {
            project = fetch(resultSet);
            if (project != null) list.add(project);
        } while (project != null);
        return list;
    }

    public boolean add(User user) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users (NAME,PASS) VALUES (?,?);");
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean add(MusicBand musicBand) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO music_band (ID,NAME,COORDINATES_X,COORDINATES_Y,CREATION_DATE,NUMBER_OF_PARTICIPANTS,DESCRIPTION,GENRE,PERSON_NAME,PERSON_HEIGHT,PERSON_PASSPORT_ID,PERSON_EYE_COLOR,USER_LOGIN) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?);");
            preparedStatement.setLong(1, musicBand.getId());
            preparedStatement.setString(2, musicBand.getName());
            preparedStatement.setLong(3, musicBand.getCoordinates().getX());
            preparedStatement.setFloat(4, musicBand.getCoordinates().getY());
            preparedStatement.setDate(5, Date.valueOf(musicBand.getCreationDate().toLocalDate()));
            preparedStatement.setInt(6, musicBand.getNumberOfParticipants());
            preparedStatement.setString(7, musicBand.getDescription());
            if (musicBand.getGenre() != null) {
                preparedStatement.setString(8, musicBand.getGenre().name());
            }
            preparedStatement.setString(9, musicBand.getFrontMan().getName());
            preparedStatement.setDouble(10, musicBand.getFrontMan().getHeight());
            preparedStatement.setString(11, musicBand.getFrontMan().getPassportID());
            preparedStatement.setString(12, musicBand.getFrontMan().getEyeColor().name());
            preparedStatement.setString(13, musicBand.getUserName());
            preparedStatement.executeUpdate();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(MusicBand musicBand) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE music_band SET NAME=?,COORDINATES_X=?,COORDINATES_Y=?,NUMBER_OF_PARTICIPANTS=?,DESCRIPTION=?,GENRE=?,PERSON_NAME=?,PERSON_HEIGHT=?,PERSON_PASSPORT_ID=?,PERSON_EYE_COLOR=? where id=? and user_login=?;");
            preparedStatement.setLong(11, musicBand.getId());
            preparedStatement.setString(1, musicBand.getName());
            preparedStatement.setLong(2, musicBand.getCoordinates().getX());
            preparedStatement.setFloat(3, musicBand.getCoordinates().getY());
            preparedStatement.setInt(4, musicBand.getNumberOfParticipants());
            preparedStatement.setString(5, musicBand.getDescription());
            if (musicBand.getGenre() != null) {
                preparedStatement.setString(6, musicBand.getGenre().name());
            }
            preparedStatement.setString(7, musicBand.getFrontMan().getName());
            preparedStatement.setDouble(8, musicBand.getFrontMan().getHeight());
            preparedStatement.setString(9, musicBand.getFrontMan().getPassportID());
            preparedStatement.setString(10, musicBand.getFrontMan().getEyeColor().name());
            preparedStatement.setString(12, musicBand.getUserName());
            preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean createTable() {
        try {
            Statement stmt;
            stmt = connection.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS USERS" +
                    "(NAME           TEXT    NOT NULL, " +
                    " PASS            TEXT     NOT NULL)";
            stmt.executeUpdate(sql);
            sql = "CREATE SEQUENCE IF NOT EXISTS id_seq START 1;";
            stmt.executeUpdate(sql);
            sql = "CREATE TABLE IF NOT EXISTS MUSIC_BAND\n" +
                    "    (ID bigint DEFAULT NEXTVAL('id_seq') NOT NULL,\n" +
                    "    NAME text NOT NULL,\n" +
                    "    COORDINATES_X bigint NOT NULL,\n" +
                    "    COORDINATES_Y double precision NOT NULL,\n" +
                    "    CREATION_DATE date NOT NULL,\n" +
                    "    NUMBER_OF_PARTICIPANTS integer NOT NULL,\n" +
                    "    DESCRIPTION text,\n" +
                    "    GENRE text,\n" +
                    "    PERSON_NAME text NOT NULL,\n" +
                    "    PERSON_HEIGHT double precision NOT NULL,\n" +
                    "    PERSON_PASSPORT_ID text NOT NULL,\n" +
                    "    PERSON_EYE_COLOR text NOT NULL,\n" +
                    "    USER_LOGIN text NOT NULL,\n" +
                    "    PRIMARY KEY (ID)\n" +
                    ")";
            stmt.executeUpdate(sql);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
