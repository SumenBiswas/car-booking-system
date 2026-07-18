package com.sumen.car;


import com.sumen.booking.BookingDao;
import com.sumen.booking.BookingStatus;
import com.sumen.booking.CarBooking;
import com.sumen.exception.CarNotFoundException;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.UUID;

public class CarService {
    private final CarDao carDao;
    private final BookingDao bookingDao;

    public CarService() {
        this.carDao = new CarDao();
        this.bookingDao = new BookingDao();
    }

    public Car findCarById(UUID carId) {
        return carDao.findById(carId).
                orElseThrow(
                        () -> new CarNotFoundException("Car not found for this car Id :: %s".formatted(carId)));
    }

    public Car[] getAvailableCars(LocalDate startDate, LocalDate endDate, boolean isElectric) {
        Car[] cars = carDao.findAll();
        int count = carDao.getCarCount();
        int tempCount = 0;
        Car[] tempCars = new Car[count];
        boolean availableCarFound = false;
        for (Car car : cars) {
            if (car == null) {
                continue;
            }

            if (isElectric && !Boolean.TRUE.equals(car.getElectric())) {
                continue;
            }

            boolean isAvailable = isCarAvailable(car, startDate, endDate);

            if (isAvailable) {
                availableCarFound = true;
                tempCars[tempCount++] = car;
            }
        }
        if (!availableCarFound) {
            return new Car[]{};
        }
        return Arrays.copyOf(tempCars, tempCount);
    }

    private boolean isCarAvailable(Car car, LocalDate requestedStartDate, LocalDate requestedEndDate) {
        CarBooking[] bookings = bookingDao.findAllBookings();

        for (int i = 0; i < bookingDao.getBookingCount(); i++) {
            CarBooking booking = bookings[i];

            if (booking == null || booking.getBookingStatus() != BookingStatus.ACTIVE) {
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

    public void loadCarInventory() {
        carDao.loadCars();
    }
}