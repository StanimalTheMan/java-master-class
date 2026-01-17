package com.stan.booking;

import com.stan.car.Car;
import com.stan.car.CarService;
import com.stan.user.User;
import com.stan.user.UserDao;

import java.util.UUID;

public class BookingService {
    private BookingDao bookingDao;
    private UserDao userDao;
    private CarService carService;

    public BookingService(BookingDao bookingDao, UserDao userDao, CarService carService) {
        this.bookingDao = bookingDao;
        this.userDao = userDao;
        this.carService = carService;
    }

    public Booking[] getBookings() {
        return this.bookingDao.getBookings();
    }

    public Booking[] getBookingsByUserId(UUID userId) {
        // TODO: use lists but will iterate twice
        // 1. in first iteration, get count of bookings that are for user
        int userBookingsCount = 0;
        Booking[] bookings = getBookings();

        for (Booking booking : bookings) {
            if (booking.getUser().getUserId().equals(userId)) {
                userBookingsCount++;
            }
        }
        // 2. create user bookings of length of found count
        Booking[] userBookings = new Booking[userBookingsCount];
        int curUserBookingIdx = 0;
        // 3. iterate second time to populate userBookings
        for (Booking booking : bookings) {
            if (booking.getUser().getUserId().equals(userId)) {
                userBookings[curUserBookingIdx] = booking;
                curUserBookingIdx++;
            }
        }

        return userBookings;
    }

    public int getCurrentBookingNumber() {
        return this.bookingDao.getCurBookingIdx();
    }


    public Car[] getCarsByUserId(UUID userId) {
        if (bookingDao.getCurBookingIdx() == 0) {
            return new Car[0];
        }

        Booking[] userBookings = getBookingsByUserId(userId);
        Car[] userCars = new Car[userBookings.length];
        int curUserCarsIdx = 0;
        for (Booking booking : userBookings) {
            userCars[curUserCarsIdx] = booking.getCar();
        }
        return userCars;
    }

    public Booking createBooking(String carRegNumber, UUID userId) {
        // Do I need to robustly handle invalid car reg number and/or user ids for now?
        Car[] cars = carService.getAvailableCars(false);
        Car foundCar = null;
        for (Car car : cars) {
            if (car.getRegNumber().equals(carRegNumber)) {
                foundCar = car;
            }
        }
        if (foundCar == null) {
            System.out.println("❌ Unable to book car that doesn't exist");
            return null;
        }

        User[] users = userDao.getUsers();
        User foundUser = null;
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                foundUser = user;
            }
        }
        if (foundUser == null) {
            System.out.println("❌ Unable to book car for user that doesn't exist");
            return null;
        }

        Booking booking = bookingDao.createBooking(foundCar, foundUser);
        System.out.println("🎉 Successfully booked car with reg number " + carRegNumber + " for user " + foundUser);
        System.out.println(String.format("Booking ref: %s", booking.getBookingId().toString()));
        return booking;
    }
}
