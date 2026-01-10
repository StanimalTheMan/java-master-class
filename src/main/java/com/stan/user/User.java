package com.stan.user;

import java.util.UUID;

public class User {
    private UUID userId;
    private String name;

    public User(UUID userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                '}';
    }
}
