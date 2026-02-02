package com.stan.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    public static final int EXPECTED_INITIAL_USERS_COUNT = 2;

    private final UserService underTest =
            new UserService(new UserDao());

    @Test
    void canGetUsers() {
        // when
        List<User> users = underTest.getUsers();
        // then
        assertThat(users.size()).isEqualTo(EXPECTED_INITIAL_USERS_COUNT);
    }

    @ParameterizedTest
    @CsvSource({
            "8ca51d2b-aaaf-4bf2-834a-e02964e10fc3",
            "b10d126a-3608-4980-9f9c-aa179f5cebc3",
    })
    void canGetUserByExistingId(UUID userId) {
        // when
        User user = underTest.getUserById(userId);
        // then
        assertThat(user.getUserId()).isEqualTo(userId);
    }

    @ParameterizedTest
    @CsvSource({
            "sfsdf",
            "1"
    })
    void willFailToGetUserByInvalidId(String userId) {
        assertThatThrownBy(() -> underTest.getUserById(UUID.fromString(userId))).isInstanceOf(IllegalArgumentException.class);
    }
}