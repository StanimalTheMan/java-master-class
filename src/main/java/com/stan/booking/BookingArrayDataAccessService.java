package com.stan.booking;

import com.stan.car.Car;
import com.stan.user.User;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BookingArrayDataAccessService implements BookingDao {
    private List<Booking> bookings = new ArrayList<>();

    private Clock clock;

    public BookingArrayDataAccessService(Clock clock) {
        this.clock = clock;
    }


    @Override
    public List<Booking> getBookings() {
        return bookings;
    }

    @Override
    public int getCurBookingIdx() {
        return bookings.size();
    }

    @Override
    public Booking createBooking(Car car, User user) {
        Booking booking = new Booking(UUID.randomUUID(), car, user, LocalDateTime.now(clock), false);
        bookings.add(booking);
        return booking;
    }
}
