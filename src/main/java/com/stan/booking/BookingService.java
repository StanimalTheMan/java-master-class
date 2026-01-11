package com.stan.booking;

import com.stan.car.Car;
import com.stan.car.CarDao;
import com.stan.user.User;
import com.stan.user.UserDao;

public class BookingService {
    private BookingDao bookingDao;
    private UserDao userDao;
    private CarDao carDao;

    public BookingService(BookingDao bookingDao, UserDao userDao, CarDao carDao) {
        this.bookingDao = bookingDao;
        this.userDao = userDao;
        this.carDao = carDao;
    }

    public Booking[] getBookings() {
        return this.bookingDao.getBookings();
    }

    public int getCurrentBookingNumber() {
        return this.bookingDao.getCurBookingIdx();
    }


    public void getCarsByUserId(String userId) {

    }

    public void createBooking(String carRegNumber, String userId) {
        // Do I need to robustly handle invalid car reg number and/or user ids for now?
        Car[] cars = carDao.getCars();
        boolean isCarFound = false;
        Car foundCar = null;
        for (Car car : cars) {
            if (car.getRegNumber().toString().equals(carRegNumber)) {
                isCarFound = true;
                foundCar = car;
            }
        }
        if (!isCarFound) {
            System.out.println("❌ Unable to book car that doesn't exist");
            return;
        }

        User[] users = userDao.getUsers();
        boolean isUserFound = false;
        User foundUser = null;
        for (User user : users) {
            if (user.getUserId().toString().equals(userId)) {
                isUserFound = true;
                foundUser = user;
            }
        }
        if (!isUserFound) {
            System.out.println("❌ Unable to book car for user that doesn't exist");
            return;
        }

        Booking booking = bookingDao.createBooking(foundCar, foundUser);
        System.out.println("🎉 Successfully boooked car with reg number " + carRegNumber + " for user " + foundUser);
        System.out.println(String.format("Booking ref: %s", booking.getBookingId().toString()));
    }
}
