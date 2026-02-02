package com.stan.booking;

import com.stan.car.Car;
import com.stan.car.CarDao;
import com.stan.car.CarService;
import com.stan.user.UserDao;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookingServiceTest {
    private final CarDao carDao = new CarDao();
    private final BookingDao bookingDao = new BookingArrayDataAccessService();
    private final UserDao userDao = new UserDao();
    private final CarService carService = new CarService(carDao, bookingDao);
    private final BookingService underTest =
            new BookingService(bookingDao, userDao, carService);

    @Test
    void getBookings() {
        // given
        // when
        // then
    }

    @Test
    void getBookingsByUserId() {
        // given
        // when
        // then
    }

    @Test
    void getCurrentBookingNumber() {
        // given
        // when
        // then
    }

    @Test
    void getCarsByUserId() {
        // given
        // when
        // then
    }

    @Test
    void createBooking() {
        // given
        // when
        // then
    }
}