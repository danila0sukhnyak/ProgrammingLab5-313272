package org.example.util;

import org.example.model.User;

/**
 * Класс хранящий данные пользователя {@link User}.
 */
public class UserHolder {

    private static final ThreadLocal<User> userContext = new ThreadLocal<>();

    public static User getUser() {
        User context = userContext.get();
        if (context == null) {
            context = createEmptyUser();
            userContext.set(context);
        }

        return userContext.get();
    }

    public static void setUser(User context) {
        userContext.set(context);
    }

    public static void cleanupUser() {
        userContext.set(createEmptyUser());
    }

    private static User createEmptyUser() {
        return new User();
    }
}
