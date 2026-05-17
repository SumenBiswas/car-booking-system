package com.amigos.service;

import com.amigos.data.DataStore;
import com.amigos.model.Car;

import java.time.LocalDate;

public class CarService {
    Car[] cars = DataStore.cars;
    int carCount = DataStore.numberOfCars;

    public static Car findCarById(String carId) {
        Car[] cars =  DataStore.cars;
        for (Car car : cars) {
            if(car == null){
                continue;
            }
            if (car.getId().toString().equals(carId)) {
                return car;
            }
        }
        return null;
    }

    public static Car[] findCarsByType(String carType){
        Car[] electricCars = new Car[50];
        Car[] nonElectricCars = new Car[50];
        int count = 0;
        int nonEletricCarCount = 0;
        for (Car car : DataStore.cars){
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

    public static void showAvailableCars(LocalDate startDate, LocalDate endDate){
        Car[] cars = DataStore.cars;
        boolean availableCarFound = false;
        System.out.println("============================ Available Cars: ======================================");
        for(Car car : cars){
            if(car == null){
                continue;
            }
            boolean isAvailable = BookingService.isCarAvailable(car, startDate, endDate);
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

    public static void showAvailableElectricCars(LocalDate startDate, LocalDate endDate){
        Car[] cars = DataStore.cars;
        boolean availableCarFound = false;
        System.out.println("============================ Available Electric  Cars: ======================================");
        for(Car car : cars){
            if(car == null){
                continue;
            }
            boolean isAvailable = BookingService.isCarAvailable(car, startDate, endDate);
            if(isAvailable && car.getElectric()){
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
}
