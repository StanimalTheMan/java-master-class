package com.stan.booking;

import com.stan.car.Car;
import com.stan.user.User;

import java.util.List;

public interface BookingDao {
    List<Booking> getBookings();
    int getCurBookingIdx();
    Booking createBooking(Car car, User user);

}
