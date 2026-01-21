package com.stan.booking;

import com.stan.car.Car;
import com.stan.user.User;

public class BookingFileDataAccessService implements BookingDao {

    private static Booking[] bookings;
    private static int curBookingIdx = 0;

    {

    }

    @Override
    public Booking[] getBookings() {
        return new Booking[0];
    }

    @Override
    public int getCurBookingIdx() {
        return 0;
    }

    @Override
    public Booking createBooking(Car car, User user) {
        return null;
    }
}
