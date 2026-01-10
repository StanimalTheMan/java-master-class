package com.stan;
// TODO 1. create a new branch called initial-implementation
// TODO 2. create a package with your name. i.e com.franco and move this file inside the new package
// TODO 3. implement https://amigoscode.com/learn/java-cli-build/lectures/3a83ecf3-e837-4ae5-85a8-f8ae3f60f7f5

import com.stan.car.Car;
import com.stan.car.CarDao;
import com.stan.car.CarService;
import com.stan.user.User;
import com.stan.user.UserDao;
import com.stan.user.UserService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CarDao carDao = new CarDao();
        CarService carService = new CarService(carDao);
        UserDao userDao = new UserDao();
        UserService userService = new UserService(userDao);
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
                    case "4":
                        Car[] cars = carService.getCars();
                        // probably can handle better with DTOs
                        for (Car car : cars) {
                            System.out.println(car);
                        }
                        break;
                    case "5":
                        Car[] electricCars = carService.getElectricCars();
                        for (Car car : electricCars) {
                            System.out.println(car);
                        }
                        break;
                    case "6":
                        User[] users = userService.getUsers();
                        for (User user : users) {
                            System.out.println(user);
                        }
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
