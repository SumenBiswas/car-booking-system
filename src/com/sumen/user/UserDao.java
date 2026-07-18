package com.sumen.user;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

public class UserDao {
    private static final User[] users = new User[50];
    private static int userCount = 0;

    public UUID save(User user) {
        users[userCount++] = user;
        return user.getId();
    }


    public Optional<User> findById(UUID userId) {
        for (int i = 0; i < userCount; i++) {
            if (users[i].getId().equals(userId)) {
                return Optional.of(users[i]);
            }
        }
        return Optional.empty();
    }

    public Optional<User> findByName(String userName) {
        for (int i = 0; i < userCount; i++) {
            if (users[i].getName().equalsIgnoreCase(userName)) {
                return Optional.of(users[i]);
            }
        }
        return Optional.empty();
    }

    public User[] findAllUsers() {
        return Arrays.copyOf(users, userCount);
    }

    public static void initializeUsers() {
        users[userCount++] = new User("Ricardo");
        users[userCount++] = new User("Jamila");
        users[userCount++] = new User("Jose");
        users[userCount++] = new User("Juan");
        users[userCount++] = new User("Joseph");

    }
}
