package com.amigos.booking;

import com.amigos.Util.DateUtility;
import com.amigos.car.CarService;
import com.amigos.car.Car;
import com.amigos.user.User;
import com.amigos.user.UserService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;
import java.util.UUID;

public class BookingService {
    private final UserService userService = new UserService();
    private final BookingDao bookingDao = new BookingDao();
    private final CarService carService = new CarService();
    private final DateUtility dateUtility = new DateUtility();
    private Scanner scanner = new Scanner(System.in);



    public void newBooking() {
        String name = null;
        CarBooking carBooking;
        User user = null;
        UUID userUUID = null;
        long totalDays;
        LocalDate startDate;
        LocalDate endDate;
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("Are you existing customer (Y/N):: ");
        String answer = scanner.nextLine();
        if(answer.equalsIgnoreCase("Y")){
            System.out.println("Please enter User Id :: ");
            String userId = scanner.nextLine();
            user = userService.findUserById(userId);
            if(user == null){
                System.out.println("User not found");
                System.out.println("Please enter User Name :: ");
                name = scanner.nextLine();
                user = userService.findUserByName(name);
                if(user == null){
                   userUUID = userService.addUser(name);
                   user = userService.findUserById(userUUID);

                }
              }
            }else{
            System.out.println("Please enter User Name :: ");
            name = scanner.nextLine();
            user = userService.findUserByName(name);
            if(user != null) {
                System.out.println("User already exists");
            }else {
               userUUID = userService.addUser(name);
               user = userService.findUserById(userUUID);
            }

        }
        while(true){
            System.out.println("Enter Start Date (yyyy-MM-dd) ::");
            startDate = LocalDate.parse(scanner.nextLine());
            System.out.println("Enter End Date (yyyy-MM-dd) ::");
            endDate = LocalDate.parse(scanner.nextLine());
            if(dateUtility.validateDate(startDate, endDate)){
                continue;
            }
           break;
        }

        totalDays = ChronoUnit.DAYS.between(startDate, endDate);

        totalDays = (totalDays == 0) ? 1 : totalDays;

        carService.showAvailableCars(startDate, endDate, false);
        System.out.println("Enter Car Id :: ");
        String carId = scanner.nextLine();
        Car car = carService.findCarById(carId);
        if(car == null){
            System.out.println("Car not found.....");
            return;
        }

        BigDecimal totalPrice = car.getRentalPricePerDay().multiply(BigDecimal.valueOf(totalDays));

        carBooking = new CarBooking(UUID.randomUUID(), car.getElectric(), user, car, startDate, endDate,
                totalPrice, BookingStatus.ACTIVE, LocalDateTime.now());

        bookingDao.save(carBooking);

        System.out.println("*********************** Your Booking is successful ***********************************");
        System.out.println(" Booking Id : " + carBooking.getId());
        System.out.println(" Car Number : " + carBooking.getCar().getRegNumber());
        System.out.println(" User Name : " + carBooking.getUser().getName());
        System.out.println(" User Id : " + carBooking.getUser().getId());
        System.out.println(" Start Date : " + carBooking.getStartDate());
        System.out.println(" End Date : " + carBooking.getEndDate());
        System.out.println(" Total Price : " + totalPrice);
        System.out.println("*****************************************************************************************");
    }



    public void deleteCarBooking(String bookingId) {
        scanner = new Scanner(System.in);
        boolean isCarAvailable = false;
        CarBooking[] bookings = bookingDao.findAll();
        int bookingCount = bookingDao.getBookingCount();
        for (int i = 0; i < bookingCount; i++) {
            if (bookings[i].getId().equals(UUID.fromString(bookingId))) {
                bookings[i].setBookingStatus(BookingStatus.CANCELLED);
                System.out.printf("Car Booking Id : %s has been cancelled %n ", bookings[i].getId().toString());
                isCarAvailable = true;
                break;
            }
        }
        if (!isCarAvailable) {
            System.out.printf("Car Booking Id : %s doesn't exist%n", bookingId);
        }

    }

    public void viewAllBookedCars() {
        boolean carBookedFound = false;
        int bookingCount = bookingDao.getBookingCount();
        CarBooking[] bookings = bookingDao.findAll();
        System.out.println("================================== Booked cars ===================================");
        for (int i = 0; i < bookingCount; i++) {
            CarBooking booking = bookings[i];
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


    public void viewAllBookings() {

        boolean bookingFound = false;
        int bookingCount = bookingDao.getBookingCount();
        CarBooking[] bookings = bookingDao.findAll();
        System.out.printf("Number of Bookings %s: ", bookingCount);
        System.out.println("============================= All Bookings =============================");

        for (int i = 0; i < bookingCount; i++) {
            CarBooking booking = bookings[i];
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
            System.out.println("****************** No bookings found ******************************");
        }

        System.out.println("========================================================================");
    }

    public int getBookingCount(){
        return bookingDao.getBookingCount();
    }

    public void viewAllBookingsByUserId(String userId){
        UUID userUUID = UUID.fromString(userId);
        CarBooking[] bookings = bookingDao.findAll();
        int bookingCount = bookingDao.getBookingCount();
        User user = userService.findUserById(userId);
        if(user == null){
            System.out.println("User not found");
            return;
        }
        String userName = userService.findUserById(userId).getName();
        System.out.println("============================== Bookings by User ( " + userName +" ) ==============================");
        for (int i = 0; i < bookingCount; i++) {
            CarBooking booking = bookings[i];
            System.out.println("Booking Status :: " + booking.getBookingStatus());
            if(booking.getUser().getId().equals(userUUID) && booking.getBookingStatus() == BookingStatus.ACTIVE){
                Car car = booking.getCar();
                System.out.printf("Booking Id: %s | Car: %s | Brand: %s | Status: %s%n | Start date: %s | End date: %s | Car Id: %s",
                        booking.getId().toString(), booking.getCar().getRegNumber(), booking.getCar().getBrand(), booking.getBookingStatus(),
                        booking.getStartDate(), booking.getEndDate(), car.getId().toString());
            }
        }
    }


}
