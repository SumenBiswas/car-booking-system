package com.sumen.presentation;

import com.sumen.Util.DateUtility;
import com.sumen.car.Car;
import com.sumen.car.CarService;
import com.sumen.exception.CarNotFoundException;

import java.time.LocalDate;

public class CarHandler {
    private final DateUtility dateUtility = new DateUtility();
    private final CarService carService = new CarService();
    public void showAvailableCars(boolean isElectric) {
        LocalDate startDate = null;
        LocalDate endDate = null;
        boolean validDate = false;

        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++");
        do{
            startDate = dateUtility.readDate("Enter Start Date (yyyy-MM-dd) ::");
            endDate = dateUtility.readDate("Enter End Date (yyyy-MM-dd) ::");
            validDate = dateUtility.validateDate(startDate, endDate);
        }while (!validDate);
        if (isElectric) {
            System.out.println("============================ Available Electric  Cars: ======================================");
        } else {
            System.out.println("============================ Available All Cars: ============================================");
        }
        try{
            Car[] tempCars = carService.findAvailableCars(startDate, endDate, isElectric);
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
