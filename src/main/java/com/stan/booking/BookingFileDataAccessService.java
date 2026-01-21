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

    {
     bookings = new Booking[capacity];
     int bookingsCount = 0;
     try {
        file = createFile("src/main/java/com/stan/bookings.txt");
         if (!file.exists()) {
             file.createNewFile();
         }
        Scanner scanner = new Scanner(file);
        while (scanner.hasNext()) {
            String serializedBooking = scanner.nextLine();
            System.out.println(serializedBooking);
            Booking booking = (Booking)Serializer.fromString(serializedBooking);
            bookings[bookingsCount] = booking;
            bookingsCount++;
        }
     } catch (FileNotFoundException e) {
         System.out.println(e.getMessage());
         throw new RuntimeException(e);
     } catch (IOException e) {
         System.out.println("❌ Could not deserialize booking");
     } catch (ClassNotFoundException e) {
         throw new RuntimeException(e);
     }
        curBookingIdx = bookings.length;
    }

    @Override
    public Booking[] getBookings() {
        return new Booking[0];
    }

    @Override
    public int getCurBookingIdx() {
        return 0;
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
        try {
            FileWriter fileWriter = new FileWriter(file, true);
            PrintWriter writer = new PrintWriter(fileWriter);
            writer.println(text);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
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
