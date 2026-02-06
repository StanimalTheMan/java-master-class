package com.stan.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    public static final int EXPECTED_INITIAL_USERS_COUNT = 2;

    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserService underTest;

    @Test
    void canGetUsers() {
        // given
        when(userDao.getUsers()).thenReturn(new ArrayList<>(
                Arrays.asList(
                        new User(UUID.fromString("8ca51d2b-aaaf-4bf2-834a-e02964e10fc3"), "James"),
                        new User(UUID.fromString("b10d126a-3608-4980-9f9c-aa179f5cebc3"), "Jamila"))));
        // when
        List<User> users = underTest.getUsers();
        // then
        verify(userDao).getUsers();
        assertThat(users.size()).isEqualTo(EXPECTED_INITIAL_USERS_COUNT);
    }

    @ParameterizedTest
    @CsvSource({
            "8ca51d2b-aaaf-4bf2-834a-e02964e10fc3",
            "b10d126a-3608-4980-9f9c-aa179f5cebc3",
    })
    void canGetUserByExistingId(UUID userId) {
        // given
        when(userDao.getUsers()).thenReturn(new ArrayList<>(
                Arrays.asList(
                        new User(UUID.fromString("8ca51d2b-aaaf-4bf2-834a-e02964e10fc3"), "James"),
                        new User(UUID.fromString("b10d126a-3608-4980-9f9c-aa179f5cebc3"), "Jamila"))));
        // when
        User user = underTest.getUserById(userId);
        // then
        verify(userDao).getUsers();
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