package org.example.command.server;

import org.example.enums.AuthState;
import org.example.model.Message;
import org.example.util.MusicBandValidator;

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
     */
    @Override
    public String execute(Message args) {
//        DBController dbController = serviceLocator.getDbController();
//        User user = args.getUser();
//        try {
//            dbController.setConnection(DatabaseUtil.getConnection());
//            User foundUser = dbController.findByName(user.getLogin());
//            if (foundUser != null) {
//                return AuthState.WRONG_USER.name();
//            }
//            dbController.add(user);
//            dbController.getConnection().commit();
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//            try {
//                dbController.getConnection().rollback();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//            return "Ошибка на сервере";
//        } finally {
//            DatabaseUtil.closeConnection();
//        }
        return AuthState.AUTH_SUCCESS.name();
    }
}
