package com.amigos.booking;

import java.util.UUID;

public class BookingDao {
    private static final CarBooking[] bookings = new CarBooking[100];
    private static int bookingCount = 0;

    public BookingDao() {
        System.out.println("Booking Dao is created");
    }

    public void save(CarBooking booking) {
        bookings[bookingCount++] = booking;
    }

    public CarBooking findById(UUID bookingId) {
        for (int i = 0; i < bookingCount; i++) {
            if(bookings[i].getId().equals(bookingId)){
                return bookings[i];
            }
        }
        return null;
    }


    public CarBooking[] findAll() {
        CarBooking[] tempBookings = new CarBooking[bookingCount];
        for(int i = 0; i < bookingCount; i++){
            tempBookings[i] = bookings[i];
        }
        return tempBookings;
    }

    public int getBookingCount() {
        return bookingCount;
    }
}
