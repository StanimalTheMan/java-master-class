package com.stan.car;

public class CarService {
    private CarDao carDao;

    public CarService(CarDao carDao) {
        this.carDao = carDao;
    }

    public Car[] getCars() {
        return this.carDao.getCars();
    }
}
