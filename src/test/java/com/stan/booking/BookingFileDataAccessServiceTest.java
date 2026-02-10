package com.stan.booking;

import com.stan.car.Car;
import com.stan.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.time.Clock;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

import static com.stan.car.Brand.AUDI;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class BookingFileDataAccessServiceTest {

    @Mock
    private Clock clock;

    private final ZoneId zoneId = ZoneId.of("Europe/London");
    private final ZonedDateTime fixedZdt
            = ZonedDateTime.of(2025, 1, 1, 0, 0, 0, 0, zoneId);

    @TempDir
    Path tempDir;

    private BookingFileDataAccessService underTest;

    @BeforeEach
    void setUp() {
        File testFile = tempDir.resolve("bookingsTest.dat").toFile();
        underTest = new BookingFileDataAccessService(testFile, clock);
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
    void createBooking() {
        // given
        Car car1234 = new Car("1234", new BigDecimal("688.00"), AUDI, false);
        User james = new User(UUID.randomUUID(), "James");
        given(clock.getZone()).willReturn(zoneId);
        given(clock.instant()).willReturn(fixedZdt.toInstant());

        // when
        Booking newBooking = underTest.createBooking(car1234, james);

        // then
        assertEquals(1, underTest.getBookings().size());
        assertEquals(newBooking, underTest.getBookings().get(0));
    }
}