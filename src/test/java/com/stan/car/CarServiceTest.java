package com.stan.car;

import com.stan.booking.Booking;
import com.stan.booking.BookingDao;
import com.stan.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.stan.car.Brand.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {
    @Mock
    private CarDao carDao;

    @Mock
    private BookingDao bookingDao;

    @InjectMocks
    private CarService underTest;

    @Test
    void canGetCars() {
        // given
        when(carDao.getCars()).thenReturn(List.of(  new Car("1234", new BigDecimal("89.00"), TESLA,true),
                new Car("5678", new BigDecimal("50.00"), AUDI, false),
                new Car("5678", new BigDecimal("77.00"), MERCEDES, false)));

        // when
        List<Car> cars = underTest.getCars();

        // then
        assertThat(cars).hasSize(3);
    }

    @Test
    void canGetAvailableElectricCars() {
        // given
        Car car1234 = new Car("1234", new BigDecimal("89.00"), TESLA,true);
        User james = new User(UUID.randomUUID(), "James");
        when(bookingDao.getBookings()).thenReturn(List.of(
                new Booking(UUID.randomUUID(), car1234, james, LocalDateTime.now(), false)
        ));
        List<Car> electricCars = List.of(
                new Car("1234", new BigDecimal("89.00"), TESLA, true));
        when(carDao.getElectricCars()).thenReturn(electricCars);

        // when
        List<Car> cars = underTest.getAvailableCars(true);

        // then
        assertThat(cars).hasSize(1).containsAll(electricCars);
    }

    @Test
    void canGetAllAvailableCars() {
        // given
        Car car1234 = new Car("1234", new BigDecimal("89.00"), TESLA,true);
        Car car5678 = new Car("5678", new BigDecimal("67.00"), MERCEDES, false);
        User james = new User(UUID.randomUUID(), "James");
        User jamila = new User(UUID.randomUUID(), "Jamila");
        when(bookingDao.getBookings()).thenReturn(List.of(
                new Booking(UUID.randomUUID(), car1234, james, LocalDateTime.now(), false),
                new Booking(UUID.randomUUID(), car5678, jamila, LocalDateTime.now(), false)
        ));

        Car availableCar = new Car("9999", new BigDecimal("77.00"), AUDI, false);
        when(carDao.getCars()).thenReturn(List.of(
                car1234,
                car5678,
                availableCar));

        // when
        List<Car> cars = underTest.getAvailableCars(false);

        // then
        assertThat(cars).hasSize(1).contains(availableCar);
    }

    @Test
    void canGetElectricCars() {
        // given
        List<Car> electricCars = List.of(
                new Car("1234", new BigDecimal("89.00"), TESLA, true));
        when(carDao.getElectricCars()).thenReturn(
                electricCars);

        // when
        List<Car> actual = underTest.getElectricCars();

        // then
        assertThat(actual).hasSize(1).containsAll(electricCars);
    }
}