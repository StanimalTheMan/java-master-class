package com.stan.car;

import com.stan.booking.BookingArrayDataAccessService;
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

import static com.stan.car.Brand.TESLA;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {
    public static final int EXPECTED_INITIAL_CARS_COUNT = 3;
    @Mock
    public CarDao carDao;
    @InjectMocks
    private CarService underTest;

    @Test
    void canGetCars() {
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
    void canGetAvailableCars(boolean isElectric) {
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
    void canGetElectricCars() {
        // given
        when(underTest.getElectricCars()).thenReturn(new ArrayList<>(
                Arrays.asList(
                        new Car("1234", new BigDecimal("89.00"), TESLA,true))));
        // when
        List<Car> electricCars = underTest.getElectricCars();
        // then
        assertThat(electricCars.size()).isEqualTo(1);
        assertThat(electricCars.get(0).isElectric()).isTrue();
    }
}