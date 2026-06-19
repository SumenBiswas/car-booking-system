package com.sumen.user;

import java.util.UUID;

public class UserDao {
    private static final User[] users = new User[50];
    private static int userCount = 0;

    public UserDao() {
        System.out.println("User Dao is created");
    }

    public UUID save(User user) {
        users[userCount++] = user;
        return user.getId();
    }


    public User findById(UUID userId) {
        for (int i = 0; i < userCount; i++) {
            if (users[i].getId().equals(userId)) {
                return users[i];
            }
        }
        return null;
    }

    public User findByName(String userName) {
        if (userName == null || userName.isEmpty()) {
            System.out.println("Invalid User Name");
            return null;
        }
        for (int i = 0; i < userCount; i++) {
            if (users[i].getName().equalsIgnoreCase(userName)) {
                return users[i];
            }
        }
        return null;
    }

    public int getUserCount() {
        return userCount;
    }

    public User[] getUsers() {
        User[] tempUsers = new User[userCount];
        for (int i = 0; i < userCount; i++) {
            tempUsers[i] = users[i];
        }
        return tempUsers;
    }

    public static void addUsers() {
        users[userCount++] = new User("Ricardo");
        users[userCount++] = new User("Jamila");
        users[userCount++] = new User("Jose");
        users[userCount++] = new User("Juan");
        users[userCount++] = new User("Joseph");

    }
}
