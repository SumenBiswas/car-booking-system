package com.amigos.service;

import com.amigos.data.DataStore;
import com.amigos.model.User;

public class UserService {

    public static void viewAllUsers() {
        User[] users = DataStore.users;
        int userCount = DataStore.userCount;
        System.out.println("************************* USER LIST *******************************");
        if (userCount == 0) {
            System.out.println("No users found");
        }
        for (int i = 0; i < userCount; i++) {
            System.out.println("Name :: " + users[i].getName() + "\t\t Id : " + users[i].getId());
        }
        System.out.println("*******************************************************************");

    }

    public static User addUser(String usename) {
        User[] users = DataStore.users;

        User user = new User(usename);
        DataStore.users[DataStore.userCount++] = user;
        System.out.println("User added");

        return user;
    }

    public static User findUserById(String uid){
        User[] users = DataStore.users;
        for (User user: users){
            if(user != null && user.getId().toString().equals(uid)){
                return user;
            }
        }
        return null;
    }

    public static User findUserByName(String userName){
        User[] users = DataStore.users;
        for (User user: users){
            if(user.getName().equals(userName)){
                return user;
            }
        }
        return null;
    }

}
