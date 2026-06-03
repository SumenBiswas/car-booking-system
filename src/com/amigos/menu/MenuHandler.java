package com.amigos.menu;

import com.amigos.service.BookingService;
import com.amigos.service.UserService;

import java.time.LocalDate;
import java.util.Scanner;

import static com.amigos.service.BookingService.validateDate;
import static com.amigos.service.BookingService.viewAllBookings;
import static com.amigos.service.CarService.showAvailableCars;
import static com.amigos.service.CarService.showAvailableElectricCars;
import static java.lang.Thread.sleep;

public class MenuHandler {

    public static void runMenu() throws InterruptedException {
        Scanner scanner = new Scanner(System.in);

        while (true) {

            printMenu();

            String menu = scanner.nextLine();

            switch (menu) {
                case "1" -> BookingService.newbooking();

                case "2" -> BookingService.deleteCarBooking();

                case "3" -> BookingService.viewAllBookedCars();

                case "4" -> {
                    viewAllBookings();
                    sleep(3000);
                }

                case "5" -> {
                    System.out.println("Enter Start Date:");
                    LocalDate startDate = LocalDate.parse(scanner.nextLine());
                    System.out.println("Enter End Date:");
                    LocalDate endDate = LocalDate.parse(scanner.nextLine());
                    showAvailableCars(startDate, endDate);
                }

                case "6" -> {
                    LocalDate startDate = null;
                    LocalDate endDate = null;
                    while(true){
                        System.out.println("Enter Start Date (yyyy-MM-dd) ::");
                        startDate = LocalDate.parse(scanner.nextLine());
                        System.out.println("Enter End Date (yyyy-MM-dd) ::");
                        endDate = LocalDate.parse(scanner.nextLine());
                        if(!validateDate(startDate,endDate)){
                            continue;
                        }
                        break;
                    }
                    showAvailableElectricCars(startDate, endDate);
                    sleep(3000);
                }

                case "7" -> {
                    UserService.viewAllUsers();
                    sleep(2000);
                }

                case "8" -> {
                    shutdown();
                    scanner.close();
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }

        }
}



private static void printMenu() throws InterruptedException {
    System.out.println("====================================================");
    System.out.println("Welcome to Car Booking Management System");
    System.out.println("====================================================");
    System.out.println("*************** Menu *****************************");
    System.out.println("1 - Book Car");
    System.out.println("2 - Delete Booking");
    System.out.println("3 - View All User Booked Cars");
    System.out.println("4 - View All Bookings");
    System.out.println("5 - View Available Cars");
    System.out.println("6 - View Available Electric Cars");
    System.out.println("7 - View All Users");
    System.out.println("8 - Exits");
    System.out.println("***************************************************");
    System.out.println("Enter your choice:");
}

private static void shutdown() throws InterruptedException {
    System.out.println("Car Booking System shutting down");
    for (int i = 0; i < 20; i++) {
        System.out.print(".");
        sleep(100);
    }
}

}
