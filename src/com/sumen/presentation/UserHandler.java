package com.sumen.presentation;

import com.sumen.exception.UserNotFoundException;
import com.sumen.user.User;
import com.sumen.user.UserService;

public class UserHandler {
    private final UserService userService = new UserService();

    public void showAllUsers() {
        System.out.println("************************* USER LIST *******************************");
        try{
            for (User user : userService.findAllUsers()){
                System.out.printf("Name :: %s \t\t Id :: %s", user.getName(), user.getId());
                System.out.println(" ");
            }
        }catch (UserNotFoundException e){
            System.out.println("*********************** " + e.getMessage() + " ***********************************************************");
        }
        System.out.println("*******************************************************************");
    }
}
