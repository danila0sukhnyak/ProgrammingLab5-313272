package org.example.command.server;

import org.example.enums.AuthState;
import org.example.model.Message;
import org.example.model.MusicBand;
import org.example.util.MusicBandValidator;

public class AuthServerCommand extends AbstractServerCommand {

    @Override
    public String command() {
        return "auth";
    }

    @Override
    public String description() {
        return "Авторизация на сервере";
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
//            User foundUser = dbController.findByName(user.getLogin());
//            if (foundUser == null){
//                return AuthState.WRONG_USER.name();
//            }
//            if (!foundUser.getPassword().equals(user.getPassword())){
//                return AuthState.WRONG_PASS.name();
//            }
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
        return AuthState.AUTH_SUCCESS.name();
    }
}
