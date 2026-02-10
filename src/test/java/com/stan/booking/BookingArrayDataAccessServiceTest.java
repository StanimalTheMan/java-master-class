package com.stan.booking;

import com.stan.car.Car;
import com.stan.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import static com.stan.car.Brand.AUDI;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class BookingArrayDataAccessServiceTest {

    @Mock
    private Clock clock;

    private final ZoneId zoneId = ZoneId.of("Europe/London");
    private final ZonedDateTime fixedZdt
            = ZonedDateTime.of(2025, 1, 1, 0, 0, 0, 0, zoneId);

    private BookingArrayDataAccessService underTest;

    @BeforeEach
    void setUp() {
        underTest = new BookingArrayDataAccessService(
            clock
        );
    }

    @Test
    void canGetBookings() {
        // when
        var bookings = underTest.getBookings();
        // then
        assertThat(bookings).isEmpty();
    }

    @Test
    void getCurBookingIdx() {
        // when
        var bookingIdx = underTest.getCurBookingIdx();
        // then
        assertThat(bookingIdx).isEqualTo(0);
    }

    @Test
    void canCreateBooking() {
        // given
        Car car1234 = new Car("1234", new BigDecimal("688.00"), AUDI, false);
        User james = new User(UUID.randomUUID(), "James");
        given(clock.getZone()).willReturn(zoneId);
        given(clock.instant()).willReturn(fixedZdt.toInstant());

        // when
        int bookingsCount = underTest.getCurBookingIdx();
        Booking newBooking = underTest.createBooking(car1234, james);

        // then
        int newBookingsCount = underTest.getCurBookingIdx();
        List<Booking> newBookings = underTest.getBookings();
        assertThat(newBookings).contains(newBooking);
        assertThat(newBookingsCount).isEqualTo(bookingsCount + 1);
    }
}