package com.sumen.user;

import com.sumen.exception.UserNotFoundException;

import java.util.UUID;


public class UserService {
    private final UserDao userDao;

    public UserService() {
        userDao = new UserDao();
    }

    public User[] findAllUsers() {
        User[] users = userDao.getUsers();
        User[] tempUsers = new User[userDao.getUserCount()];


        if (tempUsers.length == 0) {
           throw new UserNotFoundException("No Users Found");
        }
        for (int i = 0; i < userDao.getUserCount(); i++) {
            tempUsers[i] = users[i];
        }
        return tempUsers;


    }

    public UUID addUser(String userName) {
        if (userName == null || userName.isEmpty()) {
            System.out.println("Invalid User Name");
            return null;
        }
        UUID userId = userDao.save(new User(userName));
        System.out.println("User added :: " + userId.toString());
        return userId;
    }

    public User findUserById(String uid) {
        try {
            return userDao.findById(UUID.fromString(uid));
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid User Id :: " + uid);
            return null;
        }
    }

    public User findUserById(UUID uid) {
        try {
            return userDao.findById(uid);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid User Id :: " + uid);
            return null;
        }
    }

    public User findUserByName(String userName) {
        return userDao.findByName(userName);
    }

    public void addUsers(){
        UserDao.addUsers();
    }
}
