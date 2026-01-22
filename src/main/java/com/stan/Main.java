package com.stan;
// TODO 1. create a new branch called initial-implementation
// TODO 2. create a package with your name. i.e com.franco and move this file inside the new package
// TODO 3. implement https://amigoscode.com/learn/java-cli-build/lectures/3a83ecf3-e837-4ae5-85a8-f8ae3f60f7f5

import com.stan.booking.*;
import com.stan.car.Car;
import com.stan.car.CarDao;
import com.stan.car.CarService;
import com.stan.user.User;
import com.stan.user.UserDao;
import com.stan.user.UserService;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        CarDao carDao = new CarDao();
        BookingDao bookingDao = new BookingArrayDataAccessService();
        CarService carService = new CarService(carDao, bookingDao);

        UserDao userDao = new UserDao();
        UserService userService = new UserService(userDao);


        BookingService bookingService = new BookingService(bookingDao, userDao, carService);

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println();
                displayMenu();
                System.out.println();
                String userInput = scanner.nextLine();
                try {
                    int userInputNumber = Integer.parseInt(userInput);
                    if (userInputNumber < 1 || userInputNumber > 7) {
                        System.out.println(String.format("%d is not a valid option ❌", userInputNumber));
                        continue;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid user input: " + userInput);
                }
                switch (userInput) {
                    case "1":
                        // Initially display all cars
                        List<Car> cars = carService.getAvailableCars(false);
                        for (Car car : cars) {
                            System.out.println(car);
                        }
                        System.out.println("➡️ select car reg number");
                        String carRegNumber = scanner.nextLine();
                        // Then display all users
                        List<User> users = userService.getUsers();
                        for (User user : users) {
                            System.out.println(user);
                        }
                        System.out.println("➡️ select user id");
                        String userId = scanner.nextLine();
                        Booking booking = bookingService.createBooking(carRegNumber, UUID.fromString(userId));
                        break;
                    case "2":
                        // Initially display all users
                        users = userService.getUsers();
                        for (User user : users) {
                            System.out.println(user);
                        }
                        System.out.println("➡️ select user id");
                        userId = scanner.nextLine();
                        Car[] userCars = bookingService.getCarsByUserId(UUID.fromString(userId));
                        User user = userService.getUserById(UUID.fromString(userId));
                        if (userCars.length == 0) {
                            System.out.println("❌ user " + user  + " has no cars booked");
                        } else {
                            for (Car car : userCars) {
                                System.out.println(car);
                            }
                        }
                        break;
                    case "3":
                        Booking[] allBookings = bookingService.getBookings();
                        int bookingNumber = bookingService.getCurrentBookingNumber();
                        if (bookingNumber == 0) {
                            System.out.println("No bookings available 😕");
                        } else {
                            for (Booking currBooking : allBookings) {
                                if (currBooking != null) {
                                    System.out.println(currBooking);
                                }
                            }
                        }
                        break;
                    case "4":
                        cars = carService.getAvailableCars(false);
                        if (cars.size() == 0) {
                            System.out.println("❌ No cars available for renting");
                        } else {
                            // probably can handle better with DTOs
                            for (Car car : cars) {
                                System.out.println(car);
                            }
                        }
                        break;
                    case "5":
                        List<Car> electricCars = carService.getAvailableCars(true);
                        if (electricCars.size() == 0) {
                            System.out.println("❌ No electric cars available for renting");
                        } else {
                            for (Car car : electricCars) {
                                System.out.println(car);
                            }
                        }
                        break;
                    case "6":
                        users = userService.getUsers();
                        for (User foundUser : users) {
                            System.out.println(foundUser);
                        }
                        break;
                    case "7":
                        return;
                }
            }
        }
    }

    public static void displayMenu() {
        System.out.println("1️⃣ - Book Car");
        System.out.println("2️⃣ - View All User Booked Cars");
        System.out.println("3️⃣ - View All Bookings");
        System.out.println("4️⃣ - View Available Cars");
        System.out.println("5️⃣ - View Available Electric Cars");
        System.out.println("6️⃣ - View all users");
        System.out.println("7️⃣ - Exit");
    }
}
