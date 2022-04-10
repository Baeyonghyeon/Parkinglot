package com.nhnacademy.parkinglot.exception;

public class InsufficientCashException extends IllegalArgumentException {
    public InsufficientCashException() {
        super("잔액 부족");
    }
}
