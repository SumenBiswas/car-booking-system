package com.amigos.menu;

import com.amigos.Util.DateUtility;
import com.amigos.booking.BookingService;
import com.amigos.car.CarService;
import com.amigos.user.UserService;
import java.time.LocalDate;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class MenuHandler {
    private final UserService userService = new UserService();
    private final BookingService bookingService = new BookingService();
    private final CarService carService = new CarService();
    private final DateUtility dateUtility = new DateUtility();
    private Scanner scanner = new Scanner(System.in);
    public void runMenu() throws InterruptedException {


        while (true) {

            printMenu();

            String menu = scanner.nextLine();

            switch (menu) {
                case "1" -> bookingService.newBooking();

                case "2" -> {
                    System.out.println("Enter Booking Id :: ");
                    String bookingId =  scanner.nextLine();
                    bookingService.deleteCarBooking(bookingId);
                }

                case "3" -> {
                    System.out.println("Enter User Id ::");
                    String userId = scanner.nextLine();
                    bookingService.viewAllBookingsByUserId(userId);
                }

                case "4" -> {
                    bookingService.viewAllBookings();;

                }

                case "5" -> {
                    LocalDate startDate = null;
                    LocalDate endDate = null;
                    while(true){
                        try {
                            System.out.println("Enter Start Date (yyyy-MM-dd) ::");
                            startDate = LocalDate.parse(scanner.nextLine());
                            System.out.println("Enter End Date (yyyy-MM-dd) ::");
                            endDate = LocalDate.parse(scanner.nextLine());
                        }catch (Exception e){
                            System.out.println("Invalid date format. Please enter a valid date in the format yyyy-MM-dd.");
                            continue;
                        }
                        if(dateUtility.validateDate(startDate, endDate)){
                            continue;
                        }
                        break;
                    }

                    carService.showAvailableCars(startDate, endDate, false);

                }

                case "6" -> {
                    LocalDate startDate = null;
                    LocalDate endDate = null;
                    while(true){
                        try{
                            System.out.println("Enter Start Date (yyyy-MM-dd) ::");
                            startDate = LocalDate.parse(scanner.nextLine());
                            System.out.println("Enter End Date (yyyy-MM-dd) ::");
                            endDate = LocalDate.parse(scanner.nextLine());
                        }catch (Exception e){
                            System.out.println("Invalid date format. Please enter a valid date in the format yyyy-MM-dd.");
                            continue;
                        }

                        if(dateUtility.validateDate(startDate, endDate)){
                            continue;
                        }
                        break;
                    }
                    carService.showAvailableCars(startDate, endDate, true);
                }

                case "7" -> {
                    userService.viewAllUsers();
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
