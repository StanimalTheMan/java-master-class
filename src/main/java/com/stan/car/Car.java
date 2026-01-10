package com.stan.car;

import java.math.BigDecimal;
import java.util.UUID;

public class Car {
    private UUID regNumber;
    private BigDecimal rentalPricePerDay;
    private Brand brand;
    private boolean isElectric;

    public Car(UUID regNumber, BigDecimal rentalPricePerDay, Brand brand, boolean isElectric) {
        this.regNumber = regNumber;
        this.rentalPricePerDay = rentalPricePerDay;
        this.brand = brand;
        this.isElectric = isElectric;
    }

    @Override
    public String toString() {
        return "Car{" +
                "regNumber=" + regNumber +
                ", rentalPricePerDay=" + rentalPricePerDay +
                ", brand=" + brand +
                ", isElectric=" + isElectric +
                '}';
    }
}

