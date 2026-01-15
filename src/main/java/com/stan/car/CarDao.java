package com.stan.car;

import java.math.BigDecimal;

import static com.stan.car.Brand.*;

public class CarDao {

    private static final Car[] cars;

    static {
        cars = new Car[]{
                new Car("1234", new BigDecimal("89.00"), TESLA,true),
                new Car("5678", new BigDecimal("50.00"), AUDI, false),
                new Car("5678", new BigDecimal("77.00"), MERCEDES, false)
        };
    }

    public Car[] getCars() {
        return cars;
    }

    public Car[] getElectricCars() {
        // TODO: handle with streams to better filter for electric cars
        // Current implementation
        // 1. naively iterate through cars, check isElectric property and if it's true, increment count
        // 2. create new array of length of that count
        // 3. iterate again, updating each index with electric car
        int electricCarCount = 0;
        for (Car car : cars) {
            if (car.isElectric()) {
                electricCarCount++;
            }
        }

        Car[] electricCars = new Car[electricCarCount];
        int curElectricCarIdx = 0;
        for (Car car : cars) {
            if (car.isElectric()) {
                electricCars[curElectricCarIdx] = car;
                curElectricCarIdx++;
            }
        }

        return electricCars;
    }
}
