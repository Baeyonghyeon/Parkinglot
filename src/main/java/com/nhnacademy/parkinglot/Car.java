package com.nhnacademy.parkinglot;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Car {

    int number;
    LocalDateTime parkingStartTime;
    BigDecimal amount;

    public Car(int number) {
        this.number = number;
        this.parkingStartTime = LocalDateTime.now();
    }

    public Car(int number, LocalDateTime localDateTime) {
        this.number = number;
        this.parkingStartTime = localDateTime;
    }

    public LocalDateTime getParkingStartTime() {
        return parkingStartTime;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public int getNumber() {
        return number;
    }
}
