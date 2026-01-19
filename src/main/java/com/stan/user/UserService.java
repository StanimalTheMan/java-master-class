package com.stan.user;

import java.util.UUID;

public class UserService {
    private UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User[] getUsers() {
        return userDao.getUsers();
    }

    public User getUserById(UUID userId) {
        User[] users = getUsers();
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                return user;
            }
        }
        return null;
    }
}
