package com.stan.booking;

import com.stan.car.Car;
import com.stan.user.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BookingArrayDataAccessService implements BookingDao {
    private static List<Booking> bookings = new ArrayList<>();
    private static int curBookingIdx = 0;

    @Override
    public List<Booking> getBookings() {
        return bookings;
    }

    @Override
    public int getCurBookingIdx() {
        return curBookingIdx;
    }

    @Override
    public Booking createBooking(Car car, User user) {
        Booking booking = new Booking(UUID.randomUUID(), car, user, LocalDateTime.now(), false);
        bookings.add(booking);
        curBookingIdx++;
        return booking;
    }
}
