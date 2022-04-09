package com.nhnacademy.parkinglot.exception;

public class NoParkingSpaceException extends IllegalArgumentException {
    public NoParkingSpaceException() {
        super("해당 주차 공간이 없습니다.");
    }
}
