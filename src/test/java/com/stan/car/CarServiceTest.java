package com.stan.car;

import com.stan.booking.BookingDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.stan.car.Brand.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarServiceTest { ;
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

    @ParameterizedTest
    @CsvSource({
            "true",
            "false"
    })
    void canGetAvailableCars(boolean isElectric) {
        // given
        when(bookingDao.getBookings()).thenReturn(new ArrayList<>());
        if (isElectric) {
            when(carDao.getElectricCars()).thenReturn(Arrays.asList(
                    new Car("1234", new BigDecimal("89.00"), TESLA,true)));
        } else {
            when(carDao.getCars()).thenReturn(Arrays.asList(
                    new Car("1234", new BigDecimal("89.00"), TESLA,true),
                    new Car("5678", new BigDecimal("50.00"), AUDI, false),
                    new Car("5678", new BigDecimal("77.00"), MERCEDES, false)));
        }
        // when
        List<Car> cars = underTest.getAvailableCars(isElectric);
        // then
        verify(bookingDao).getBookings();
        if (isElectric) {
            verify(carDao).getElectricCars();
            assertThat(cars.size()).isEqualTo(1);
        } else {
            verify(carDao).getCars();
            assertThat(cars.size()).isEqualTo(3);
        }
    }

    @Test
    void canGetElectricCars() {
        // given
        when(carDao.getElectricCars()).thenReturn(
                List.of(
                        new Car("1234", new BigDecimal("89.00"), TESLA,true)));
        // when
        List<Car> electricCars = underTest.getElectricCars();
        // then
        assertThat(electricCars).hasSize(1);
        assertThat(electricCars.get(0).isElectric()).isTrue();
    }
}