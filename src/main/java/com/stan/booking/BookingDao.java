package com.stan.booking;

import com.stan.car.Car;
import com.stan.user.User;

public interface BookingDao {
    Booking[] getBookings();
    int getCurBookingIdx();
    Booking createBooking(Car car, User user);

}
