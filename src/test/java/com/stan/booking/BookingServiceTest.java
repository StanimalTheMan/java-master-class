package com.stan.booking;

import com.stan.car.Car;
import com.stan.car.CarDao;
import com.stan.car.CarService;
import com.stan.user.UserDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BookingServiceTest {
    private CarDao carDao;
    private BookingDao bookingDao;
    private UserDao userDao;
    private CarService carService;
    private BookingService underTest;

    @BeforeEach
    void setUp() {
        carDao = new CarDao();
        bookingDao = new BookingArrayDataAccessService();
        userDao = new UserDao();
        carService = new CarService(carDao, bookingDao);
        underTest =
                new BookingService(bookingDao, userDao, carService);
    }

    @Test
    void canGetBookings() {
        // when
        List<Booking> bookings = underTest.getBookings();
        // then
        assertThat(bookings.isEmpty()).isTrue();
    }

    @ParameterizedTest
    @CsvSource({
            "8ca51d2b-aaaf-4bf2-834a-e02964e10fc3",
            "b10d126a-3608-4980-9f9c-aa179f5cebc3",
    })
    void canGetBookingsByUserId(UUID userId) {
        // when
        List<Booking> bookingsByUserId = underTest.getBookingsByUserId(userId);
        // then
        assertThat(bookingsByUserId.isEmpty()).isTrue();
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
        // when
        int currentBookingNumber = underTest.getCurrentBookingNumber();
        // then
        assertThat(currentBookingNumber).isEqualTo(0);
    }

    @ParameterizedTest
    @CsvSource({
            "8ca51d2b-aaaf-4bf2-834a-e02964e10fc3",
            "b10d126a-3608-4980-9f9c-aa179f5cebc3",
    })
    void canGetCarsByUserId(UUID userId) {
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
        // when
        int bookingsCount = underTest.getBookings().size();
        Booking newBooking = underTest.createBooking(carRegNumber, userId);
        // then
        assertThat(underTest.getBookings().size()).isEqualTo(bookingsCount + 1);
        assertThat(newBooking.getCar().getRegNumber()).isEqualTo(carRegNumber);
        assertThat(newBooking.getUser().getUserId()).isEqualTo(userId);
    }
}