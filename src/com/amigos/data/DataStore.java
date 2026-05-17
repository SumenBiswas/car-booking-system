package com.amigos.data;

import com.amigos.model.Brand;
import com.amigos.model.Car;
import com.amigos.model.CarBooking;
import com.amigos.model.User;

import java.math.BigDecimal;
import java.util.UUID;

public class DataStore {
    public static Car[] cars = new Car[50];
    public static int numberOfCars = 0;

    public static User[] users = new User[50];
    public static int userCount = 0;

    public static CarBooking[] carBookings = new CarBooking[50];
    public static int carBookingCount = 0;

    public static void loadCars(){
        cars[numberOfCars++] = new Car(
                UUID.randomUUID(),
                "DXB-1001",
                new BigDecimal("150.00"),
                Brand.TESLA,
                true
        );

        cars[numberOfCars++] = new Car(
                UUID.randomUUID(),
                "DXB-1002",
                new BigDecimal("120.00"),
                Brand.TOYOTA,
                true
        );

        cars[numberOfCars++] = new Car(
                UUID.randomUUID(),
                "DXB-1003",
                new BigDecimal("200.00"),
                Brand.AUDI,
                true
        );

        cars[numberOfCars++] = new Car(
                UUID.randomUUID(),
                "DXB-1004",
                new BigDecimal("180.00"),
                Brand.MERCEDES,
                true
        );

        cars[numberOfCars++] = new Car(
                UUID.randomUUID(),
                "DXB-1005",
                new BigDecimal("150.00"),
                Brand.TESLA,
                false
        );

        /*cars[numberOfCars++] = new Car(
                UUID.randomUUID(),
                "DXB-1006",
                new BigDecimal("120.00"),
                Brand.TOYOTA,
                false
        );

        cars[numberOfCars++] = new Car(
                UUID.randomUUID(),
                "DXB-1007",
                new BigDecimal("200.00"),
                Brand.AUDI,
                false
        );

        cars[numberOfCars++] = new Car(
                UUID.randomUUID(),
                "DXB-1008",
                new BigDecimal("180.00"),
                Brand.MERCEDES,
                false
        );

        cars[numberOfCars++] = new Car(
                UUID.randomUUID(),
                "DXB-1009",
                new BigDecimal("150.00"),
                Brand.TESLA,
                true
        );

        cars[numberOfCars++] = new Car(
                UUID.randomUUID(),
                "DXB-1010",
                new BigDecimal("120.00"),
                Brand.TOYOTA,
                true
        );

        cars[numberOfCars++] = new Car(
                UUID.randomUUID(),
                "DXB-1011",
                new BigDecimal("200.00"),
                Brand.AUDI,
                true
        );

        cars[numberOfCars++] = new Car(
                UUID.randomUUID(),
                "DXB-1012",
                new BigDecimal("180.00"),
                Brand.MERCEDES,
                true
        );

        cars[numberOfCars++] = new Car(
                UUID.randomUUID(),
                "DXB-1013",
                new BigDecimal("150.00"),
                Brand.TESLA,
                false
        );

        cars[numberOfCars++] = new Car(
                UUID.randomUUID(),
                "DXB-1014",
                new BigDecimal("120.00"),
                Brand.TOYOTA,
                false
        );

        cars[numberOfCars++] = new Car(
                UUID.randomUUID(),
                "DXB-1015",
                new BigDecimal("200.00"),
                Brand.AUDI,
                false
        );

        cars[numberOfCars++] = new Car(
                UUID.randomUUID(),
                "DXB-1016",
                new BigDecimal("180.00"),
                Brand.MERCEDES,
                false
        );

        cars[numberOfCars++] = new Car(
                UUID.randomUUID(),
                "DXB-1017",
                new BigDecimal("180.00"),
                Brand.MERCEDES,
                true
        );

        cars[numberOfCars++] = new Car(
                UUID.randomUUID(),
                "DXB-1018",
                new BigDecimal("150.00"),
                Brand.TESLA,
                true
        );

        cars[numberOfCars++] = new Car(
                UUID.randomUUID(),
                "DXB-1019",
                new BigDecimal("120.00"),
                Brand.TOYOTA,
                true
        );

        cars[numberOfCars++] = new Car(
                UUID.randomUUID(),
                "DXB-1020",
                new BigDecimal("200.00"),
                Brand.AUDI,
                true
        );

        cars[numberOfCars++] = new Car(
                UUID.randomUUID(),
                "DXB-1021",
                new BigDecimal("180.00"),
                Brand.MERCEDES,
                true
        );*/
    }

}
