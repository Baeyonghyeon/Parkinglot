package com.nhnacademy.parkinglot.exception;

public class NoAccessParkingLot extends IllegalArgumentException {
    public NoAccessParkingLot() {
        super("대형차는 출입이 불가능합니다.");
    }
}
