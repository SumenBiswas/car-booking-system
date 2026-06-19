package com.sumen.booking;

import com.sumen.Util.DateUtility;
import com.sumen.car.CarService;
import com.sumen.car.Car;
import com.sumen.exception.BookingNotFoundException;
import com.sumen.exception.CarNotFoundException;
import com.sumen.exception.UserNotFoundException;
import com.sumen.user.User;
import com.sumen.user.UserService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class BookingService {
    private final UserService userService = new UserService();
    private final BookingDao bookingDao = new BookingDao();
    private final CarService carService = new CarService();
    private final DateUtility dateUtility = new DateUtility();

    public CarBooking addBooking(String userId, String carId, LocalDate startDate, LocalDate endDate) {
        User user = userService.findUserById(userId);
        if (user == null) {
            throw new UserNotFoundException("User not found for this User Id :: " + userId);
        }
        Car car = carService.findCarById(carId);
        if (car == null) {
            throw new CarNotFoundException("Car not found for this Car Id :: " + carId);
        }
        long totalDays = ChronoUnit.DAYS.between(startDate, endDate);
        totalDays = (totalDays == 0) ? 1 : totalDays;
        BigDecimal totalPrice = car.getRentalPricePerDay().multiply(BigDecimal.valueOf(totalDays));
        CarBooking carBooking = new CarBooking(user, car, startDate, endDate, totalPrice);
        bookingDao.save(carBooking);
        return carBooking;
    }



    public void deleteCarBooking(String bookingId) {
        boolean isCarAvailable = false;
        CarBooking[] bookings = bookingDao.findAll();
        int bookingCount = bookingDao.getBookingCount();
        for (int i = 0; i < bookingCount; i++) {
            if (bookings[i].getId().equals(UUID.fromString(bookingId))) {
                bookings[i].setBookingStatus(BookingStatus.CANCELLED);
                isCarAvailable = true;
                break;
            }
        }
        if (!isCarAvailable) {
            throw new CarNotFoundException("Car Booking not found for this Booking Id :: " + bookingId);
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


    public CarBooking[] viewAllBookings() {

        boolean bookingFound = false;
        int bookingCount = bookingDao.getBookingCount();
        CarBooking[] bookings = bookingDao.findAll();
        CarBooking[] tempBookings = new CarBooking[bookingCount];

        for (int i = 0; i < bookingCount; i++) {
            CarBooking booking = bookings[i];
            if (booking == null || booking.getBookingStatus() != BookingStatus.ACTIVE) {
                continue;
            }
            bookingFound = true;
            tempBookings[i] = booking;
        }

        if (!bookingFound) {
            throw new BookingNotFoundException("Booking not found");
        }
        return tempBookings;
    }

    public int getBookingCount(){
        return bookingDao.getBookingCount();
    }

    public CarBooking[] viewAllBookingsByUserId(String userId){
        UUID userUUID = UUID.fromString(userId);
        CarBooking[] bookings = bookingDao.findAll();
        int bookingCount = bookingDao.getBookingCount();
        CarBooking[] bookingsByUserId = new CarBooking[50];
        User user = userService.findUserById(userId);
        if(user == null){
            throw new UserNotFoundException("User not found for this User Id :: " + userId);
        }
        String userName = userService.findUserById(userId).getName();
        for (int i = 0; i < bookingCount; i++) {
            CarBooking booking = bookings[i];

            if(booking.getUser().getId().equals(userUUID) && booking.getBookingStatus() == BookingStatus.ACTIVE){
                bookingsByUserId[i] = booking;
            }
        }
        return bookingsByUserId;
    }


}
