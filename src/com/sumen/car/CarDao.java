package com.sumen.car;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;


public class CarDao {
    private static Car[] cars = new Car[50];
    private static int carCount = 0;

    public void save(Car car) {
        cars[carCount++] = car;
    }

    public int getCarCount() {
        return carCount;
    }


    public Optional<Car> findById(UUID carId) {
        for (int i = 0; i < carCount; i++) {
            if (cars[i].getId().equals(carId)) {
                return Optional.of(cars[i]);
            }
        }
        return Optional.empty();
    }

    public Car[] findAll() {
        return Arrays.copyOf(cars, carCount);
    }

    public void loadCars() {
        save(new Car(UUID.randomUUID(), "DXB-1001", new BigDecimal("150.00"), Brand.TESLA, true));
        save(new Car(UUID.randomUUID(), "DXB-1002", new BigDecimal("120.00"), Brand.TOYOTA, true));
        save(new Car(UUID.randomUUID(), "DXB-1003", new BigDecimal("200.00"), Brand.AUDI, false));
        save(new Car(UUID.randomUUID(), "DXB-1004", new BigDecimal("180.00"), Brand.MERCEDES, true));
        save( new Car(UUID.randomUUID(), "DXB-1005", new BigDecimal("150.00"), Brand.TESLA, false));
    }
}
