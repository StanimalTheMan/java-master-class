package com.stan.car;

import com.stan.booking.Booking;
import com.stan.booking.BookingDao;

public class CarService {
    private CarDao carDao;
    private BookingDao bookingDao;

    public CarService(CarDao carDao, BookingDao bookingDao) {
        this.carDao = carDao;
        this.bookingDao = bookingDao;
    }

    public Car[] getCars() {
        return this.carDao.getCars();
    }

    public Car[] getAvailableCars() {
        Booking[] bookings = bookingDao.getBookings();
        Car[] cars = getCars();
        int availableCarsCount = cars.length;
        for (Car car : cars) {
            for (Booking booking : bookings) {
                if (booking != null && booking.getCar().getRegNumber() == car.getRegNumber()) {
                    availableCarsCount--;
                }
            }
        }

        Car[] availableCars = new Car[availableCarsCount];
        int curAvailableCarsIdx = 0;
        for (Car car : cars) {
            boolean isCarAvailable = true;
            for (Booking booking : bookings) {
                if (booking != null && booking.getCar().getRegNumber() == car.getRegNumber()) {
                    isCarAvailable = false;
                }
            }
            if (isCarAvailable) {
                availableCars[curAvailableCarsIdx] = car;
                curAvailableCarsIdx++;
            }
        }

        return availableCars;
    }

    public Car[] getElectricCars() {
        return this.carDao.getElectricCars();
    }

    public Car[] getAvailableElectricCars() {
        Booking[] bookings = bookingDao.getBookings();
        Car[] electricCars = getElectricCars();
        int availableElectricCarsCount = electricCars.length;
        for (Car car : electricCars) {
            for (Booking booking : bookings) {
                if (booking != null && booking.getCar().getRegNumber() == car.getRegNumber() && car.isElectric()) {
                    availableElectricCarsCount--;
                }
            }
        }

        Car[] availableElectricCars = new Car[availableElectricCarsCount];
        int curAvailableElectricCarsCount = 0;
        for (Car car : electricCars) {
            boolean isCarAvailable = true;
            for (Booking booking : bookings) {
                if (booking != null && booking.getCar().getRegNumber() == car.getRegNumber() && car.isElectric()) {
                    isCarAvailable = false;
                }
            }
            if (isCarAvailable) {
                availableElectricCars[curAvailableElectricCarsCount] = car;
                curAvailableElectricCarsCount++;
            }
        }

        return availableElectricCars;
    }
}
