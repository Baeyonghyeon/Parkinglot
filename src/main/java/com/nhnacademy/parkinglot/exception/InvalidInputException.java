package com.nhnacademy.parkinglot.exception;

public class InvalidInputException extends IllegalArgumentException {
    public InvalidInputException(String id) {
        super("invalid input");
    }
}
