package com.stan.booking;

import com.stan.car.Car;
import com.stan.user.User;

import java.time.LocalDateTime;
import java.util.UUID;

public class BookingDao {
    private static int capacity = 100;

    private static Booking[] bookings = new Booking[capacity] ;
    private static int curBookingIdx = 0;

    public Booking[] getBookings() {
        return bookings;
    }

    public int getCurBookingIdx() {
        return curBookingIdx;
    }

    public Booking createBooking(Car car, User user) {
        if (curBookingIdx >= capacity) {
            // expand capacity when full
            // copy over existing bookings
            capacity *= 2;
            Booking[] newBookings = new Booking[capacity];
            for (int i = 0; i < bookings.length; i++) {
                newBookings[i] = bookings[i];
            }
            bookings = newBookings;
        }
        Booking booking = new Booking(UUID.randomUUID(), car, user, LocalDateTime.now(), false);
        bookings[curBookingIdx] = booking;
        curBookingIdx++;
        return booking;
    }
}
