package com.stan.booking;

import com.stan.car.Car;
import com.stan.user.User;

import java.time.LocalDateTime;
import java.util.UUID;

public class BookingDao {
    private static final int MAX_BOOKINGS = 100;

    private static Booking[] bookings = new Booking[MAX_BOOKINGS] ;
    private int curBookingIdx = 0;

    public Booking[] getBookings() {
        return bookings;
    }

    public int getCurBookingIdx() {
        return curBookingIdx;
    }

    public Booking createBooking(Car car, User user) {
        Booking booking = new Booking(UUID.randomUUID(), car, user, LocalDateTime.now());
        bookings[curBookingIdx] = booking;
        curBookingIdx++;
        return booking;
    }
}
