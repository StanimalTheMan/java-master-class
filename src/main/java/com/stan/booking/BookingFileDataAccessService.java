package com.stan.booking;

import com.stan.car.Car;
import com.stan.user.User;
import com.stan.utilities.Serializer;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.UUID;

public class BookingFileDataAccessService implements BookingDao {
    private static int capacity = 100;

    private static Booking[] bookings;
    private static int curBookingIdx;
    private static File file;

    static {
        bookings = new Booking[capacity];
        int bookingsCount = 0;

        file = createFile("src/main/java/com/stan/bookings.dat");
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String serializedBooking = scanner.nextLine();
                if (serializedBooking.isEmpty()) {
                    continue;
                }
                System.out.println(serializedBooking);
                Booking booking = (Booking)Serializer.fromString(serializedBooking);
                if (bookingsCount >= capacity) {
                    capacity *= 2;
                    Booking[] newBookings = new Booking[capacity];
                    System.arraycopy(bookings, 0, newBookings, 0, bookings.length);
                    bookings = newBookings;
                }
                bookings[bookingsCount] = booking;
                bookingsCount++;
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("❌ Could not load bookings from file", e);
        }
        curBookingIdx = bookingsCount;
    }

    @Override
    public Booking[] getBookings() {
        return bookings;
    }

    @Override
    public int getCurBookingIdx() {
        return curBookingIdx;
    }

    @Override
    public Booking createBooking(Car car, User user) {
        if (curBookingIdx >= capacity) {
            // expand capacity when full
            // copy over existing bookings
            capacity *= 2;
            Booking[] newBookings = new Booking[capacity];
            for (int i = 0; i < bookings.length; i++) {
                newBookings[i] = bookings[i];
            }
            bookings = newBookings;
        }
        Booking booking = new Booking(UUID.randomUUID(), car, user, LocalDateTime.now(), false);
        bookings[curBookingIdx] = booking;
        curBookingIdx++;
        try {
            String bookingString = Serializer.toString(booking);
            writeToFile(file, bookingString);
        } catch (IOException e) {
            System.out.println("❌ Could not serialize booking");
        }
        return booking;
    }

    private static void writeToFile(File file, String text) {
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
