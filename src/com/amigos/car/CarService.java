package com.amigos.car;


import com.amigos.booking.BookingDao;
import com.amigos.booking.BookingStatus;
import com.amigos.booking.CarBooking;

import java.time.LocalDate;
import java.util.UUID;

public class CarService {
    private final CarDao carDao;
    private final BookingDao bookingDao;

    public CarService() {
        carDao = new CarDao();
        bookingDao = new BookingDao();
    }

    public Car findCarById(String carId) {
        UUID carIdAsUUID = UUID.fromString(carId);
        return carDao.findById(carIdAsUUID);
    }

    public Car[] findCarsByType(String carType){
        Car[] electricCars = new Car[50];
        Car[] nonElectricCars = new Car[50];
        int count = 0;
        int nonEletricCarCount = 0;
        for (Car car : carDao.findAll()){
            if(car == null){
                continue;
            }
            if (Boolean.TRUE.equals(car.getElectric())){
                electricCars[count++] = car;
            }else {
                nonElectricCars[nonEletricCarCount++] = car;
            }
        }
        if("ELECTRIC".equals(carType)){
            return electricCars;
        }else if("NON_ELECTRIC".equals(carType)){
            return nonElectricCars;
        }
        return null;
    }

    public void showAvailableCars(LocalDate startDate, LocalDate endDate, boolean isElectric){
        Car[] cars = carDao.findAll();
        int count = carDao.getCarCount();
        boolean availableCarFound = false;
        if(isElectric){
            System.out.println("============================ Available Electric  Cars: ======================================");
        } else {
            System.out.println("============================ Available All Cars: ============================================");
        }
        for(Car car : cars){
            if(car == null){
                continue;
            }

            if (isElectric && !Boolean.TRUE.equals(car.getElectric())) {
                continue;
            }

            boolean isAvailable = isCarAvailable(car, startDate, endDate);

            if(isAvailable){
                availableCarFound = true;
                System.out.printf(
                        "Car Id :: %s\tBrand :: %s\tRegistration No :: %s\tElectric :: %s\tPrice Per Day :: %s%n",
                        car.getId(),
                        car.getBrand(),
                        car.getRegNumber(),
                        car.getElectric(),
                        car.getRentalPricePerDay()
                );
            }
        }
        if (!availableCarFound){
            System.out.println("No cars available for the selected dates.");
        }
        System.out.println("====================================================================================");
    }

    private boolean isCarAvailable(Car car, LocalDate requestedStartDate, LocalDate requestedEndDate) {
        CarBooking[] bookings = bookingDao.findAll();

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

    public void updateCarInventory(){
        carDao.loadCars();
    }
}