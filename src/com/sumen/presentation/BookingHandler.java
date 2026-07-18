package com.sumen.presentation;

import com.sumen.Util.DateUtility;
import com.sumen.booking.BookingService;
import com.sumen.booking.CarBooking;
import com.sumen.car.Car;
import com.sumen.car.CarService;
import com.sumen.exception.BookingNotFoundException;
import com.sumen.exception.CarNotFoundException;
import com.sumen.exception.UserNotFoundException;
import com.sumen.user.User;
import com.sumen.user.UserService;

import java.time.LocalDate;
import java.util.Scanner;
import java.util.UUID;


public class BookingHandler {
    private final UserService userService = new UserService();
    private final BookingService bookingService = new BookingService();
    private final CarService carService = new CarService();
    private final DateUtility dateUtility = new DateUtility();
    private Scanner scanner = new Scanner(System.in);

    public void addBooking() {
        CarBooking carBooking = null;
        LocalDate startDate;
        LocalDate endDate;
        boolean validDate = false;
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++");
        viewAllUsers();
        System.out.println("Enter User Id :: ");
        UUID userId = UUID.fromString(scanner.nextLine());
        try {
            User user = userService.findUserById(userId);
            do{
                startDate = dateUtility.readDate("Enter Start Date (yyyy-MM-dd) ::");
                endDate = dateUtility.readDate("Enter End Date (yyyy-MM-dd) ::");
                validDate = dateUtility.validateDate(startDate, endDate);
            }while (!validDate);
            showAllAvailableCars(startDate, endDate, false);
            System.out.println("Enter Car Id :: ");
            UUID carId = UUID.fromString(scanner.nextLine());
            Car car = carService.findCarById(carId);
            carBooking = bookingService.addBooking(user, car, startDate, endDate);
        } catch (UserNotFoundException e) {
            System.out.println("Booking failed :: " + e.getMessage());
            System.out.println("Please try again");
            System.out.println("**************************************************************************************");
            return;
        } catch (CarNotFoundException e) {
            System.out.println("Booking failed :: " + e.getMessage());
            System.out.println("Please try again");
            System.out.println("**************************************************************************************");
            return;
        }
        System.out.println("*********************** Your Booking is successful ***********************************");
        System.out.println(" Booking Id : " + carBooking.getId());
        System.out.println(" Car Number : " + carBooking.getCar().getRegNumber());
        System.out.println(" User Name : " + carBooking.getUser().getName());
        System.out.println(" User Id : " + carBooking.getUser().getId());
        System.out.println(" Start Date : " + carBooking.getStartDate());
        System.out.println(" End Date : " + carBooking.getEndDate());
        System.out.println(" Total Price : " + carBooking.getPrice());
        System.out.println("*****************************************************************************************");
    }

    public void deleteBooking(){
        System.out.println("============================== Delete Booking ==============================");
        System.out.println("Enter Booking Id :: ");
        String bookingId =  scanner.nextLine();
        try{
            bookingService.cancelBooking(UUID.fromString(bookingId));
            System.out.printf("Booking %s is Cancelled Successfully :: ", bookingId);
        }catch(BookingNotFoundException e){
            System.out.println(e.getMessage());
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
        System.out.println("============================================================================");
    }

    public void viewAllBookingsByUserId(){

        System.out.println("Enter User Id ::");
        String userId = scanner.nextLine();

        try{
            String userName = userService.findUserById(UUID.fromString(userId)).getName();
            CarBooking[] bookings = bookingService.getAllActiveBookingsByUserId(UUID.fromString(userId));
            System.out.println("============================== Bookings by User ( " + userName +" ) ==============================");
            for (CarBooking booking : bookings) {
                if (booking == null){
                    continue;
                }
                Car car = booking.getCar();
                System.out.printf("Booking Id: %s | Car: %s | Brand: %s | Status: %s%n | Start date: %s | End date: %s | Car Id: %s",
                        booking.getId().toString(), booking.getCar().getRegNumber(), booking.getCar().getBrand(), booking.getBookingStatus(),
                        booking.getStartDate(), booking.getEndDate(), car.getId().toString());
                System.out.println("");
            }
        }catch(UserNotFoundException e){
            System.out.println("*********************** " + e.getMessage() + " ***********************************************************");
        }catch (BookingNotFoundException e){
            System.out.println("*********************** " + e.getMessage() + " ***********************************************************");
        }

        System.out.println("====================================================================================================");
    }

    public void viewAllBookings(){
        System.out.println("============================== All Bookings ==============================");
        try {
            CarBooking[] bookings = bookingService.getAllBookings();
            for (CarBooking booking : bookings) {
                if (booking == null){
                    continue;
                }
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
        }catch(BookingNotFoundException e){
            System.out.println("*********************** " + e.getMessage() + " ***********************************************************");
        }
        System.out.println("====================================================================================================");
    }

    private void viewAllUsers(){
        System.out.println("============================== All Users ==============================");
        try {
            User[] users = userService.getAllUsers();
            for (User user : users) {
                if (user == null){
                    continue;
                }
                System.out.printf("User Id :: %s | Name :: %s%n", user.getId(), user.getName());
            }
        }catch (UserNotFoundException e){
            System.out.println("*********************** " + e.getMessage() + " ***********************************************************");
        }
        System.out.println("==========================================================================");

    }

    private void showAllAvailableCars(LocalDate startDate, LocalDate endDate, boolean isElectric){
        System.out.println("============================ Available All Cars: ============================================");
        try{
            Car[] tempCars = carService.getAvailableCars(startDate, endDate, isElectric);
            System.out.println("No of Available Cars :: " + tempCars.length);
            for(int i = 0; i < tempCars.length; i++){
                Car car = tempCars[i];
                System.out.printf(
                        "Car Id :: %s\tBrand :: %s\tRegistration No :: %s\tElectric :: %s\tPrice Per Day :: %s%n",
                        car.getId(),
                        car.getBrand(),
                        car.getRegNumber(),
                        car.getElectric(),
                        car.getRentalPricePerDay()
                );
            }
        }catch (CarNotFoundException e){
            System.out.println("---------------------------- " + e.getMessage() + " --------------------------------------------");
        }
        System.out.println("====================================================================================");
    }

}

