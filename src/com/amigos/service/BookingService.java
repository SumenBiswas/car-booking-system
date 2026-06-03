package com.amigos.service;

import com.amigos.data.DataStore;
import com.amigos.model.BookingStatus;
import com.amigos.model.Car;
import com.amigos.model.CarBooking;
import com.amigos.model.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;
import java.util.UUID;

import static java.lang.Thread.sleep;

public class BookingService {
    public static void newbooking() throws InterruptedException {
        //CarBooking[] carBookings = DataStore.carBookings;
        //int carBookingCount = DataStore.carBookingCount;
        CarBooking carBooking = null;
        User user = null;
        long totalDays = 0;
        LocalDate startDate = null;
        LocalDate endDate = null;
        Scanner scanner = new Scanner(System.in);
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("Are you existing customer (Y/N):: ");
        String answer = scanner.nextLine();
        if(answer.equalsIgnoreCase("Y")){
            System.out.println("Please enter User Id :: ");
            String userId = scanner.nextLine();
            user = UserService.findUserById(userId);
            if(user == null){
                System.out.println("User not found");
                System.out.println("Please enter User name :: ");
                String userName = scanner.nextLine();
                user = UserService.findUserByName(userName);
                if(user == null){
                    System.out.println("User not found");
                    UserService.addUser(userName);
                }
            }
        }else{
            System.out.println("Please enter User Name :: ");
            String name = scanner.nextLine();
            user = UserService.addUser(name);

        }
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

        totalDays = ChronoUnit.DAYS.between(startDate, endDate);

        if (totalDays == 0){
            totalDays = 1;
        }

        CarService.showAvailableCars(startDate, endDate);
        System.out.println("Enter Car Id :: ");
        String carId = scanner.nextLine();
        Car car = CarService.findCarById(carId);
        if(car == null){
            System.out.println("Car not found.....");
            sleep(1000);
            return;
        }

        BigDecimal totalPrice = car.getRentalPricePerDay().multiply(BigDecimal.valueOf(totalDays));

        carBooking = new CarBooking(UUID.randomUUID(), car.getElectric(), user, car, startDate, endDate,
                totalPrice, BookingStatus.ACTIVE, LocalDateTime.now());

        DataStore.carBookings[DataStore.carBookingCount++] = carBooking;

        System.out.println("*********************** Your Booking is successfull ***********************************");
        System.out.println(" Booking Id : " + carBooking.getId());
        System.out.println(" Car Number : " + carBooking.getCar().getId());
        System.out.println(" User Name : " + carBooking.getUser().getName());
        System.out.println(" User Id : " + carBooking.getUser().getId());
        System.out.println(" Start Date : " + carBooking.getStartDate());
        System.out.println(" End Date : " + carBooking.getEndDate());
        System.out.println(" Total Price : " + totalPrice);
        System.out.println("*****************************************************************************************");
    }

    public static boolean validateDate(LocalDate startDate, LocalDate endDate){
        if(endDate.isBefore(startDate)){
            System.out.println("End date cannot be before start date");
            return false;
        }
        return true;
    }

    public static boolean isCarAvailable(Car car, LocalDate requestedStartDate, LocalDate requestedEndDate) {
        CarBooking[] bookings = DataStore.carBookings;

        for (int i = 0; i < DataStore.carBookingCount; i++) {
            CarBooking booking = bookings[i];
            if (booking.getBookingStatus() != BookingStatus.ACTIVE) {
                continue;
            }
            if (booking.getCar().getId().equals(car.getId())) {
                boolean overlapDays =
                        !requestedStartDate.isAfter(booking.getEndDate()) &&
                                !requestedEndDate.isBefore(booking.getStartDate());

                if (overlapDays) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void deleteCarBooking() {
        Scanner scanner = new Scanner(System.in);
        boolean isCarAvailable = false;
        System.out.println("Enter Booking Id :: ");
        String bookingId =  scanner.nextLine();
        CarBooking[] bookings = DataStore.carBookings;
        for (int i = 0; i < DataStore.carBookingCount; i++) {
            if (bookings[i].getId().toString().equals(bookingId)) {
                bookings[i].setBookingStatus(BookingStatus.CANCELLED);
                System.out.println(String.format("Car Booking Id : %s has been cancelled", bookings[i].getId().toString()));
                isCarAvailable = true;
                break;
            }
        }
        if (!isCarAvailable) {
            System.out.println(String.format("Car Booking Id : %s doesn't exist", bookingId) );
        }

    }

    public static void viewAllBookedCars() {
        boolean carBookedFound = false;
        System.out.println("================================== Booked cars ===================================");
        for (int i = 0; i < DataStore.carBookingCount; i++) {
            CarBooking booking = DataStore.carBookings[i];
            if(booking != null && booking.getBookingStatus() == BookingStatus.ACTIVE){
                Car car = booking.getCar();
                System.out.printf("Booking Id: %s | Car: %s | Brand: %s | Status: %s%n | Start date: %s | End date: %s", booking.getId().toString(),
                        car.getRegNumber(), car.getBrand(), booking.getBookingStatus(), booking.getStartDate(), booking.getEndDate());
                carBookedFound = true;
            }
        }

        if(!carBookedFound) {
            System.out.println("-------- No Books Car Found ---------------");
        }
        System.out.println("======================================================================================");
    }


    public static void viewAllBookings() {

        boolean bookingFound = false;
        System.out.println("Booking Array Length :" + DataStore.carBookings.length);
        System.out.println("Number of Bookings :" + DataStore.carBookingCount );
        System.out.println("============================= All Bookings =============================");

        for (int i = 0; i < DataStore.carBookingCount; i++) {

            CarBooking booking = DataStore.carBookings[i];

            if (booking == null || booking.getBookingStatus() != BookingStatus.ACTIVE) {
                continue;
            }

            bookingFound = true;

            Car car = booking.getCar();
            User user = booking.getUser();

            System.out.printf(
                    "Booking Id :: %s | User :: %s | Car :: %s | Brand :: %s | Status :: %s | Start Date :: %s | End Date :: %s%n",
                    booking.getId(),
                    user.getName(),
                    car.getRegNumber(),
                    car.getBrand(),
                    booking.getBookingStatus(),
                    booking.getStartDate(),
                    booking.getEndDate()
            );
        }

        if (!bookingFound) {
            System.out.println("No bookings found.");
        }

        System.out.println("========================================================================");
    }


}
