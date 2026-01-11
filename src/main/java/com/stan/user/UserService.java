package com.stan.user;

public class UserService {
    private UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User[] getUsers() {
        return userDao.getUsers();
    }

    public User getUserById(String userId) {
        User[] users = getUsers();
        for (User user : users) {
            if (user.getUserId().toString().equals(userId)) {
                return user;
            }
        }
        return null;
    }
}
