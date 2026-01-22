package com.stan.car;

import com.stan.booking.Booking;
import com.stan.booking.BookingDao;

import java.util.ArrayList;
import java.util.List;

public class CarService {
    private CarDao carDao;
    private BookingDao bookingDao;

    public CarService(CarDao carDao, BookingDao bookingDao) {
        this.carDao = carDao;
        this.bookingDao = bookingDao;
    }

    public List<Car> getCars() {
        return this.carDao.getCars();
    }

    public List<Car> getAvailableCars(boolean isElectric) {
        Booking[] bookings = bookingDao.getBookings();
        List<Car> cars;
        if (isElectric) {
            cars = getElectricCars();
        } else {
            cars = getCars();
        }
        List<Car> availableCars = getCars(isElectric, cars, bookings);

        return availableCars;
    }

    private static List<Car> getCars(boolean isElectric, List<Car> cars, Booking[] bookings) {
        List<Car> availableCars = new ArrayList<>();
        for (Car car : cars) {
            for (Booking booking : bookings) {
                if (isElectric) {
                    if (booking != null && booking.getCar().getRegNumber() != car.getRegNumber() && car.isElectric()) {
                        availableCars.add(car);
                    }
                } else {
                    if (booking != null && booking.getCar().getRegNumber() != car.getRegNumber()) {
                        availableCars.add(car);
                    }
                }
            }
        }

        return availableCars;
    }

    public List<Car> getElectricCars() {
        return this.carDao.getElectricCars();
    }
}
