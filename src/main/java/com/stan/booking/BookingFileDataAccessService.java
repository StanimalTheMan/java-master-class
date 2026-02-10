package com.stan.booking;

import com.stan.car.Car;
import com.stan.user.User;
import com.stan.utilities.Serializer;

import java.io.*;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class BookingFileDataAccessService implements BookingDao {
    private final List<Booking> bookings = new ArrayList<>();
    private final File file;

    private Clock clock;

    public BookingFileDataAccessService(File file, Clock clock) {
        this.file = file;
        this.clock = clock;
        ensureFileExists();
        loadBookings();
    }

    private void ensureFileExists() {
        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
        } catch (IOException e) {
            throw new RuntimeException("❌ Could not create bookings file", e);
        }
    }

    private void loadBookings() {
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String serializedBooking = scanner.nextLine();
                if (serializedBooking.isEmpty()) {
                    continue;
                }
                Booking booking = (Booking)Serializer.fromString(serializedBooking);
                bookings.add(booking);
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("❌ Could not load bookings from file", e);
        }
    }

    @Override
    public List<Booking> getBookings() {
        return bookings;
    }

    @Override
    public int getCurBookingIdx() {
        return bookings.size();
    }

    @Override
    public Booking createBooking(Car car, User user) {
        Booking booking = new Booking(UUID.randomUUID(), car, user, LocalDateTime.now(clock), false);
        // TODO: this logic breaks because every time i rerun app, available cars are reinitialized.  so this only works if i currently add multiple bookings in same run?
        bookings.add(booking);
        try {
            String bookingString = Serializer.toString(booking);
            writeToFile(file, bookingString);
        } catch (IOException e) {
            System.out.println("❌ Could not serialize booking");
        }
        return booking;
    }

    private void writeToFile(File file, String text) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(file, true))) {
            writer.println(text);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static File createFile(String path) {
        try {
            File file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
            }
            return file;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new IllegalStateException(e);
        }
    }
}
