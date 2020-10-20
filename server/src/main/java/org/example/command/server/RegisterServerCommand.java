package org.example.command.server;

import org.apache.commons.codec.digest.DigestUtils;
import org.example.dao.DBController;
import org.example.model.Message;
import org.example.model.User;
import org.example.util.DatabaseUtil;
import org.example.util.MusicBandValidator;

import java.sql.SQLException;

public class RegisterServerCommand extends AbstractServerCommand {

    @Override
    public String command() {
        return "register";
    }

    @Override
    public String description() {
        return "Регистрация на сервере";
    }

    /**
     * Использует {@link MusicBandValidator} для наполнения объекта {@link MusicBand}
     * и добавляет его в коллекцию
     *
     * @param args аргументы
     * @return
     */
    @Override
    public Message execute(Message args) {
        DBController dbController = serviceLocator.getDbController();
        User user = args.getUser();
        try {
            dbController.setConnection(DatabaseUtil.getConnection());
            User foundUser = dbController.findByName(user.getLogin());
            if (foundUser != null) {
                return new Message("Пользователь уже существует");
            }
            String password = user.getPassword();
            if (password != null) {
                user.setPassword(DigestUtils.md2Hex(password));
            }
            dbController.add(user);
            dbController.getConnection().commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            try {
                dbController.getConnection().rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return new Message("Ошибка на сервере");
        } finally {
            DatabaseUtil.closeConnection();
        }
        return new Message("Успешная регистрация");
    }
}
