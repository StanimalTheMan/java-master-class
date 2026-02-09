package com.stan.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
        List<User> users = List.of(
                new User(UUID.randomUUID(), "James"),
                new User(UUID.randomUUID(), "Jamila"));
        when(userDao.getUsers()).thenReturn(users);

        // when
        List<User> actual = underTest.getUsers();

        // then
        int expectedUserCount = 2;
        assertThat(actual).hasSize(expectedUserCount).containsAll(users);
    }

    @Test
    void canGetUserByExistingId() {
        // given
        UUID uuid = UUID.randomUUID();
        User james = new User(uuid, "James");
        when(userDao.getUsers()).thenReturn(List.of(
                james,
                        new User(UUID.randomUUID(), "Jamila")));

        // when
        User actual = underTest.getUserById(uuid);

        // then
        assertThat(actual).isEqualTo(james);
    }

    @Test
    void willFailToGetUserByInvalidId() {
        String invalidUserId = "sfsdf";
        assertThatThrownBy(() -> underTest.getUserById(UUID.fromString(invalidUserId))).isInstanceOf(IllegalArgumentException.class);
    }
}