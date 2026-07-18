package com.sumen.presentation;

import java.util.Scanner;

import static java.lang.Thread.sleep;

public class MenuHandler {
    private final BookingHandler bookingHandler = new BookingHandler();
    private final CarHandler carHandler = new CarHandler();
    private final UserHandler userHandler = new UserHandler();
    private Scanner scanner = new Scanner(System.in);

    public void runMenu() throws InterruptedException {
        while (true) {
            printMenu();
            String menu = scanner.nextLine();

            switch (menu) {
                case "1" -> bookingHandler.addBooking();
                case "2" -> bookingHandler.deleteBooking();
                case "3" -> bookingHandler.viewAllBookingsByUserId();
                case "4" -> bookingHandler.viewAllBookings();
                case "5" -> carHandler.showAvailableCars(false);
                case "6" -> carHandler.showAvailableCars(true);
                case "7" -> userHandler.showAllUsers();
                case "8" -> {
                    shutdown();
                    scanner.close();
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
}

private void printMenu() {
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

private void shutdown() throws InterruptedException {
    System.out.println("Car Booking System shutting down");
    for (int i = 0; i < 20; i++) {
        System.out.print(".");
        sleep(100);
    }
}

}
