package com.stan.booking;

import com.stan.car.Car;
import com.stan.user.User;

import java.time.LocalDateTime;
import java.util.UUID;

public interface BookingDao {
    Booking[] getBookings();
    int getCurBookingIdx();
    Booking createBooking(Car car, User user);

}
