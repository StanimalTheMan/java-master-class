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

    public Car[] getAvailableCars(boolean isElectric) {
        Booking[] bookings = bookingDao.getBookings();
        Car[] cars;
        if (isElectric) {
            cars = getElectricCars();
        } else {
            cars = getCars();
        }
        Car[] availableCars = getCars(isElectric, cars, bookings);
        int curAvailableCarsIdx = 0;
        for (Car car : cars) {
            boolean isCarAvailable = isIsCarAvailable(isElectric, car, bookings);
            if (isCarAvailable) {
                availableCars[curAvailableCarsIdx] = car;
                curAvailableCarsIdx++;
            }
        }

        return availableCars;
    }

    private static Car[] getCars(boolean isElectric, Car[] cars, Booking[] bookings) {
        int availableCarsCount = cars.length;
        for (Car car : cars) {
            for (Booking booking : bookings) {
                if (isElectric) {
                    if (booking != null && booking.getCar().getRegNumber() == car.getRegNumber() && car.isElectric()) {
                        availableCarsCount--;
                    }
                } else {
                    if (booking != null && booking.getCar().getRegNumber() == car.getRegNumber()) {
                        availableCarsCount--;
                    }
                }
            }
        }

        Car[] availableCars = new Car[availableCarsCount];
        return availableCars;
    }

    private static boolean isIsCarAvailable(boolean isElectric, Car car, Booking[] bookings) {
        boolean isCarAvailable = true;
        for (Booking booking : bookings) {
            if (isElectric) {
                if (booking != null && booking.getCar().getRegNumber() == car.getRegNumber() && car.isElectric()) {
                    isCarAvailable = false;
                }
            } else {
                if (booking != null && booking.getCar().getRegNumber() == car.getRegNumber()) {
                    isCarAvailable = false;
                }
            }
        }
        return isCarAvailable;
    }

    public Car[] getElectricCars() {
        return this.carDao.getElectricCars();
    }
}
