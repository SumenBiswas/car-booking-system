package com.amigos.user;

import java.util.UUID;


public class UserService {
    private final UserDao userDao;

    public UserService() {
        userDao = new UserDao();
    }

    public void viewAllUsers() {
        User[] users = userDao.getUsers();

        System.out.println("************************* USER LIST *******************************");
        if (users.length == 0) {
            System.out.println("************* No users found ***********************");
            return;
        }
       for (int i = 0; i < userDao.getUserCount(); i++) {
            System.out.printf("Name :: %s \t\t Id :: %s", users[i].getName(), users[i].getId());
            System.out.println(" ");
        }
        System.out.println("*******************************************************************");

    }

    public UUID addUser(String userName) {
        if(userName == null || userName.isEmpty()) {
            System.out.println("Invalid User Name");
            return null;
        }
        UUID userId = userDao.save(new User(userName));
        System.out.println("User added :: " + userId.toString());
        return userId;
    }

    public User findUserById(String uid){
        try{
            return userDao.findById(UUID.fromString(uid));
        }catch(IllegalArgumentException e){
            System.out.println("Invalid User Id :: " + uid);
            return null;
        }
    }

    public User findUserById(UUID uid){
        try{
            return userDao.findById(uid);
        }catch(IllegalArgumentException e){
            System.out.println("Invalid User Id :: " + uid);
            return null;
        }
    }

    public User findUserByName(String userName){
        return userDao.findByName(userName);
    }

}
