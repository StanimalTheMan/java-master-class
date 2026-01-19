package com.stan.user;

import java.util.UUID;

public class User {
    private UUID id;
    private String name;

    public User(UUID userId, String name) {
        this.id = userId;
        this.name = name;
    }

    public UUID getUserId() {
        return id;
    }

    public void setUserId(UUID userId) {
        this.id = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
