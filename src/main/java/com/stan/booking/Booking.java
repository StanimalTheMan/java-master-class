package com.stan.booking;

import com.stan.car.Car;
import com.stan.user.User;

import java.time.LocalDateTime;
import java.util.UUID;

public class Booking {
    private UUID bookingId;
    private Car car;
    private User user;
    private LocalDateTime bookingTime;

    public Booking(UUID bookingId, Car car, User user, LocalDateTime bookingTime) {
        this.bookingId = bookingId;
        this.car = car;
        this.user = user;
        this.bookingTime = bookingTime;
    }

    public UUID getBookingId() {
        return bookingId;
    }

    public void setBookingId(UUID bookingId) {
        this.bookingId = bookingId;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingId=" + bookingId +
                ", car=" + car +
                ", user=" + user +
                ", bookingTime=" + bookingTime +
                '}';
    }
}
