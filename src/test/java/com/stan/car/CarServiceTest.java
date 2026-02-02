package com.stan.car;

import com.stan.booking.BookingArrayDataAccessService;
import com.stan.booking.BookingDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CarServiceTest {
    public static final int EXPECTED_INITIAL_CARS_COUNT = 3;
    private final CarService underTest =
            new CarService(new CarDao(), new BookingArrayDataAccessService());

    @Test
    void getCars() {
        // when
        List<Car> cars = underTest.getCars();
        // then
        assertThat(cars.size()).isEqualTo(EXPECTED_INITIAL_CARS_COUNT);
    }

    @ParameterizedTest
    @CsvSource({
            "true",
            "false"
    })
    void getAvailableCars(boolean isElectric) {
        // when
        List<Car> cars = underTest.getAvailableCars(isElectric);
        // then
        if (isElectric) {
            assertThat(cars.size()).isEqualTo(1);
        } else {
            assertThat(cars.size()).isEqualTo(3);
        }
    }

    @Test
    void getElectricCars() {
        // when
        List<Car> electricCars = underTest.getElectricCars();
        // then
        assertThat(electricCars.size()).isEqualTo(1);
        assertThat(electricCars.get(0).isElectric()).isTrue();
    }
}