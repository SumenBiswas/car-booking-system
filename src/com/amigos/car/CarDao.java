package com.amigos.car;

import java.math.BigDecimal;
import java.util.UUID;


public class CarDao {
    private static Car[] cars = new Car[50];
    private static int carCount=0;

    public CarDao() {
        System.out.println("Car Dao is created");
    }

    public void save(Car car) {
        cars[carCount++] = car;
    }

    public int getCarCount() {return carCount;}


    public Car findById(UUID carId) {
        for (int i = 0; i < carCount; i++) {
            if(cars[i].getId().equals(carId)){
                return cars[i];
            }
        }
        return null;
    }

    public Car[] findAll() {
        Car[] tempCars = new Car[carCount];
        for(int i = 0; i < carCount; i++){
            tempCars[i] = cars[i];
        }
        return tempCars;
    }

    public void loadCars(){
        cars[carCount++] = new Car(
                UUID.randomUUID(),
                "DXB-1001",
                new BigDecimal("150.00"),
                Brand.TESLA,
                true
        );

        cars[carCount++] = new Car(
                UUID.randomUUID(),
                "DXB-1002",
                new BigDecimal("120.00"),
                Brand.TOYOTA,
                true
        );

        cars[carCount++] = new Car(
                UUID.randomUUID(),
                "DXB-1003",
                new BigDecimal("200.00"),
                Brand.AUDI,
                false
        );

        cars[carCount++] = new Car(
                UUID.randomUUID(),
                "DXB-1004",
                new BigDecimal("180.00"),
                Brand.MERCEDES,
                true
        );

        cars[carCount++] = new Car(
                UUID.randomUUID(),
                "DXB-1005",
                new BigDecimal("150.00"),
                Brand.TESLA,
                false
        );
        System.out.println("Cars loaded successfully.....");
    }
}
