package com.sumen.booking;

import com.sumen.car.Car;
import com.sumen.exception.BookingNotFoundException;
import com.sumen.user.User;
import com.sumen.user.UserService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.UUID;

public class BookingService {
    private final UserService userService = new UserService();
    private final BookingDao bookingDao = new BookingDao();

    public CarBooking addBooking(User user, Car car, LocalDate startDate, LocalDate endDate) {
        long totalDays = ChronoUnit.DAYS.between(startDate, endDate);
        totalDays = (totalDays == 0) ? 1 : totalDays;
        BigDecimal totalPrice = car.getRentalPricePerDay().multiply(BigDecimal.valueOf(totalDays));
        CarBooking carBooking = new CarBooking(user, car, startDate, endDate, totalPrice);
        bookingDao.save(carBooking);
        return carBooking;
    }



    public void cancelBooking(UUID bookingId) {
        CarBooking booking = bookingDao.findById(bookingId).orElseThrow(
                () -> new BookingNotFoundException("Booking not found for this booking Id :: %s".formatted(bookingId)));
        if(booking.getBookingStatus() == BookingStatus.CANCELLED){
            throw new IllegalStateException("Booking is already cancelled");
        }
        booking.setBookingStatus(BookingStatus.CANCELLED);
    }

    public CarBooking[] getAllBookings() {
        CarBooking[] bookings = bookingDao.findAllBookings();
        CarBooking[] tempBookings = new CarBooking[bookings.length];
        int tempCount = 0;
        for (CarBooking booking : bookings) {
            if(booking != null && booking.getBookingStatus() == BookingStatus.ACTIVE){
                tempBookings[tempCount++] = booking;
            }
        }
        return Arrays.copyOf(tempBookings, tempCount);
    }

    public CarBooking[] getAllActiveBookingsByUserId(UUID userId){
        userService.findUserById(userId);

        CarBooking[] bookings = bookingDao.findAllBookings();
        CarBooking[] bookingsByUserId = new CarBooking[bookings.length];

        int count = 0;

        for (CarBooking booking : bookings) {
            if (isActiveBookingForUser(booking, userId)) {
                bookingsByUserId[count++] = booking;
            }
        }
        return Arrays.copyOf(bookingsByUserId, count);
    }

    private boolean isActiveBookingForUser(CarBooking booking, UUID userId){
        return booking!=null
                && booking.getBookingStatus() == BookingStatus.ACTIVE
                && booking.getUser().getId().equals(userId);
    }


}
