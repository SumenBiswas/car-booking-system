package com.sumen.booking;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

public class BookingDao {
    private static CarBooking[] bookings = new CarBooking[100];
    private static int bookingCount = 0;

    public void save(CarBooking booking) {
        if (bookingCount == bookings.length) {
            bookings = Arrays.copyOf(bookings, bookingCount * 2);
        }
        bookings[bookingCount++] = booking;
    }

    public Optional<CarBooking> findById(UUID bookingId) {
        for (int i = 0; i < bookingCount; i++) {
            if (bookings[i].getId().equals(bookingId)) {
                return Optional.of(bookings[i]);
            }
        }
        return Optional.empty();
    }


    public CarBooking[] findAllBookings() {
        return Arrays.copyOf(bookings, bookingCount);
    }

    public int getBookingCount() {
        return bookingCount;
    }
}
