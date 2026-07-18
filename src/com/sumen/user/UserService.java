package com.sumen.user;

import com.sumen.Util.StringUtils;
import com.sumen.exception.UserNotFoundException;
import java.util.UUID;


public class UserService {
    private final UserDao userDao;

    public UserService() {
        userDao = new UserDao();
    }

    public User[] getAllUsers() {
        return userDao.findAllUsers();
       }

    public UUID addUser(String userName) {
        if (StringUtils.isNullOrBlank(userName)) {
            throw new IllegalArgumentException("User Name cannot be null or blank");
        }
        return userDao.save(new User(userName));
    }

    public User findUserById(UUID uid) {
        return userDao.findById(uid)
                .orElseThrow(
                        () -> new UserNotFoundException("User not found for this User Id :: %s".formatted(uid.toString())));
    }

    public User findUserByName(String userName) {
        if (StringUtils.isNullOrBlank(userName)) {
            throw new IllegalArgumentException("User Name cannot be null or blank");
        }
        return userDao.findByName(userName)
                .orElseThrow(
                        () -> new UserNotFoundException("User not found for this User Name :: %s".formatted(userName)));
    }

    public void addUsers(){
        UserDao.initializeUsers();
    }
}
