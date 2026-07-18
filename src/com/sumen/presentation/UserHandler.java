package com.sumen.presentation;

import com.sumen.user.User;
import com.sumen.user.UserService;

public class UserHandler {
    private final UserService userService = new UserService();

    public void showAllUsers() {
        System.out.println("************************* USER LIST *******************************");
            User[] users = userService.getAllUsers();
            if (users.length == 0){
                System.out.println("No Users Found");
            }else {
                for (User user : users){
                    System.out.printf("Name :: %s \t\t Id :: %s", user.getName(), user.getId());
                    System.out.println(" ");
                }
            }
       System.out.println("*******************************************************************");
    }
}
