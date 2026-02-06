package com.stan.booking;

import com.stan.car.Car;
import com.stan.car.CarDao;
import com.stan.car.CarService;
import com.stan.user.User;
import com.stan.user.UserDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.stan.car.Brand.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {
    @Mock
    private Clock clock;
    private final ZoneId zoneId = ZoneId.of("America/New_York");
    private final ZonedDateTime fixedZdt
            = ZonedDateTime.of(2025, 1, 1, 0, 0, 0, 0, zoneId);

    @Mock
    private CarDao carDao;

    @Mock
    private BookingDao bookingDao;

    @Mock
    private UserDao userDao;

    @Mock
    private CarService carService;

    @Captor
    private ArgumentCaptor<Car> carArgumentCaptor;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @InjectMocks
    private BookingService underTest;

    @Test
    void canGetBookings() {
        // given
        Car car1234 = new Car("1234", new BigDecimal("89.00"), TESLA,true);
        User james = new User(UUID.fromString("8ca51d2b-aaaf-4bf2-834a-e02964e10fc3"), "James");
        given(bookingDao.getBookings()).willReturn(new ArrayList<>(
                Arrays.asList(new Booking(
                        UUID.randomUUID(),
                        car1234,
                        james,
                        LocalDateTime.now(),
                        false))));
        // when
        List<Booking> bookings = underTest.getBookings();
        // then
        verify(bookingDao).getBookings();
        assertThat(bookings.isEmpty()).isFalse();
        assertThat(bookings.size()).isEqualTo(1);
    }

    @ParameterizedTest
    @CsvSource({
            "8ca51d2b-aaaf-4bf2-834a-e02964e10fc3",
    })
    void canGetBookingsByUserId(UUID userId) {
        // given
        Car car1234 = new Car("1234", new BigDecimal("89.00"), TESLA,true);
        User james = new User(UUID.fromString("8ca51d2b-aaaf-4bf2-834a-e02964e10fc3"), "James");
        given(bookingDao.getBookings()).willReturn(new ArrayList<>(
                Arrays.asList(new Booking(
                        UUID.randomUUID(),
                        car1234,
                        james,
                        LocalDateTime.now(),
                        false))));
        // when
        List<Booking> bookingsByUserId = underTest.getBookingsByUserId(userId);
        // then
        assertThat(bookingsByUserId.isEmpty()).isFalse();
        assertThat(bookingsByUserId.get(0).getUser().getUserId()).isEqualTo(userId);
    }

    @ParameterizedTest
    @CsvSource({
            "sfsdf",
            "1",
    })
    void willFailToGetBookingsByInvalidUserId(String userId) {
        assertThatThrownBy(() -> underTest.getBookingsByUserId(UUID.fromString(userId))).isInstanceOf(IllegalArgumentException.class);

    }

    @Test
    void canGetCurrentBookingNumber() {
        // given
        given(bookingDao.getCurBookingIdx()).willReturn(2);
        // when
        int currentBookingNumber = underTest.getCurrentBookingNumber();
        // then
        verify(bookingDao).getCurBookingIdx();
        assertThat(currentBookingNumber).isEqualTo(2);
    }

    @ParameterizedTest
    @CsvSource({
            "8ca51d2b-aaaf-4bf2-834a-e02964e10fc3",
            "b10d126a-3608-4980-9f9c-aa179f5cebc3",
    })
    void canGetCarsByUserId(UUID userId) {
        // given
        // when
        List<Car> carsByUserId = underTest.getCarsByUserId(userId);
        // then
        assertThat(carsByUserId.size()).isEqualTo(0);
    }

    @ParameterizedTest
    @CsvSource({
            "sfsdf",
            "1",
    })
    void willFailToGetCarsByInvalidUserId(String userId) {
        assertThatThrownBy(() -> underTest.getCarsByUserId(UUID.fromString(userId))).isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @CsvSource({
            "1234, 8ca51d2b-aaaf-4bf2-834a-e02964e10fc3",
    })
    void canCreateBooking(String carRegNumber, UUID userId) {
        // given
        given(clock.getZone()).willReturn(zoneId);
        given(clock.instant()).willReturn(fixedZdt.toInstant());
        LocalDateTime currentTime = LocalDateTime.now(clock);
        Car car1234 = new Car("1234", new BigDecimal("89.00"), TESLA,true);
        given(carService.getAvailableCars(false)).willReturn(new ArrayList<>(
                Arrays.asList(
                        car1234,
                        new Car("5678", new BigDecimal("50.00"), AUDI, false),
                        new Car("5678", new BigDecimal("77.00"), MERCEDES, false))));
        User james = new User(UUID.fromString("8ca51d2b-aaaf-4bf2-834a-e02964e10fc3"), "James");
        given(userDao.getUsers()).willReturn(
                new ArrayList<>(
                        Arrays.asList(
                                james,
                                new User(UUID.fromString("b10d126a-3608-4980-9f9c-aa179f5cebc3"), "Jamila"))));
        given(bookingDao.getBookings()).willReturn(new ArrayList<>());
        Booking returnedBooking = new Booking(
                UUID.randomUUID(),
                car1234,
                james,
                LocalDateTime.now(),
                false
        );
        given(bookingDao.createBooking(any(Car.class), any(User.class)))
                .willReturn(returnedBooking);
        // when
        int bookingsCount = underTest.getBookings().size();
        Booking newBooking = underTest.createBooking(carRegNumber, userId);
        // then
        verify(bookingDao).createBooking(carArgumentCaptor.capture(), userArgumentCaptor.capture());
        Car carArgumentCaptorValue = carArgumentCaptor.getValue();
        assertThat(carArgumentCaptorValue.getRegNumber()).isEqualTo(carRegNumber);
        User userArgumentCaptorValue = userArgumentCaptor.getValue();
        assertThat(userArgumentCaptorValue.getUserId()).isEqualTo(userId);
        assertThat(newBooking.getCar().getRegNumber()).isEqualTo(carRegNumber);
        assertThat(newBooking.getUser().getUserId()).isEqualTo(userId);
        assertThat(newBooking.getBookingTime()).isAfter(currentTime);
    }
}