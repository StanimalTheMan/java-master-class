package com.stan.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class UserDao {

    private static final List<User> users;

    static {
        users = new ArrayList<>(
                Arrays.asList(
                    new User(UUID.fromString("8ca51d2b-aaaf-4bf2-834a-e02964e10fc3"), "James"),
                    new User(UUID.fromString("b10d126a-3608-4980-9f9c-aa179f5cebc3"), "Jamila")));
    }

    public List<User> getUsers() {
        return users;
    }
}
